package kamkeel.plugin.Blocks;

import java.util.List;

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

public class BlockConcrete extends Block {
    public static final int META_MAX = 27;
    public final int META_AMOUNT;
    public final int META_SET;

    public final String blockName;
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);

    public BlockConcrete(int metaSet, String blockName) {
        super(Material.ROCK);
        this.setHardness(1.8F);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 0);
        this.setCreativeTab(PluginMod.blocksTab);

        this.META_SET = metaSet;
        if(16 * (META_SET + 1) > META_MAX){
            this.META_AMOUNT = META_MAX % 16;
        }
        else {
            this.META_AMOUNT = 15;
        }

        this.blockName = blockName;
        this.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + blockName);
        this.setDefaultState(this.blockState.getBaseState().withProperty(META, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta < 0 || meta > META_AMOUNT) {
            meta = 0;
        }
        return this.getDefaultState().withProperty(META, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for(int meta = 0; meta <= META_AMOUNT; ++meta) {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public MapColor getMapColor(IBlockState state, net.minecraft.world.IBlockAccess worldIn, net.minecraft.util.math.BlockPos pos) {
        return ColorUtil.metaToMapColorConcrete(state.getValue(META) + (16 * META_SET));
    }

    /** Model file name for a metadata value (e.g. "concrete_burgundy"). */
    public String getModelNameForMeta(int meta) {
        if (meta < 0 || meta > META_AMOUNT) {
            meta = 0;
        }
        return "concrete_" + ColorUtil.concreteToString(meta + (16 * META_SET));
    }
}
