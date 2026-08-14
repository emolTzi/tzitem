package kamkeel.plugin.Blocks;

import net.minecraft.block.material.Material;

public abstract class BlockBase extends BlockGeneric {

    public BlockBase(String blockName, Class enumClass) {
        super(blockName, enumClass, Material.ROCK);
    }
}
