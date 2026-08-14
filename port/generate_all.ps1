$ErrorActionPreference = 'Stop'
$port = "C:\Development\tzitem-1.21.1\port"
$root = "C:\Development\tzitem-1.21.1\src\main\resources\assets\plug"
$items = Get-Content "$port\items.json" -Raw | ConvertFrom-Json
$texRoot = "$root\textures\item"
$enc = [System.Text.UTF8Encoding]::new($false)

$modelDir = "$root\models\item"
New-Item -ItemType Directory -Force -Path $modelDir | Out-Null

# ---- 1) Item models ----
$modelCount = 0
foreach ($it in $items) {
    $tex = $it.tex
    if ($it.kind -eq 'bow') {
        if (Test-Path "$texRoot\$tex`_standby.png") { $tex = "$tex`_standby" }
    }
    $parent = if ($it.kind -eq 'weapon') { "minecraft:item/handheld" } else { "minecraft:item/generated" }
    $json = @{
        parent = $parent
        textures = @{ layer0 = "plug:item/$tex" }
    } | ConvertTo-Json -Depth 3
    $outPath = Join-Path $modelDir ($it.name + ".json")
    [System.IO.File]::WriteAllText($outPath, $json, $enc)
    $modelCount++
}
Write-Output "Generated item models: $modelCount"

# ---- 2) ModItems.java ----
$sb = New-Object System.Text.StringBuilder

[void]$sb.AppendLine('package kamkeel.plugin.Items;')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('import kamkeel.plugin.Blocks.ModBlocks;')
[void]$sb.AppendLine('import kamkeel.plugin.ModRegistries;')
[void]$sb.AppendLine('import net.minecraft.tags.BlockTags;')
[void]$sb.AppendLine('import net.minecraft.world.item.Item;')
[void]$sb.AppendLine('import net.minecraft.world.item.SwordItem;')
[void]$sb.AppendLine('import net.minecraft.world.item.Tier;')
[void]$sb.AppendLine('import net.minecraft.world.item.crafting.Ingredient;')
[void]$sb.AppendLine('import net.neoforged.neoforge.common.SimpleTier;')
[void]$sb.AppendLine('import net.neoforged.neoforge.registries.DeferredItem;')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('import java.util.ArrayList;')
[void]$sb.AppendLine('import java.util.List;')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('/**')
[void]$sb.AppendLine(' * Item registration. Ported from the 1.12.2 ModItems/ModWeapons registries;')
[void]$sb.AppendLine(' * every metadata variant of the old items is registered as a separate item.')
[void]$sb.AppendLine(' */')
[void]$sb.AppendLine('public final class ModItems {')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('    private ModItems() {}')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('    /** Every registered item, in registration order. */')
[void]$sb.AppendLine('    public static final List<DeferredItem<Item>> ALL = new ArrayList<>();')
[void]$sb.AppendLine('    /** Items grouped by creative tab. */')
[void]$sb.AppendLine('    public static final List<DeferredItem<Item>> MISC = new ArrayList<>();')
[void]$sb.AppendLine('    public static final List<DeferredItem<Item>> CARDS = new ArrayList<>();')
[void]$sb.AppendLine('    public static final List<DeferredItem<Item>> WEAPONS = new ArrayList<>();')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('    /** Generic weapon tier (1.12.2 PluginMaterial: 20000 uses, 6.0 speed, 7.0 damage). */')
[void]$sb.AppendLine('    public static final Tier WEAPON_TIER = new SimpleTier(')
[void]$sb.AppendLine('            BlockTags.INCORRECT_FOR_IRON_TOOL, 20000, 6.0F, 7.0F, 0,')
[void]$sb.AppendLine('            () -> Ingredient.EMPTY);')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('    private static DeferredItem<Item> simple(String name, List<DeferredItem<Item>> tab) {')
[void]$sb.AppendLine('        DeferredItem<Item> it = ModRegistries.ITEMS.register(name, () -> new Item(new Item.Properties()));')
[void]$sb.AppendLine('        ALL.add(it);')
[void]$sb.AppendLine('        tab.add(it);')
[void]$sb.AppendLine('        return it;')
[void]$sb.AppendLine('    }')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('    private static DeferredItem<Item> weapon(String name, List<DeferredItem<Item>> tab) {')
[void]$sb.AppendLine('        DeferredItem<Item> it = ModRegistries.ITEMS.register(name, () -> new SwordItem(WEAPON_TIER, new Item.Properties()));')
[void]$sb.AppendLine('        ALL.add(it);')
[void]$sb.AppendLine('        tab.add(it);')
[void]$sb.AppendLine('        return it;')
[void]$sb.AppendLine('    }')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('    /** Resolve a registered item by its registry-name path. */')
[void]$sb.AppendLine('    public static DeferredItem<Item> find(String name) {')
[void]$sb.AppendLine('        for (DeferredItem<Item> it : ALL) {')
[void]$sb.AppendLine('            if (it.getId().getPath().equals(name)) {')
[void]$sb.AppendLine('                return it;')
[void]$sb.AppendLine('            }')
[void]$sb.AppendLine('        }')
[void]$sb.AppendLine('        return null;')
[void]$sb.AppendLine('    }')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('    /** Registers a BlockItem for every registered block. */')
[void]$sb.AppendLine('    public static void registerBlockItems() {')
[void]$sb.AppendLine('        for (var block : ModBlocks.ALL) {')
[void]$sb.AppendLine('            ModRegistries.ITEMS.registerSimpleBlockItem(block);')
[void]$sb.AppendLine('        }')
[void]$sb.AppendLine('    }')
[void]$sb.AppendLine('')
[void]$sb.AppendLine('    public static void register() {')

