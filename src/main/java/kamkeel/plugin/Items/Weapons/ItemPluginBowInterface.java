package kamkeel.plugin.Items.Weapons;

import kamkeel.plugin.Items.ItemRenderInterface;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class ItemPluginBowInterface extends ItemBow implements ItemRenderInterface {

	public String textureName;

	public ItemPluginBowInterface(String name){
		this.textureName = "plug:bows/"+ name;
		setCreativeTab(PluginMod.weaponTab);
		setFull3D();
		PluginMod.proxy.registerBow(this);

		// Register the pull/pulling property getters so the model override chain is evaluated
		this.addPropertyOverride(new ResourceLocation("pulling"), new net.minecraft.item.IItemPropertyGetter() {
			@Override
			@net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
			public float apply(ItemStack stack, net.minecraft.world.World worldIn, net.minecraft.entity.EntityLivingBase entityIn) {
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});
		this.addPropertyOverride(new ResourceLocation("pull"), new net.minecraft.item.IItemPropertyGetter() {
			@Override
			@net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
			public float apply(ItemStack stack, net.minecraft.world.World worldIn, net.minecraft.entity.EntityLivingBase entityIn) {
				if (entityIn == null) {
					return 0.0F;
				}
				ItemStack itemstack = entityIn.getActiveItemStack();
				return !itemstack.isEmpty() && itemstack.getItem() instanceof ItemPluginBowInterface
						? (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F
						: 0.0F;
			}
		});
	}

	@Override
	public Item setUnlocalizedName(String name){
		return super.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + name);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, net.minecraft.entity.EntityLivingBase entityLiving, int itemInUseCount) {
		if (!(entityLiving instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer entityPlayer = (EntityPlayer) entityLiving;
		int charge = this.getMaxItemUseDuration(itemstack) - itemInUseCount;

		boolean hasAmmo = !this.findAmmo(entityPlayer).isEmpty();

		ArrowLooseEvent event = new ArrowLooseEvent(entityPlayer, itemstack, world, charge, hasAmmo);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return;
		}
		charge = event.getCharge();

		boolean isNoPickup = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0;

		if (isNoPickup || entityPlayer.inventory.hasItemStack(new ItemStack(Items.ARROW)))
		{
			float velocity = (float)charge / 20.0F;
			velocity = (velocity * velocity + velocity * 2.0F) / 3.0F;

			if ((double)velocity < 0.1D)
			{
				return;
			}

			if (velocity > 1.0F)
			{
				velocity = 1.0F;
			}

			EntityArrow entityarrow = getArrow(world, entityPlayer, velocity * 2.0F);

			if (velocity == 1.0F)
			{
				entityarrow.setIsCritical(true);
			}

			int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemstack);

			if (powerLevel > 0)
			{
				entityarrow.setDamage(entityarrow.getDamage() + (double)powerLevel * 0.5D + 0.5D);
			}

			int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemstack);

			if (punchLevel > 0)
			{
				entityarrow.setKnockbackStrength(punchLevel);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemstack) > 0)
			{
				entityarrow.setFire(100);
			}

			itemstack.damageItem(1, entityPlayer);
			world.playSound(null, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

			if (isNoPickup)
			{
				entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
			}
			else
			{
				entityPlayer.inventory.clearMatchingItems(Items.ARROW, -1, 1, null);
			}

			if (!world.isRemote)
			{
				world.spawnEntity(entityarrow);
			}
		}
	}

	protected net.minecraft.entity.projectile.EntityTippedArrow getArrow(World world, EntityPlayer entityPlayer, float velocity) {
		net.minecraft.entity.projectile.EntityTippedArrow arrow = new net.minecraft.entity.projectile.EntityTippedArrow(world, entityPlayer);
		// 1.7.10 bow speed factor: velocity * 1.5
		arrow.shoot(entityPlayer, entityPlayer.rotationPitch, entityPlayer.rotationYaw, 0.0F, velocity * 1.5F, 1.0F);
		return arrow;
	}


	@Override
	public void renderSpecial() {

	}
}
