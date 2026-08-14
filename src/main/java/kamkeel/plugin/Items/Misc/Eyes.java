package kamkeel.plugin.Items.Misc;

import kamkeel.plugin.Enum.Items.EnumEyes;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;

public class Eyes extends Item implements ItemVariantTexture {

    public Eyes(){

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.miscTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumEyes coin = EnumEyes.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX + "eyes_" + coin.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumEyes coin: EnumEyes.values()){
            list.add(new ItemStack(this, 1, coin.getMeta()));
        }
    }

    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumEyes.values().length) {
            meta = 0;
        }
        return "plug:eyes/eyes_" + EnumEyes.values()[meta].getName().toLowerCase();
    }

    @Override
    public int getVariantCount() {
        return EnumEyes.values().length;
    }
}
