package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumDeepCrystal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockDeepCrystal extends BlockGeneric<EnumDeepCrystal> {

    public static final PropertyEnum<EnumDeepCrystal> VARIANT = PropertyEnum.create("variant", EnumDeepCrystal.class);

    @Override
    protected PropertyEnum<EnumDeepCrystal> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public BlockDeepCrystal(){
        super("deep_crystal", EnumDeepCrystal.class, Material.GLASS);

        this.setLightOpacity(0);
        this.setHardness(0.3f);
        this.setResistance(0.3f);
        this.setLightLevel(0.4f);
        this.setSoundType(SoundType.GLASS);
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 1;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        EnumFacing face = side.getOpposite();
        BlockPos mePos = pos.offset(face);
        IBlockState meState = world.getBlockState(mePos);
        IBlockState otherState = world.getBlockState(pos);
        return meState.getBlock() != otherState.getBlock() || meState.getValue(VARIANT) != otherState.getValue(VARIANT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
