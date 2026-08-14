package kamkeel.plugin.Items.Weapons;

import kamkeel.plugin.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumParticleTypes;

import com.google.common.collect.Multimap;

public class ItemGlass extends ItemSword {

    // Huge attack speed modifier: 1.7.10 had no attack cooldown, so make swings instant in 1.12.2
    private static final AttributeModifier NO_COOLDOWN = new AttributeModifier(java.util.UUID.fromString("fa233e1c-4180-4865-b01b-bcce9785aca4"), "Weapon modifier (no cooldown)", 1024.0D, 0);

    public ItemGlass(Item.ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        setCreativeTab(PluginMod.weaponTab);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), NO_COOLDOWN);
        }
        return multimap;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return false;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
        boolean result = super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
        if (result) {
            par1ItemStack.damageItem(1000, par3EntityLiving);
        }

        return result;
    }

    /**
     * Called when the player Left Clicks (attacks) an entity.
     * Processed before damage is done, if return value is true further processing is canceled
     * and the entity is not attacked.
     *
     * @param stack The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (player.world.isRemote) {
            // glass shatter animation!
            for (int var1 = 0; var1 < 20; ++var1) {
                double px = entity.posX + itemRand.nextFloat() * entity.width * 2.0F - entity.width;
                double py = entity.posY + itemRand.nextFloat() * entity.height;
                double pz = entity.posZ + itemRand.nextFloat() * entity.width * 2.0F - entity.width;
                player.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, px, py, pz, 0, 0, 0, Block.getStateId(Blocks.STAINED_GLASS.getDefaultState()));
            }

            player.playSound(Blocks.GLASS.getSoundType().getBreakSound(), 1F, 0.5F);
        }
        return false;
    }

    @Override
    public Item setUnlocalizedName(String name){
        return super.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + name);
    }
}
