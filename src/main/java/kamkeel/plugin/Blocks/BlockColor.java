package kamkeel.plugin.Blocks;

import kamkeel.plugin.PluginMod;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class BlockColor extends Block {
    public static float[][] initColor = new float[16][3];

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

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);

    public int curMetadata = 0;
    public String oreName;
    public Block baseBlock;
    public String textureName;

    public BlockColor(Block b, String orename, String texture){
        super(b.getMaterial(b.getDefaultState()));
        setHardness(ObfuscationReflectionHelper.getPrivateValue(Block.class, b, "blockHardness", "field_149782_v"));
        setResistance(ObfuscationReflectionHelper.getPrivateValue(Block.class, b, "blockResistance", "field_149781_w"));
        setSoundType(b.getSoundType());
        this.textureName = texture;
        setUnlocalizedName("color_" + b.getUnlocalizedName().substring(5));
        setLightLevel(b.getLightValue(b.getDefaultState()) / 15.0F);
        setLightOpacity(b.getLightOpacity(b.getDefaultState()));
        this.setCreativeTab(PluginMod.blocksTab);
        this.oreName = orename;
        this.baseBlock = b;
        this.setDefaultState(this.blockState.getBaseState().withProperty(META, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta < 0 || meta > 15) {
            meta = 0;
        }
        return this.getDefaultState().withProperty(META, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    public static int getRenderColor(int meta) {
        float[] col = initColor[meta];
        return (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int j = 0; j < 16; j++)
            list.add(new ItemStack(this, 1, j));
    }

}
