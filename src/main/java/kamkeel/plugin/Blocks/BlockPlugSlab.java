package kamkeel.plugin.Blocks;

import kamkeel.plugin.Compat.CompatibilityBiomesOPlenty;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPlugSlab extends BlockSlab
{

    public String name;

    public static enum SlabCategory
    {
        REGULAR, BOP;
    }

    public static final String[] regular = new String[] {"barrel"};
    public static final String[] bop = new String[] {"limestone", "limestonesmooth", "siltstone", "siltstonesmooth", "shale", "shalesmooth"};

    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 5);

    private final SlabCategory category;
    private final boolean doubleSlab;

    public BlockPlugSlab(boolean isDoubleSlab, Material material, SlabCategory cat)
    {
        super(material);

        category = cat;
        this.doubleSlab = isDoubleSlab;

        if (material == Material.WOOD)
        {
            this.setHardness(2.0F);
            this.setResistance(5.0F);
            this.setSoundType(SoundType.WOOD);
        }
        else if (material == Material.ROCK)
        {
            this.setHarvestLevel("pickaxe", 0);
            this.setHardness(1.8F);
            this.setSoundType(SoundType.STONE);
        }

        if (!isDoubleSlab)
        {
            this.setCreativeTab(PluginMod.blocksTab);
        }

        this.useNeighborBrightness = true;
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM).withProperty(VARIANT, 0));
    }

    public SlabCategory getCategory() {
        return category;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        return iblockstate.withProperty(VARIANT, getTypeFromMeta(meta) % (VARIANT.getAllowedValues().size()));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(HALF) == EnumBlockHalf.TOP) {
            i |= 8;
        }
        i |= state.getValue(VARIANT) & 7;
        return i;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        int max = 0;

        if (category == SlabCategory.REGULAR) {
            max = 1;
        }
        else if (category == SlabCategory.BOP) {
            max = 6;
        }

        for (int i = 0; i < max; ++i) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        if (category == SlabCategory.REGULAR)
            return (new StringBuilder()).append(regular[getRegularType(meta)]).toString();
        else
            return (new StringBuilder()).append(bop[getBOPType(meta)]).toString();
    }

    @Override
    public boolean isDouble() {
        return this.doubleSlab;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return getTypeFromMeta(stack.getMetadata()) % (VARIANT.getAllowedValues().size());
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state) & 7;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        if (doubleSlab)
        {
            if (this == ModBlocks.regularDoubleSlab)
                return Item.getItemFromBlock(ModBlocks.regularSingleSlab);
            else
                return Item.getItemFromBlock(CompatibilityBiomesOPlenty.bopSingleSlab);
        }
        else
            return Item.getItemFromBlock(this);
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
        int meta = getMetaFromState(state);
        float hardness = blockHardness;

        if (category == SlabCategory.REGULAR)
        {
            switch (getTypeFromMeta(meta))
            {
                case 0:
                case 3:
                    hardness = 1.6F;
                    break;

                case 1:
                case 4:
                    hardness = 1.1F;
                    break;

                case 2:
                    hardness = 1.0F;
                    break;
            }
        }

        return hardness;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        Block block = !doubleSlab ? this : (this == ModBlocks.regularDoubleSlab ? ModBlocks.regularSingleSlab : CompatibilityBiomesOPlenty.bopSingleSlab);

        return new ItemStack(block, 1, getMetaFromState(state) & 7);
    }

    private int getBOPType(int meta)
    {
        meta = getTypeFromMeta(meta);
        if (meta < bop.length)
            return meta;

        return 0;
    }

    private int getRegularType(int meta)
    {
        meta = getTypeFromMeta(meta);
        if (meta < regular.length)
            return meta;

        return 0;
    }

    private static int getTypeFromMeta(int meta)
    {
        return meta & 7;
    }
}
