$ErrorActionPreference = 'Stop'
$src = "C:\Development\tzitem\src\main\java\kamkeel\plugin"
$out = "C:\Development\tzitem-1.21.1\port\items.json"

# items: list of @{name=...; tex=...; tab=...; kind=...}
$items = New-Object System.Collections.Generic.List[object]

function Add-Item($name, $tex, $tab, $kind) {
    $items.Add([pscustomobject]@{ name = $name; tex = $tex; tab = $tab; kind = $kind })
}

# ---------------- Weapons (ModWeapons.java) ----------------
$mw = Get-Content "$src\Items\Weapons\ModWeapons.java" -Raw
$mwLines = Get-Content "$src\Items\Weapons\ModWeapons.java"

foreach ($line in $mwLines) {
    # Bows (no setTexture)
    if ($line -match 'ItemPluginBowInterface\("([a-z0-9_]+)"') {
        Add-Item $Matches[1] ("bows/" + $Matches[1]) 'weapons' 'bow'
        continue
    }
    # Shields
    if ($line -match 'create(?:RotatedShield|Shield)\("([a-z0-9_]+)"') {
        Add-Item $Matches[1] ("weapons/shields/" + $Matches[1]) 'weapons' 'weapon'
        continue
    }
    # Dagger (creates name + reverse_name)
    if ($line -match 'createDagger\("([a-z0-9_]+)"') {
        $n = $Matches[1]
        Add-Item $n ("weapons/" + $n) 'weapons' 'weapon'
        Add-Item ("reverse_" + $n) ("weapons/" + $n) 'weapons' 'weapon'
        continue
    }
    # Other helper weapon factories
    if ($line -match 'create(Spear|WarAxe|BattleAxe|Hammer|Sword|Broadsword)\("([a-z0-9_]+)"') {
        $n = $Matches[2]
        Add-Item $n ("weapons/" + $n) 'weapons' 'weapon'
        continue
    }
    # Explicit registrations: setTexture(registerItem(... , "REG"), "plug:TEX")
    if ($line -match 'ModItems\.setTexture\(ModItems\.registerItem\(') {
        $texMatch = [regex]::Match($line, '"plug:([^"]+)"')
        $regMatch = [regex]::Match($line, 'registerItem\([^;]*,\s*"([a-z0-9_]+)"\)')
        if ($texMatch.Success -and $regMatch.Success) {
            Add-Item $regMatch.Groups[1].Value $texMatch.Groups[1].Value 'weapons' 'weapon'
        }
    }
}

# ---------------- Simple items (ModItems.java, setTexture lines) ----------------
$miLines = Get-Content "$src\Items\ModItems.java"
foreach ($line in $miLines) {
    if ($line -match 'setTexture\(registerItem\(') {
        $texMatch = [regex]::Match($line, '"plug:([^"]+)"')
        $regMatch = [regex]::Match($line, 'registerItem\([^;]*,\s*"([a-z0-9_]+)"\)')
        if ($texMatch.Success -and $regMatch.Success) {
            $n = $regMatch.Groups[1].Value
            $t = $texMatch.Groups[1].Value
            $kind = 'simple'
            if ($line -match 'PluginItemFoodInterface') { $kind = 'food' }
            $tab = 'misc'
            if ($n -eq 'paperbomb') { $tab = 'weapons' }
            if ($n -eq 'cardicon') { $tab = 'cards' }
            if ($n -in @('voidrasenshuriken','massiverasengan','massiverasengan_spin')) { $tab = 'weapons' }
            Add-Item $n $t $tab $kind
        }
    }
}

# ---------------- Metadata enum items ----------------
function Add-EnumItems($enumFile, $prefix, $texDir, $tab, $kind, $suffix = '') {
    $txt = Get-Content $enumFile -Raw
    $m = [regex]::Matches($txt, '([A-Za-z0-9_]+)\(\d+,\s*"([^"]+)"\)')
    foreach ($x in $m) {
        $raw = $x.Groups[2].Value.ToLowerInvariant()
        $nm = $prefix + $raw + $suffix
        $tex = "$texDir/$nm"
        Add-Item $nm $tex $tab $kind
    }
}

$e = "$src\Enum\Items"
Add-EnumItems "$e\EnumPills.java" 'pills_' 'pills' 'misc' 'simple'
Add-EnumItems "$e\EnumCoins.java" 'coins_' 'coins' 'misc' 'simple'
Add-EnumItems "$e\EnumBalls.java" 'balls_' 'balls' 'misc' 'simple'
Add-EnumItems "$e\EnumEyes.java" 'eyes_' 'eyes' 'misc' 'simple'
Add-EnumItems "$e\EnumApples.java" '' 'apples' 'misc' 'food' '_apple'
Add-EnumItems "$e\EnumArtifacts.java" '' 'artifacts' 'misc' 'simple'
Add-EnumItems "$e\EnumEnergy.java" '' 'energy' 'weapons' 'simple'

$s = "$e\Suits"
Add-EnumItems "$s\EnumHeartCard.java" 'hearts_' 'cards' 'cards' 'simple'
Add-EnumItems "$s\EnumDiamondCard.java" 'diamond_' 'cards' 'cards' 'simple'
Add-EnumItems "$s\EnumSpadeCard.java" 'spade_' 'cards' 'cards' 'simple'
Add-EnumItems "$s\EnumClubsCard.java" 'clubs_' 'cards' 'cards' 'simple'
Add-EnumItems "$s\EnumJokerCard.java" 'joker_' 'cards' 'cards' 'simple'
Add-EnumItems "$s\EnumAddonCard.java" 'cards_' 'cards' 'cards' 'simple'

# ---------------- Output ----------------
# Dedupe by name (keep first)
$seen = @{}
$uniq = New-Object System.Collections.Generic.List[object]
foreach ($it in $items) {
    if (-not $seen.ContainsKey($it.name)) {
        $seen[$it.name] = $true
        $uniq.Add($it)
    }
}

$json = $uniq | ConvertTo-Json -Depth 3
[System.IO.File]::WriteAllText($out, $json, [System.Text.UTF8Encoding]::new($false))

Write-Output "Total unique items: $($uniq.Count)"
Write-Output "By tab:"
$uniq | Group-Object tab | ForEach-Object { "  {0}: {1}" -f $_.Name, $_.Count }
Write-Output "By kind:"
$uniq | Group-Object kind | ForEach-Object { "  {0}: {1}" -f $_.Name, $_.Count }
