package kamkeel.plugin.Blocks;

import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockPlugStair extends BlockStairs
{

    public String textureName;

    public BlockPlugStair(Block b, int meta, String name)
    {
        super(b.getStateFromMeta(meta));
        this.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + "stair_" + name);
        this.setSoundType(b.getSoundType());
        this.setLightOpacity(0);
        this.setCreativeTab(PluginMod.blocksTab);
    }

}
