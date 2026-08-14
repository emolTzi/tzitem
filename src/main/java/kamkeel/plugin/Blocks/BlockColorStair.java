package kamkeel.plugin.Blocks;

import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;

public class BlockColorStair extends BlockStairs
{

    public static float[][] initColor = new float[16][3];
    public int curMetadata = 0;
    public String textureName;

    static {
        float saturation = 0.85F;
        for (int i = 0; i < 16; i++) {
            float[] dye = EnumDyeColor.values()[i].getColorComponentValues();
            float r = dye[0];
            float g = dye[1];
            float b = dye[2];
            float m = (r + g + b) / 3.0F * (1.0F - saturation);
            initColor[i][0] = r * saturation + m;
            initColor[i][1] = g * saturation + m;
            initColor[i][2] = b * saturation + m;
        }
    }

    public BlockColorStair(final net.minecraft.block.Block b, int metaData, String name)
    {
        super(b.getStateFromMeta(metaData));
        this.curMetadata = metaData;
        this.setLightOpacity(0);
        this.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + name + "Stair." + metaData);
        this.setCreativeTab(PluginMod.blocksTab);
    }

    public static int getRenderColor(int meta) {
        float[] col = initColor[meta];
        return (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F);
    }
}
