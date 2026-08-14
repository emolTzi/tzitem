package kamkeel.plugin.Blocks;

import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BlockBarrel extends Block {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public String name;

    public BlockBarrel(String _name) {
        super(Material.WOOD);
        this.setCreativeTab(PluginMod.blocksTab);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(2.5F);
        this.name = _name;
        this.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + name + "_barrel");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta >= 0 && meta < 6) {
            return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
        }
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}
