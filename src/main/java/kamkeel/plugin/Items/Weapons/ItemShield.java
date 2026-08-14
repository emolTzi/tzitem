package kamkeel.plugin.Items.Weapons;

import kamkeel.plugin.Enum.Items.EnumToolMaterials;
import kamkeel.plugin.Items.PluginItemInterface;
import kamkeel.plugin.PluginMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;


public class ItemShield extends PluginItemInterface {
	public EnumToolMaterials material;

	public ItemShield(int par1, EnumToolMaterials material) {
		super(par1);
		this.material = material;
		this.setMaxDamage(material.getMaxUses());
		setCreativeTab(PluginMod.weaponTab);
	}

	@Override
	public void renderSpecial(){
		GL11.glScalef(0.6f, 0.6f,0.6f);
		GL11.glTranslatef(0f, 0f, -0.2f);
		GL11.glRotatef(-6, 0, 1, 0);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack){
		return EnumAction.BLOCK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
		ItemStack itemStack = player.getHeldItem(hand);
		player.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack){
		return 72000;
	}
}
