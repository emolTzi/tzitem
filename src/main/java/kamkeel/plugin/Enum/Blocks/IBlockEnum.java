package kamkeel.plugin.Enum.Blocks;

import net.minecraft.util.IStringSerializable;

public interface IBlockEnum extends IStringSerializable {
    int getMeta();
    String getName();
    int getHarvestLevel();
}