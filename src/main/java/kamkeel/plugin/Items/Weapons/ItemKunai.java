package kamkeel.plugin.Items.Weapons;

import kamkeel.plugin.Entity.EntityProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ItemKunai extends ItemPluginWeaponInterface{

    public ItemKunai(int par1, Item.ToolMaterial tool) {
        super(par1, tool);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World world, net.minecraft.entity.EntityLivingBase entityLiving, int par4)
    {
        if (!(entityLiving instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) entityLiving;
        if(world.isRemote){
            player.swingArm(EnumHand.MAIN_HAND);
            return;
        }
        EntityProjectile projectile = new EntityProjectile(world, player, par1ItemStack, false);
        projectile.damage = getAttackDamage();
        projectile.destroyedOnEntityHit = false;
        projectile.canBePickedUp = !player.capabilities.isCreativeMode;
        projectile.setIs3D(true);
        projectile.setStickInWall(true);
        projectile.setHasGravity(true);
        projectile.setSpeed(12);
        projectile.shoot(1);

        if(!player.capabilities.isCreativeMode){
            par1ItemStack.damageItem(1, player);
            if(par1ItemStack.isEmpty())
                return;
            player.inventory.mainInventory.set(player.inventory.currentItem, ItemStack.EMPTY);
            //item.dropItem = par1ItemStack;
        }

        world.spawnEntity(projectile);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    @Override
    public void renderSpecial(){
        GL11.glScalef(0.4f, 0.4f, 0.4f);
        GL11.glTranslatef(-0.4F, 0.5f, 0.1f);
    }

    @Override
    public boolean shouldRotateAroundWhenRendering(){
        return true;
    }

}
