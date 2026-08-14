package kamkeel.plugin.Blocks.ItemBlock;

import kamkeel.plugin.Util.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockEnergy extends ItemBlock {

    public ItemBlockEnergy(Block block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName() + "." + ColorUtil.energyToString(itemStack.getItemDamage());
    }
}
