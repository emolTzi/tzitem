package kamkeel.plugin.Blocks;

import kamkeel.plugin.TileEntity.TileEntityBlockColorData;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockColorData extends Block implements ITileEntityProvider
{
    public BlockColorData() {
        super(Material.AIR);
        this.setLightLevel(0.0f);
        this.setLightOpacity(0);
        this.setUnlocalizedName("plug:datablock");
        this.setHardness(0.0f);
    }

    public static int dataBlockX(final int x) {
        return x >> 4 << 4;
    }

    public static int dataBlockY(final int y) {
        return 255;
    }

    public static int dataBlockZ(final int z) {
        return z >> 4 << 4;
    }

    public static BlockPos dataBlockPos(final int x, final int y, final int z) {
        return new BlockPos(dataBlockX(x), dataBlockY(y), dataBlockZ(z));
    }

    public static float[] getColorData(final IBlockAccess world, final BlockPos pos) {
        return getColorData(world, pos, getMetaFromWorld(world, pos));
    }

    public static int getMetaFromWorld(final IBlockAccess world, final BlockPos pos) {
        return world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
    }

    public static float[] getColorData(final IBlockAccess world, final BlockPos pos, final int metadata) {
        BlockPos dataPos = dataBlockPos(pos.getX(), pos.getY(), pos.getZ());
        TileEntityBlockColorData datablock = null;
        if (world.getTileEntity(dataPos) instanceof TileEntityBlockColorData) {
            datablock = (TileEntityBlockColorData)world.getTileEntity(dataPos);
            return datablock.palette[metadata];
        }
        return BlockColor.initColor[metadata];
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, net.minecraft.util.EnumFacing side) {
        return false;
    }

    @Override
    public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBlockColorData();
    }
}
