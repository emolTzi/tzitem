package kamkeel.plugin.Items.Weapons;

import kamkeel.plugin.Items.ItemRenderInterface;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import kamkeel.plugin.*;

import com.google.common.collect.Multimap;

import org.lwjgl.opengl.GL11;

public class ItemPluginWeaponInterface extends ItemSword implements ItemRenderInterface {

	// Huge attack speed modifier: 1.7.10 had no attack cooldown, so make swings instant in 1.12.2
	private static final AttributeModifier NO_COOLDOWN = new AttributeModifier(java.util.UUID.fromString("fa233e1c-4180-4865-b01b-bcce9785aca3"), "Weapon modifier (no cooldown)", 1024.0D, 0);

	public ItemPluginWeaponInterface(int par1, ToolMaterial material) {
		this(material);
	}
	public ItemPluginWeaponInterface(ToolMaterial material) {
		super(material);
		PluginMod.proxy.registerItem(this);
		setCreativeTab(PluginMod.weaponTab);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, net.minecraft.item.ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if (slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), NO_COOLDOWN);
		}
		return multimap;
	}

	@Override
	public void renderSpecial(){
		GL11.glScalef(0.66f, 0.66f,0.66f);
		GL11.glTranslatef(0.16f, 0.26f, 0.06f);
	}

	@Override
	public Item setUnlocalizedName(String name){
		return super.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + name);
	}


}
