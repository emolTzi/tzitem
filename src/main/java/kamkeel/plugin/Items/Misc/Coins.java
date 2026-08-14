package kamkeel.plugin.Items.Misc;

import kamkeel.plugin.Enum.Items.EnumCoins;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;

public class Coins extends Item implements ItemVariantTexture {

    public Coins(){

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.miscTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumCoins coin = EnumCoins.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX + "coins_" + coin.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumCoins coin: EnumCoins.values()){
            list.add(new ItemStack(this, 1, coin.getMeta()));
        }
    }

    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumCoins.values().length) {
            meta = 0;
        }
        return "plug:coins/coins_" + EnumCoins.values()[meta].getName().toLowerCase();
    }

    @Override
    public int getVariantCount() {
        return EnumCoins.values().length;
    }
}
