package kamkeel.plugin.Items.Misc;

import kamkeel.plugin.Entity.EntityProjectile;
import kamkeel.plugin.Items.Weapons.ItemPluginWeaponInterface;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class VoidRasenshuriken extends ItemPluginWeaponInterface {

    public VoidRasenshuriken(int par1, ToolMaterial material) {
        super(par1, material);
        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(1);
        this.setCreativeTab(PluginMod.weaponTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return LocalizationHelper.ITEM_PREFIX + "voidrasenshuriken";
    }

    @Override
    public void renderSpecial(){
        GL11.glScalef(1.3f, 1.3f,1.3f);
        // GL11.glTranslatef(-0.2f, 0.7f, -0.7f);
        // GL11.glRotatef(120, 1, 0, 0);
        // GL11.glRotatef(-30, 0, 1, 0);
    };

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
        projectile.destroyedOnEntityHit = true;
        projectile.canBePickedUp = !player.capabilities.isCreativeMode;
        projectile.setIs3D(true);
        projectile.setStickInWall(true);
        projectile.setHasGravity(true);
        projectile.setSpeed(15);
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
    public boolean shouldRotateAroundWhenRendering(){
        return false;
    }

}
