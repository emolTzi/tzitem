package kamkeel.plugin.Items.Cards;

import kamkeel.plugin.Enum.Items.Suits.EnumAddonCard;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;

public class AddonCard extends Item implements ItemVariantTexture {

    public AddonCard(){

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.cardsTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumAddonCard card = EnumAddonCard.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX + "cards_" + card.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumAddonCard card: EnumAddonCard.values()){
            list.add(new ItemStack(this, 1, card.getMeta()));
        }
    }

    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumAddonCard.values().length) {
            meta = 0;
        }
        return "plug:cards/cards_" + EnumAddonCard.values()[meta].getName().toLowerCase();
    }

    @Override
    public int getVariantCount() {
        return EnumAddonCard.values().length;
    }
}
