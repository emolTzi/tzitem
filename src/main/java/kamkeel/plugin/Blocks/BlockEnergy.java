package kamkeel.plugin.Blocks;

import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import kamkeel.plugin.Util.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockEnergy extends Block
{
    public static final int META_MAX = 15;

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, META_MAX);

    private static final float[][] cols;

    protected BlockEnergy()
    {
        super(Material.GROUND);
        this.setLightLevel(1.0F);
        this.setHardness(1.8F);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 0);
        this.setCreativeTab(PluginMod.blocksTab);
        this.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + "energyBlock");
        this.setDefaultState(this.blockState.getBaseState().withProperty(META, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta < 0 || meta > META_MAX) {
            meta = 0;
        }
        return this.getDefaultState().withProperty(META, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    public static int getLightLevel(final int metadata) {
        return (int)((BlockEnergy.cols[metadata][0] + BlockEnergy.cols[metadata][1] + BlockEnergy.cols[metadata][2]) / 3.0f * 15.0f);
    }

    @Override
    public int getLightValue(IBlockState state) {
        return getLightLevel(state.getValue(META));
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return true;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for(int meta = 0; meta <= META_MAX; ++meta) {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return ColorUtil.metaToMapColorEnergy(state.getValue(META));
    }

    /** Model file name for a metadata value (e.g. "energy_block_blood_red"). */
    public String getModelNameForMeta(int meta) {
        if (meta < 0 || meta > META_MAX) {
            meta = 0;
        }
        return "energy_block_" + ColorUtil.energyToString(meta);
    }

    static {
        cols = new float[][] { { 1.0f, 1.0f, 1.0f }, { 1.0f, 0.5f, 0.0f }, { 1.0f, 0.0f, 1.0f }, { 0.0f, 0.5f, 0.85f }, { 1.0f, 1.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, { 1.0f, 0.6f, 0.65f }, { 0.5f, 0.5f, 0.5f }, { 0.8f, 0.8f, 0.8f }, { 0.0f, 1.0f, 1.0f }, { 0.7f, 0.2f, 1.0f }, { 0.0f, 0.0f, 1.0f }, { 0.5f, 0.2f, 0.0f }, { 0.0f, 0.6f, 0.0f }, { 1.0f, 0.0f, 0.0f }, { 0.0f, 0.0f, 0.0f } };
    }

}
