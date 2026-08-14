package kamkeel.plugin.Items.Cards;

import kamkeel.plugin.Enum.Items.Suits.EnumClubsCard;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;

public class ClubsCard extends Item implements ItemVariantTexture {

    public ClubsCard(){

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.cardsTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumClubsCard card = EnumClubsCard.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX + "clubs_" + card.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumClubsCard card: EnumClubsCard.values()){
            list.add(new ItemStack(this, 1, card.getMeta()));
        }
    }

    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumClubsCard.values().length) {
            meta = 0;
        }
        return "plug:cards/clubs_" + EnumClubsCard.values()[meta].getName().toLowerCase();
    }

    @Override
    public int getVariantCount() {
        return EnumClubsCard.values().length;
    }
}
