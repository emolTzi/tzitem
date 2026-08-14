package kamkeel.plugin.Items.Misc;

import kamkeel.plugin.Enum.Items.EnumEnergy;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;

public class Energy extends Item implements ItemVariantTexture {

    public Energy(){

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.weaponTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumEnergy energy = EnumEnergy.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX +  energy.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumEnergy energy: EnumEnergy.values()){
            list.add(new ItemStack(this, 1, energy.getMeta()));
        }
    }

    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumEnergy.values().length) {
            meta = 0;
        }
        return "plug:energy/" + EnumEnergy.values()[meta].getName().toLowerCase();
    }

    @Override
    public int getVariantCount() {
        return EnumEnergy.values().length;
    }
}
