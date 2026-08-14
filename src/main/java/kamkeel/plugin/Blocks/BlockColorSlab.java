package kamkeel.plugin.Blocks;

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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class BlockColorSlab extends BlockSlab
{

    public String name;
    public static float[][] initColor = new float[16][3];
    public int metaColor = 0;
    private String blockName;
    private boolean doubleSlab;
    private BlockColorSlab dropSlab;
    private static final String[] metaColorNames = new String[] {"White", "Orange", "Magenta", "Light_blue", "Yellow", "Lime", "Pink", "Gray", "Light_gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"};

    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 0);

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

    public BlockColorSlab(boolean isDoubleSlab, Material material, String blockName, String textureLoc, int _metaColor)
    {
        super(material);

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
            this.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + blockName + "SSlab." + _metaColor);
            this.setCreativeTab(PluginMod.blocksTab);
        }
        else{
            //LocalizationHelper.MOD_PREFIX +
            this.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + blockName + "DSlab." + _metaColor);
        }
        this.doubleSlab = isDoubleSlab;
        this.blockName = textureLoc;
        this.metaColor = _metaColor;

        this.useNeighborBrightness = true;
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM).withProperty(VARIANT, 0));
    }

    public String getTextureLoc() {
        return this.blockName;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        return iblockstate.withProperty(VARIANT, 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(HALF) == EnumBlockHalf.TOP) {
            i |= 8;
        }
        return i;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int j = 0; j < 1; j++)
            list.add(new ItemStack(this, 1, j));
    }

    public static int getRenderColor(int meta) {
        float[] col = initColor[meta];
        return (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F);
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        if(this.doubleSlab){
            return (new StringBuilder()).append("1").toString();
        }
        return (new StringBuilder()).append("0").toString();
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
        return 0;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        if(dropSlab != null){
            return Item.getItemFromBlock(dropSlab);
        }
        return Item.getItemFromBlock(this);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        Block block;
        if(dropSlab != null){
            block = dropSlab;
        }
        else{
            block = this;
        }
        return new ItemStack(block, 1, 0);
    }

    public void setDropSlab(BlockColorSlab dropSlab) {
        this.dropSlab = dropSlab;
    }

    // 1.12.2: ItemSlab handles double->single placement; keep both halves non-colliding like vanilla
    @Override
    public boolean isFullCube(IBlockState state) {
        return this.isDouble();
    }
}