function Emit($list, $filter, $method, $tabVar) {
    foreach ($it in $items | Where-Object $filter) {
        [void]$sb.AppendLine("        $method(`"$($it.name)`", $tabVar);")
    }
}

[void]$sb.AppendLine('        // ---- Misc: materials ----')
Emit $null { $_.tab -eq 'misc' -and $_.tex -like 'materials/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Misc: weapon parts ----')
Emit $null { $_.tab -eq 'misc' -and $_.tex -like 'weapons/parts/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Misc: extras ----')
Emit $null { $_.tab -eq 'misc' -and $_.tex -notlike 'materials/*' -and $_.tex -notlike 'weapons/parts/*' -and $_.tex -notlike 'pills/*' -and $_.tex -notlike 'coins/*' -and $_.tex -notlike 'balls/*' -and $_.tex -notlike 'eyes/*' -and $_.tex -notlike 'apples/*' -and $_.tex -notlike 'artifacts/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Misc: pills ----')
Emit $null { $_.tex -like 'pills/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Misc: coins ----')
Emit $null { $_.tex -like 'coins/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Misc: balls ----')
Emit $null { $_.tex -like 'balls/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Misc: eyes ----')
Emit $null { $_.tex -like 'eyes/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Misc: apples (food) ----')
Emit $null { $_.tex -like 'apples/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Misc: artifacts ----')
Emit $null { $_.tex -like 'artifacts/*' } 'simple' 'MISC'
[void]$sb.AppendLine('        // ---- Cards ----')
Emit $null { $_.tab -eq 'cards' } 'simple' 'CARDS'
[void]$sb.AppendLine('        // ---- Weapons: energy attacks ----')
Emit $null { $_.tab -eq 'weapons' -and $_.tex -like 'energy/*' } 'simple' 'WEAPONS'
[void]$sb.AppendLine('        // ---- Weapons ----')
Emit $null { $_.tab -eq 'weapons' -and $_.kind -eq 'weapon' } 'weapon' 'WEAPONS'
[void]$sb.AppendLine('        // ---- Weapons: bows ----')
Emit $null { $_.kind -eq 'bow' } 'simple' 'WEAPONS'

[void]$sb.AppendLine('    }')
[void]$sb.AppendLine('}')

$javaPath = "C:\Development\tzitem-1.21.1\src\main\java\kamkeel\plugin\Items\ModItems.java"
[System.IO.File]::WriteAllText($javaPath, $sb.ToString(), $enc)
Write-Output "Generated ModItems.java ($($items.Count) items)"
