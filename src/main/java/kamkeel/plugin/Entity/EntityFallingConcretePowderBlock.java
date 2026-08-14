package kamkeel.plugin.Entity;

import kamkeel.plugin.Blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityFallingConcretePowderBlock extends EntityFallingBlock {

	private int meta;
	private Block concreteBlock;

	public EntityFallingConcretePowderBlock(World world) {
		super(world);
	}

	public EntityFallingConcretePowderBlock(World world, double posX, double posY, double posZ) {
		this(world, posX, posY, posZ, 0);
	}

	public EntityFallingConcretePowderBlock(World world, double posX, double posY, double posZ, int meta) {
		super(world, posX, posY, posZ, ModBlocks.concretePowder.getStateFromMeta(meta));
		this.concreteBlock = ModBlocks.concreteBlock;
		this.meta = meta;
	}

	public EntityFallingConcretePowderBlock(World world, double posX, double posY, double posZ, Block concrete, int meta) {
		super(world, posX, posY, posZ, concrete.getStateFromMeta(meta));
		this.concreteBlock = concrete;
		this.meta = meta;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ));

		if(this.world.getBlockState(pos).getMaterial() == Material.WATER
		|| this.world.getBlockState(pos.east()).getMaterial() == Material.WATER
		|| this.world.getBlockState(pos.west()).getMaterial() == Material.WATER
		|| this.world.getBlockState(pos.south()).getMaterial() == Material.WATER
		|| this.world.getBlockState(pos.north()).getMaterial() == Material.WATER) {
			IBlockState concreteState = this.concreteBlock.getStateFromMeta(this.meta);
			this.world.setBlockState(pos, concreteState, 3);
			this.setDead();
		}
	}

}
