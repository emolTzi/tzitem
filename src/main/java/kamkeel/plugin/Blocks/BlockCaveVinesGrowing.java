package kamkeel.plugin.Blocks;

import kamkeel.plugin.PluginMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCaveVinesGrowing extends BlockBush
{
    public static final String[] types;
    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 1);

    private static final AxisAlignedBB CAVE_VINES_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 1.0D, 0.9D);

    public BlockCaveVinesGrowing() {
        super(Material.PLANTS);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(PluginMod.blocksTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta < 0 || meta > 1) {
            meta = 1;
        }
        return this.getDefaultState().withProperty(VARIANT, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CAVE_VINES_AABB;
    }

    @Override
    public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random random) {
        final BlockPos below = pos.down();
        if (random.nextInt(10) == 0 && world.isAirBlock(below)) {
            int height;
            for (height = 1; world.getBlockState(pos.up(height)).getBlock() == this; ++height) {}
            if (height < 20) {
                world.setBlockState(pos, getStateFromMeta(1), 2);
                world.setBlockState(below, getStateFromMeta(0), 2);
            }
        }
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            if (player.posY - 0.0 >= pos.getY()) {
                player.moveForward = 0.0f;
                if (player.motionY < -0.15) {
                    player.motionY = -0.15;
                }
                if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) && player.motionY < 0.2) {
                    player.motionY = 0.2;
                }
            }
            if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak) && (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump))) {
                player.motionY = 0.2;
            }
            else if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
                player.motionX = 0.0;
                player.motionY = 0.08;
                player.motionZ = 0.0;
            }
        }
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int i = 0; i < BlockCaveVinesGrowing.types.length; ++i) {
            if (i != 1) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighbour, BlockPos fromPos) {
        this.checkBlockCoordValid(world, pos);
        if (world.isAirBlock(pos.down()) && world.getBlockState(pos).getBlock() == ModBlocks.caveVinesGrowing) {
            world.setBlockState(pos, getStateFromMeta(0), 2);
        }
    }

    public final void checkBlockCoordValid(final World world, final BlockPos pos) {
        if (!this.canBlockStay(world, pos, world.getBlockState(pos))) {
            this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
            world.setBlockToAir(pos);
        }
    }

    @Override
    public boolean canBlockStay(final World world, final BlockPos pos, final IBlockState state) {
        return canPlaceRootBelow(world, pos.up());
    }

    public static boolean canPlaceRootBelow(final World world, final BlockPos pos) {
        final Block blockAbove = world.getBlockState(pos).getBlock();
        return blockAbove.getMaterial(world.getBlockState(pos)) == Material.ROCK || blockAbove.getMaterial(world.getBlockState(pos)) == Material.WOOD || blockAbove == ModBlocks.caveVinesGrowing || blockAbove == Blocks.SOUL_SAND || blockAbove instanceof BlockGrass || blockAbove instanceof BlockDirt;
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos, world.getBlockState(pos));
    }

    @Override
    public int damageDropped(IBlockState state) {
        if (state.getValue(VARIANT) == 1) {
            return 0;
        }
        return state.getValue(VARIANT);
    }

    static {
        types = new String[] { "head", "body" };
    }
}
