$ErrorActionPreference = 'Stop'
$root = "C:\Development\tzitem-1.21.1\src\main\resources\assets\plug"
$enc = [System.Text.UTF8Encoding]::new($false)

$concreteColors = @('burgundy','caramel','chocolate','denim','haze','mint','peanut','clover','pearl','mustard','sky_blue','periwinkle','peach','plum','avocado','red_brown','blood','seafoam','mauve','seaweed','carbon','indigo','khaki','ash','ivy','ivory','camel','salmon')
$energyColors = @('blood_red','baby_blue','dark_blue','deep_blue','green','hot_pink','lemon','light_blue','lime','magenta','orange','purple','red','teal','turquoise','yellow')

function Write-Blockstate($name, $model) {
    $json = @{ variants = @{ "" = @{ model = "plug:block/$model" } } } | ConvertTo-Json -Depth 4
    [System.IO.File]::WriteAllText("$root\blockstates\$name.json", $json, $enc)
}
function Write-ItemModel($name, $blockModel) {
    $json = @{ parent = "plug:block/$blockModel" } | ConvertTo-Json -Depth 3
    [System.IO.File]::WriteAllText("$root\models\item\$name.json", $json, $enc)
}

$count = 0
# Concrete + powder
foreach ($c in $concreteColors) {
    Write-Blockstate "concrete_$c" "concrete_$c"
    Write-ItemModel "concrete_$c" "concrete_$c"
    Write-Blockstate "concrete_powder_$c" "concrete_powder_$c"
    Write-ItemModel "concrete_powder_$c" "concrete_powder_$c"
    $count += 4
}
# Energy
foreach ($e in $energyColors) {
    Write-Blockstate "energy_block_$e" "energy_block_$e"
    Write-ItemModel "energy_block_$e" "energy_block_$e"
    $count += 2
}
# Cave vines (blockstates already exist; just item models)
Write-ItemModel "cave_vines" "cave_vines_head"
Write-ItemModel "cave_vines_growing" "cave_vines_head"
$count += 2

Write-Output "Generated $count files (blockstates + item models)"

# Remove old meta-based blockstates that are now split
$old = @('concrete.json','concrete2.json','concrete_powder.json','concrete_powder2.json','energy_block.json')
foreach ($o in $old) {
    $p = "$root\blockstates\$o"
    if (Test-Path $p) { Remove-Item $p; Write-Output "Removed old $o" }
}
