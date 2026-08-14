package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.IBlockEnum;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public abstract class BlockGeneric<T extends Enum<T> & IBlockEnum> extends Block {

    protected final T[] variants;
    protected final String blockName;

    public BlockGeneric(String blockName, Class<T> enumClass, Material material) {
        super(material);
        this.blockName = blockName;
        this.variants = enumClass.getEnumConstants();
        this.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + blockName);
        this.setCreativeTab(PluginMod.blocksTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(getVariantProperty(), variants[0]));
    }

    /** The variant property, provided by the subclass as a static field. */
    protected abstract PropertyEnum<T> getVariantProperty();

    @Override
    protected abstract BlockStateContainer createBlockState();

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta >= 0 && meta < variants.length) {
            return this.getDefaultState().withProperty(getVariantProperty(), variants[meta]);
        }
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(getVariantProperty()).getMeta();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(getVariantProperty()).getMeta();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (T variant : variants) {
            list.add(new ItemStack(this, 1, variant.getMeta()));
        }
    }

    // Per-variant harvest levels, mirroring the 1.7.10 setHarvestLevel(tool, level, meta) calls
    @Override
    public String getHarvestTool(IBlockState state) {
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return state.getValue(getVariantProperty()).getHarvestLevel();
    }

    /** Model file name for a metadata value (e.g. "dark_impure"). */
    public String getModelNameForMeta(int meta) {
        if (meta < 0 || meta >= variants.length) {
            meta = 0;
        }
        return blockName + "_" + variants[meta].getName().toLowerCase();
    }

    /** Number of metadata variants. */
    public int getModelCount() {
        return variants.length;
    }
}
