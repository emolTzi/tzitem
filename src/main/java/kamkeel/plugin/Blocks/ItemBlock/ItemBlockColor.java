package kamkeel.plugin.Blocks.ItemBlock;

import kamkeel.plugin.Blocks.BlockColor;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockColor extends ItemBlock
{

    public ItemBlockColor(final Block par1) {
        super(par1);
        this.setHasSubtypes(true);
    }

    public String getItemStackDisplayName(final ItemStack p_77653_1_) {
        final Block bc = ((BlockColor)this.block).baseBlock;
        final Item i = Item.getItemFromBlock(bc);
        String name;
        if (i == null) {
            name = bc.getUnlocalizedName();
            if (name != null) {
                name = I18n.format(name);
                name = ("" + I18n.format(name + ".name")).trim();
            }
            else {
                name = "";
            }
        }
        else {
            name = new ItemStack(i, 1, 0).getDisplayName();
        }
        return I18n.format("tile.plug.colorBlock." + p_77653_1_.getItemDamage() + ".name").replaceAll("BLOCKNAME", name);
    }
}
