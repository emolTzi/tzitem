package kamkeel.plugin.Blocks.ItemBlock;

import kamkeel.plugin.Blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockCaveVines extends ItemColored
{
    private static final String[] types;

    public ItemBlockCaveVines(final Block block) {
        super(block, true);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(final int meta) {
        return meta;
    }

    public String getUnlocalizedName(final ItemStack itemstack) {
        int meta = itemstack.getItemDamage();
        if (meta < 0 || meta >= ItemBlockCaveVines.types.length) {
            meta = 0;
        }
        return super.getUnlocalizedName() + "_" + ItemBlockCaveVines.types[meta];
    }

    @Override
    public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final net.minecraft.block.state.IBlockState newState) {
        int metadata = this.getMetadata(stack.getMetadata());
        if (metadata == 0 && world.getBlockState(pos.up()).getBlock() == ModBlocks.caveVines) {
            world.setBlockState(pos.up(), ModBlocks.caveVines.getStateFromMeta(1), 2);
            world.setBlockState(pos, ModBlocks.caveVines.getStateFromMeta(0), 2);
            stack.shrink(1);
            world.playSound((EntityPlayer)null, (double)(pos.getX() + 0.5f), (double)(pos.getY() + 0.5f), (double)(pos.getZ() + 0.5f), ModBlocks.caveVines.getSoundType().getBreakSound(), SoundCategory.BLOCKS, (ModBlocks.caveVines.getSoundType().getVolume() + 1.0f) / 2.0f, ModBlocks.caveVines.getSoundType().getPitch() * 0.8f);
        }
        return super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
    }

    static {
        types = new String[] { "head", "body" };
    }
}
