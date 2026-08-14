package kamkeel.plugin.Items;

import kamkeel.plugin.LocalizationHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import kamkeel.plugin.PluginMod;

import org.lwjgl.opengl.GL11;

public class PluginItemInterface extends Item implements ItemRenderInterface {

    public PluginItemInterface(int par1) {
        this();
    }
    public PluginItemInterface() {
        PluginMod.proxy.registerItem(this);
    }

    public void renderSpecial(){
        GL11.glScalef(0.66f, 0.66f,0.66f);
        GL11.glTranslatef(0, 0.3f, 0);
    };

    @Override
    public int getItemEnchantability()
    {
        //return this.toolMaterial.getEnchantability();
        return super.getItemEnchantability();
    }

    @Override
    public Item setUnlocalizedName(String name){
        return super.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + name);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving)
    {
        if(par2EntityLiving.getHealth() <= 0)
            return false;
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }

    public boolean hasItem(EntityPlayer player, Item item) {
        return player.inventory.hasItemStack(new ItemStack(item));
    }

    public boolean consumeItem(EntityPlayer player, Item item) {
        if (!hasItem(player, item)) {
            return false;
        }
        player.inventory.clearMatchingItems(item, -1, 1, null);
        return true;
    }
}
