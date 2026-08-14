package kamkeel.plugin.Items.Misc;

import kamkeel.plugin.Enum.Items.EnumArtifacts;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;

public class Artifacts extends Item implements ItemVariantTexture {

    public Artifacts(){

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.miscTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumArtifacts artifact = EnumArtifacts.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX + artifact.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumArtifacts artifact: EnumArtifacts.values()){
            list.add(new ItemStack(this, 1, artifact.getMeta()));
        }
    }

    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumArtifacts.values().length) {
            meta = 0;
        }
        return "plug:artifacts/" + EnumArtifacts.values()[meta].getName().toLowerCase();
    }

    @Override
    public int getVariantCount() {
        return EnumArtifacts.values().length;
    }
}
