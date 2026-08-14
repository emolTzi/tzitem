package kamkeel.plugin.Items.Misc;

import kamkeel.plugin.Enum.Items.EnumApples;
import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.PluginMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import net.minecraft.util.NonNullList;

public class Apple extends ItemFood implements ItemVariantTexture {


    public Apple(int p_i45341_1_, float p_i45341_2_, boolean p_i45341_3_){
        super(p_i45341_1_, p_i45341_2_, p_i45341_3_);

        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setAlwaysEdible();
        this.setMaxDamage(0);
        this.setCreativeTab(PluginMod.miscTab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){

        int metadata = stack.getItemDamage();
        EnumApples apple = EnumApples.values()[metadata];
        return LocalizationHelper.ITEM_PREFIX + apple.getName().toLowerCase() + "_apple";
    }



    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack p_77613_1_)
    {

        if(p_77613_1_.getItemDamage() == 0){
            return EnumRarity.UNCOMMON;
        }
        else if(p_77613_1_.getItemDamage() == 1 || p_77613_1_.getItemDamage() == 2){
            return EnumRarity.RARE;
        }

        return EnumRarity.EPIC;
    }

    protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_)
    {
        // POTION EFFECTS STUFF

        // Heal Potion TIME * 4 = Health Gained
        // Regen *3 = TIME * 3.2   >>> 5 * 3.2 = 16
        // Regen *4 = TIME * 8.25   >>> 5 * 8.25 = 33

        // Default NOTES: Heal (13) 52 + Regen (3) (45) 144 = 196
        if (!p_77849_2_.isRemote) {

            int dmg = p_77849_1_.getItemDamage();

            // Total = Instant + Regen

            if(dmg == 2){
                // Vintage
                // Total Health = 96 +  192 = 288
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 24, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 3));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
            }
            else if(dmg == 3){
                // Rage
                // Strength: 45s
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 2));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else if(dmg == 4){
                // Mend
                // Total Health = 40 + 288 = 328
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 10, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 700, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
            }
            else if(dmg == 5){
                // Shield
                // Total Health = 24 +  96 = 120
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 3));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 6, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else if(dmg == 6){
                // Buffer
                // Regen: 45s
                // Total Health = 80 +  144 = 224
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 20, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 3));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 900, 9));
            }
            else if(dmg == 7){
                // Swift
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 3));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else if(dmg == 8) {
                // Boost
                // Total Health = 160 +  144 = 304
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 40, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 3));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
            }
            else if(dmg == 9){
                // Alert
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 3600, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.HASTE, 3600, 1));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else if(dmg == 10){
                // Element
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 3600, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 3600, 0));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else if(dmg == 11){
                // Full
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 3600, 0));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else if(dmg == 12){
                // Drunk
                // Total Health = 52 +  165 = 217
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 900, 1));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 3));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 4));


                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
            }
            else if(dmg == 13){
                // Tank
                // Total Health = 24 +  96 = 120
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 900, 1));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 3));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 900, 9));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 6, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else if(dmg == 14){
                // Rotten
                // Total Health = 160 +  96 = 256
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 900, 4));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 40, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 2));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
            }
            else if(dmg == 15){
                // Corrupt
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));

                useRandomEffect(p_77849_3_);
            }
            else if(dmg == 16){
                // Reinforced
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 900, 1));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 5));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else if(dmg == 17){
                // Toxic
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.POISON, 900, 1));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 3600, 0));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 110, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
            }
            else if(dmg == 18){
                // Odd
                // Total Health = 52 +  96 = 148
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1800, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1800, 0));

                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
            }
            else {
                // Total Health = 196
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 13, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 3));
                p_77849_3_.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
            }
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if (!this.isInCreativeTab(tab)) return;
        for (EnumApples apple: EnumApples.values()){
            list.add(new ItemStack(this, 1, apple.getMeta()));
        }
    }

    public static void useRandomEffect(EntityPlayer player){
        int r = player.getRNG().nextInt(20);
        if(r == 1){
            player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 7200, 0));
        }
        else if(r == 2){
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 7200, 1));
        }
        else if(r == 3){
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 900, 3));
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1, 7));
        }
        else if(r == 4){
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 900, 3));
        }
        else if(r == 5){
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 7200, 0));
        }
        else if(r == 6){
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 900, 2));
        }
        else if(r == 7){
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 4));
        }
        else if(r == 8){
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 3));
        }
        else if(r == 9){
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 4));
        }
        else if(r == 10){
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 3));
        }
        else if(r == 11){
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 7));
        }
        else if(r == 12){
            player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 900, 2));
        }
        else if(r == 13){
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 3));
        }
        else if(r == 14){
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 900, 6));
        }
        else if(r == 15){
            player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 7200, 0));
        }
        else if(r == 16){
            player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 110, 0));
        }
        else if(r == 17){
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 7200, 0));
        }
        else if(r == 18){
            player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 900, 19));
        }
        else if(r == 19){
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 900, 0));
        }
        else {
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 7200, 0));
        }
    }


    @Override
    public String getTextureName(int meta) {
        if (meta < 0 || meta >= EnumApples.values().length) {
            meta = 0;
        }
        return "plug:apples/" + EnumApples.values()[meta].getName().toLowerCase() + "_apple";
    }

    @Override
    public int getVariantCount() {
        return EnumApples.values().length;
    }
}
