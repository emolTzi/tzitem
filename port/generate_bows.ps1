$ErrorActionPreference = 'Stop'
$port = "C:\Development\tzitem-1.21.1\port"
$root = "C:\Development\tzitem-1.21.1\src\main\resources\assets\plug"
$enc = [System.Text.UTF8Encoding]::new($false)

$items = Get-Content "$port\items.json" -Raw | ConvertFrom-Json
$bows = $items | Where-Object { $_.kind -eq 'bow' } | Select-Object -ExpandProperty name

$count = 0
foreach ($b in $bows) {
    # Pulling models
    foreach ($p in @(0, 1, 2)) {
        $model = @{
            parent = "minecraft:item/generated"
            textures = @{ layer0 = "plug:item/bows/${b}_pulling_${p}" }
        } | ConvertTo-Json -Depth 3
        [System.IO.File]::WriteAllText("$root\models\item\${b}_pulling_${p}.json", $model, $enc)
        $count++
    }
    # Base model with overrides
    $base = @{
        parent = "minecraft:item/generated"
        textures = @{ layer0 = "plug:item/bows/${b}_standby" }
        overrides = @(
            @{ predicate = @{ pulling = 1 }; model = "plug:item/${b}_pulling_0" }
            @{ predicate = @{ pulling = 1; pull = 0.65 }; model = "plug:item/${b}_pulling_1" }
            @{ predicate = @{ pulling = 1; pull = 0.9 }; model = "plug:item/${b}_pulling_2" }
        )
    } | ConvertTo-Json -Depth 6
    [System.IO.File]::WriteAllText("$root\models\item\${b}.json", $base, $enc)
    $count++
}
Write-Output "Generated $count bow model files for $($bows.Count) bows"
