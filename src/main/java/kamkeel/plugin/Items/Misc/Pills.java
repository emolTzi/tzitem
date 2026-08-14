package kamkeel.plugin.Items.Misc;

import kamkeel.plugin.Enum.Items.EnumPills;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;

public class Pills extends Item implements ItemVariantTexture {

    public Pills(){

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.miscTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumPills coin = EnumPills.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX + "pills_" + coin.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumPills coin: EnumPills.values()){
            list.add(new ItemStack(this, 1, coin.getMeta()));
        }
    }

    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumPills.values().length) {
            meta = 0;
        }
        return "plug:pills/pills_" + EnumPills.values()[meta].getName().toLowerCase();
    }

    @Override
    public int getVariantCount() {
        return EnumPills.values().length;
    }
}
