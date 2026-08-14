package kamkeel.plugin.Items.Cards;

import kamkeel.plugin.Enum.Items.Suits.EnumDiamondCard;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;

public class DiamondCard extends Item implements ItemVariantTexture {

    public DiamondCard(){

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.cardsTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumDiamondCard card = EnumDiamondCard.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX + "diamond_" + card.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumDiamondCard card: EnumDiamondCard.values()){
            list.add(new ItemStack(this, 1, card.getMeta()));
        }
    }

    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumDiamondCard.values().length) {
            meta = 0;
        }
        return "plug:cards/diamond_" + EnumDiamondCard.values()[meta].getName().toLowerCase();
    }

    @Override
    public int getVariantCount() {
        return EnumDiamondCard.values().length;
    }
}
