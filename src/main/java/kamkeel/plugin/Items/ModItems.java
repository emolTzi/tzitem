package kamkeel.plugin.Items;

import kamkeel.plugin.Config.ConfigItems;
import kamkeel.plugin.Items.Cards.*;
import kamkeel.plugin.Items.Misc.*;
import kamkeel.plugin.Items.Weapons.*;
import kamkeel.plugin.PluginMod;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kamkeel.plugin.Util.PluginMaterials.*;

public class ModItems {

    // All items in registration order
    public static final List<Item> ITEMS = new ArrayList<>();

    // Explicit item -> texture assignments (1.7.10 setTextureName calls)
    public static final Map<Item, String> ITEM_TEXTURES = new HashMap<>();

    /**
     * Assign an item texture (1.12.2 models are resolved from these at runtime).
     */
    public static Item setTexture(Item item, String texture) {
        ITEM_TEXTURES.put(item, texture);
        return item;
    }

    /**
     * Converts a 1.7.10 icon name (e.g. "plug:weapons/katana") to the
     * 1.12.2 resource path ("plug:items/weapons/katana"). Block textures
     * ("plug:blocks/...") and atlas sprites ("plug:bw_...") pass through.
     */
    public static String resolveTexturePath(String texture) {
        if (texture.startsWith("plug:items/") || texture.startsWith("plug:blocks/") || texture.startsWith("plug:bw_")) {
            return texture;
        }
        if (texture.startsWith("plug:")) {
            return "plug:items/" + texture.substring("plug:".length());
        }
        return texture;
    }

    // Cards
    public static Item HeartCard;
    public static Item DiamondCard;
    public static Item SpadeCard;
    public static Item ClubsCard;
    public static Item CardIcon;
    public static Item JokerCard;
    public static Item AddonCard;

    // Misc
    public static Item Pills;
    public static Item Coins;
    public static Item Balls;
    public static Item Eyes;
    public static Item Artifacts;
    public static Item Apples;
    public static Item TreasureMap;
    public static Item PaperBomb;

    // Energy
    public static Item EnergyAttacks;
    public static Item VoidRasenshuriken;
    public static Item MassiveRasengan;
    public static Item MassiveRasenganSpin;

    // Materials
    public static Item dark_metal_ingot;
    public static Item blue_steel_ingot;
    public static Item jungle_ingot;
    public static Item glass_shard;
    public static Item imperial_gold_ingot;
    public static Item aqua_ingot;

    public static Item codium_ingot;
    public static Item damascus_ingot;
    public static Item deep_ingot;
    public static Item energy_quartz;
    public static Item plug_ingot;

    // Custom Weapon Parts
    public static Item BlueLongSwordBlade;
    public static Item BlueLongSwordGuard;
    public static Item BlueLongSwordRod;
    public static Item DarkDaggerBlade;
    public static Item DarkDaggerHandle;
    public static Item VoidCharm;
    public static Item GlassDisc;
    public static Item GlassPanHandle;
    public static Item GlassPanBinding;
    public static Item JungleAxeHead;
    public static Item JungleAxeBinding;
    public static Item JungleAxeRod;
    public static Item TreasureCutlassBlade;
    public static Item TreasureCutlassHandle;
    public static Item TreasureCutlassBinding;
    public static Item AdaptorsSpearPoint;
    public static Item AdaptorsSpearHandle;
    public static Item AdaptorsSpearBinding;

    /**
     * Assign a registry name and queue an item for registration.
     */
    public static Item registerItem(Item item, String name) {
        item.setRegistryName(new ResourceLocation("plug", name));
        ITEMS.add(item);
        return item;
    }

    /**
     * Declare and register items. Do NOT add recipes here!
     */
    public static void init() {

        if(ConfigItems.DisableAllItems){
            return;
        }

        // Load Weapons
        if(!ConfigItems.DisableAllWeapons){
            // Ingots/Materials:
            if(ConfigItems.SpecialWeaponItems){
                codium_ingot = setTexture(registerItem(new PluginItemInterface(26712), "codium_ingot"), "plug:materials/codium_ingot").setUnlocalizedName("codium_ingot").setCreativeTab(PluginMod.miscTab);
                damascus_ingot = setTexture(registerItem(new PluginItemInterface(26713), "damascus_ingot"), "plug:materials/damascus_ingot").setUnlocalizedName("damascus_ingot").setCreativeTab(PluginMod.miscTab);
                deep_ingot = setTexture(registerItem(new PluginItemInterface(26714), "deep_ingot"), "plug:materials/deep_ingot").setUnlocalizedName("deep_ingot").setCreativeTab(PluginMod.miscTab);
                energy_quartz = setTexture(registerItem(new PluginItemInterface(26715), "energy_quartz"), "plug:materials/energy_quartz").setUnlocalizedName("energy_quartz").setCreativeTab(PluginMod.miscTab);
                dark_metal_ingot = setTexture(registerItem(new PluginItemInterface(26700), "dark_metal"), "plug:materials/dark_metal_ingot").setUnlocalizedName("dark_metal").setCreativeTab(PluginMod.miscTab);
                blue_steel_ingot = setTexture(registerItem(new PluginItemInterface(26700), "blue_steel"), "plug:materials/blue_steel").setUnlocalizedName("blue_steel").setCreativeTab(PluginMod.miscTab);
                jungle_ingot = setTexture(registerItem(new PluginItemInterface(26700), "jungle_ingot"), "plug:materials/jungle_ingot").setUnlocalizedName("jungle_ingot").setCreativeTab(PluginMod.miscTab);
                glass_shard = setTexture(registerItem(new PluginItemInterface(26700), "glass_shard"), "plug:materials/glass_shard").setUnlocalizedName("glass_shard").setCreativeTab(PluginMod.miscTab);
                imperial_gold_ingot = setTexture(registerItem(new PluginItemInterface(26713), "imperial_gold"), "plug:materials/imperial_gold").setUnlocalizedName("imperial_gold").setCreativeTab(PluginMod.miscTab);
                aqua_ingot = setTexture(registerItem(new PluginItemInterface(26713), "aqua_ingot"), "plug:materials/aqua_ingot").setUnlocalizedName("aqua_ingot").setCreativeTab(PluginMod.miscTab);
            }

            plug_ingot = setTexture(registerItem(new PluginItemInterface(26712), "plug_ingot"), "plug:materials/plug_ingot").setUnlocalizedName("plug_ingot").setCreativeTab(PluginMod.miscTab);
            ModWeapons.init();

            if(ConfigItems.SpecialWeaponItems){
                // Parts:
                BlueLongSwordBlade = setTexture(registerItem(new PluginItemInterface(26700), "blue_longsword_custom_blade"), "plug:weapons/parts/blue_longsword_blade").setUnlocalizedName("blue_longsword_custom_blade").setCreativeTab(PluginMod.miscTab);
                BlueLongSwordGuard = setTexture(registerItem(new PluginItemInterface(26701), "blue_longsword_custom_guard"), "plug:weapons/parts/blue_longsword_guard").setUnlocalizedName("blue_longsword_custom_guard").setCreativeTab(PluginMod.miscTab);
                BlueLongSwordRod = setTexture(registerItem(new PluginItemInterface(26702), "blue_longsword_custom_rod"), "plug:weapons/parts/blue_longsword_rod").setUnlocalizedName("blue_longsword_custom_rod").setCreativeTab(PluginMod.miscTab);
                DarkDaggerBlade = setTexture(registerItem(new PluginItemInterface(26703), "dark_dagger_custom_blade"), "plug:weapons/parts/dark_dagger_custom_blade").setUnlocalizedName("dark_dagger_custom_blade").setCreativeTab(PluginMod.miscTab);
                DarkDaggerHandle = setTexture(registerItem(new PluginItemInterface(26704), "dark_dagger_custom_handle"), "plug:weapons/parts/dark_dagger_custom_handle").setUnlocalizedName("dark_dagger_custom_handle").setCreativeTab(PluginMod.miscTab);
                VoidCharm = setTexture(registerItem(new PluginItemInterface(26705), "void_charm"), "plug:weapons/parts/void_charm").setUnlocalizedName("void_charm").setCreativeTab(PluginMod.miscTab);
                GlassDisc = setTexture(registerItem(new PluginItemInterface(26705), "glass_disc"), "plug:weapons/parts/glass_disc").setUnlocalizedName("glass_disc").setCreativeTab(PluginMod.miscTab);
                GlassPanHandle = setTexture(registerItem(new PluginItemInterface(26707), "glass_pan_handle"), "plug:weapons/parts/glass_pan_handle").setUnlocalizedName("glass_pan_handle").setCreativeTab(PluginMod.miscTab);
                GlassPanBinding = setTexture(registerItem(new PluginItemInterface(26708), "glass_pan_binding"), "plug:weapons/parts/glass_pan_binding").setUnlocalizedName("glass_pan_binding").setCreativeTab(PluginMod.miscTab);
                JungleAxeHead = setTexture(registerItem(new PluginItemInterface(26709), "jungle_axe_head"), "plug:weapons/parts/jungle_axe_head").setUnlocalizedName("jungle_axe_head").setCreativeTab(PluginMod.miscTab);
                JungleAxeBinding = setTexture(registerItem(new PluginItemInterface(26710), "jungle_axe_binding"), "plug:weapons/parts/jungle_axe_binding").setUnlocalizedName("jungle_axe_binding").setCreativeTab(PluginMod.miscTab);
                JungleAxeRod = setTexture(registerItem(new PluginItemInterface(26711), "jungle_axe_rod"), "plug:weapons/parts/jungle_axe_rod").setUnlocalizedName("jungle_axe_rod").setCreativeTab(PluginMod.miscTab);
                TreasureCutlassBlade = setTexture(registerItem(new PluginItemInterface(26709), "treasure_cutlass_blade"), "plug:weapons/parts/treasure_cutlass_blade").setUnlocalizedName("treasure_cutlass_blade").setCreativeTab(PluginMod.miscTab);
                TreasureCutlassBinding = setTexture(registerItem(new PluginItemInterface(26710), "treasure_cutlass_binding"), "plug:weapons/parts/treasure_cutlass_binding").setUnlocalizedName("treasure_cutlass_binding").setCreativeTab(PluginMod.miscTab);
                TreasureCutlassHandle = setTexture(registerItem(new PluginItemInterface(26711), "treasure_cutlass_handle"), "plug:weapons/parts/treasure_cutlass_handle").setUnlocalizedName("treasure_cutlass_handle").setCreativeTab(PluginMod.miscTab);
                AdaptorsSpearPoint = setTexture(registerItem(new PluginItemInterface(26709), "adaptors_spear_point"), "plug:weapons/parts/adaptors_spear_point").setUnlocalizedName("adaptors_spear_point").setCreativeTab(PluginMod.miscTab);
                AdaptorsSpearBinding = setTexture(registerItem(new PluginItemInterface(26710), "adaptors_spear_binding"), "plug:weapons/parts/adaptors_spear_binding").setUnlocalizedName("adaptors_spear_binding").setCreativeTab(PluginMod.miscTab);
                AdaptorsSpearHandle = setTexture(registerItem(new PluginItemInterface(26711), "adaptors_spear_handle"), "plug:weapons/parts/adaptors_spear_handle").setUnlocalizedName("adaptors_spear_handle").setCreativeTab(PluginMod.miscTab);
            }
        }



        // Metadata Items:
        if(ConfigItems.PillItems){
            Pills = registerItem(new Pills(), "pills");
        }

        if(ConfigItems.CoinItems){
            Coins = registerItem(new Coins(), "coins");
        }

        if(ConfigItems.EyeItems){
            Eyes = registerItem(new Eyes(), "eyes");
        }

        if(ConfigItems.BallItems){
            Balls = registerItem(new Balls(), "balls");
        }

        if(ConfigItems.ArtifactItems){
            Artifacts = registerItem(new Artifacts(), "artifacts");
        }

        if(ConfigItems.EnergyAttackItems){
            EnergyAttacks = registerItem(new Energy(), "energy");

            VoidRasenshuriken = setTexture(registerItem(new VoidRasenshuriken(10001, random), "voidrasenshuriken"), "plug:energy/voidrasenshuriken").setUnlocalizedName("VoidRasenshuriken");
            MassiveRasengan = setTexture(registerItem(new MassiveRasengan(), "massiverasengan"), "plug:energy/massive_rasengan").setUnlocalizedName("massiverasengan");
            MassiveRasenganSpin = setTexture(registerItem(new MassiveRasengan(), "massiverasengan_spin"), "plug:energy/massive_rasengan_spin").setUnlocalizedName("massiverasengan_spin");
        }

        if(ConfigItems.AppleItems){
            Apples = registerItem(new Apple(4, 1.2F, false), "apples");
        }

        if(ConfigItems.MiscItems){
            TreasureMap = setTexture(registerItem(new TreasureMap(), "treasuremap"), "plug:extra/treasuremap").setUnlocalizedName("TreasureMap");
            PaperBomb = setTexture(registerItem(new PaperBomb(), "paperbomb"), "plug:weapons/paperbomb").setUnlocalizedName("PaperBomb");

            Item miscItem = setTexture(registerItem(new PluginItemInterface(10001), "medkit"), "plug:extra/medkit").setUnlocalizedName("medkit").setCreativeTab(PluginMod.miscTab);
            miscItem = setTexture(registerItem(new PluginItemInterface(10001), "bandages"), "plug:extra/bandages").setUnlocalizedName("bandages").setCreativeTab(PluginMod.miscTab);
            miscItem = setTexture(registerItem(new PluginItemInterface(10001), "bell"), "plug:extra/bell").setUnlocalizedName("bell").setCreativeTab(PluginMod.miscTab);
            miscItem = setTexture(registerItem(new PluginItemInterface(10001), "energy_catalyst"), "plug:materials/catalyst").setUnlocalizedName("energy_catalyst").setCreativeTab(PluginMod.miscTab);
            miscItem = setTexture(registerItem(new PluginItemInterface(10001), "hyper_catalyst"), "plug:materials/hyper_catalyst").setUnlocalizedName("hyper_catalyst").setCreativeTab(PluginMod.miscTab);
            miscItem = setTexture(registerItem(new PluginItemInterface(10001), "horn"), "plug:extra/horn").setUnlocalizedName("horn").setCreativeTab(PluginMod.miscTab);
            miscItem = setTexture(registerItem(new PluginItemInterface(10001), "rope"), "plug:extra/rope").setUnlocalizedName("rope").setCreativeTab(PluginMod.miscTab);
            miscItem = setTexture(registerItem(new PluginItemInterface(10001), "painkiller"), "plug:extra/painkiller").setUnlocalizedName("painkiller").setCreativeTab(PluginMod.miscTab);

            miscItem = setTexture(registerItem(new PluginItemFoodInterface(10, 0.8F, true), "burrito"), "plug:extra/burrito").setUnlocalizedName("burrito").setCreativeTab(PluginMod.miscTab);

        }

        if(ConfigItems.CardItems){
            // Cards
            HeartCard = registerItem(new HeartCard(), "hearts_card");
            DiamondCard = registerItem(new DiamondCard(), "diamond_card");
            SpadeCard = registerItem(new SpadeCard(), "spade_card");
            ClubsCard = registerItem(new ClubsCard(), "clubs_card");
            JokerCard = registerItem(new JokerCard(), "joker_card");
            CardIcon = setTexture(registerItem(new CardIcon(), "cardicon"), "plug:cards/plugcard").setUnlocalizedName("CardIcon");
            AddonCard = registerItem(new AddonCard(), "addon_card");
        }

        if(ConfigItems.SpecialWeaponItems && !ConfigItems.DisableAllWeapons){
            // Ingot Assignment
            dark_metal.setRepairItem(new ItemStack(dark_metal_ingot));
            custom_dark_metal.setRepairItem(new ItemStack(dark_metal_ingot));
            glass.setRepairItem(new ItemStack(glass_shard));
            custom_glass.setRepairItem(new ItemStack(glass_shard));
            random.setRepairItem(new ItemStack(glass_shard));
            jungle.setRepairItem(new ItemStack(jungle_ingot));
            custom_jungle.setRepairItem(new ItemStack(jungle_ingot));
            blue_steel.setRepairItem(new ItemStack(blue_steel_ingot));
            custom_blue_steel.setRepairItem(new ItemStack(blue_steel_ingot));
            imperial_gold.setRepairItem(new ItemStack(imperial_gold_ingot));
            custom_imperial_gold.setRepairItem(new ItemStack(imperial_gold_ingot));
            aqua.setRepairItem(new ItemStack(aqua_ingot));
            custom_aqua.setRepairItem(new ItemStack(aqua_ingot));
            PluginMaterial.setRepairItem(new ItemStack(plug_ingot));
        }
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ITEMS) {
            if (item.getRegistryName() != null) {
                event.getRegistry().register(item);
            }
        }
    }

    public static void initRecipes() {
        if(ConfigItems.DisableAllItems){
            return;
        }

        if(ConfigItems.SpecialWeaponItems && !ConfigItems.DisableAllWeapons){
            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "energy_quartz_recipe"), null, new ItemStack(ModItems.energy_quartz), "121", "232", "121", '1', Items.BLAZE_POWDER, '2', Items.QUARTZ, '3', Items.DIAMOND);

            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "blue_steel_recipe"), null, new ItemStack(ModItems.blue_steel_ingot), "SCS", "CQC", "SCS", 'S', ModItems.damascus_ingot, 'C', ModItems.codium_ingot, 'Q', ModItems.energy_quartz);
            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "dark_metal_recipe"), null, new ItemStack(ModItems.dark_metal_ingot), "SCS", "CQC", "SCS", 'S', ModItems.damascus_ingot, 'C', ModItems.deep_ingot, 'Q', ModItems.energy_quartz);
            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "jungle_ingot_recipe"), null, new ItemStack(ModItems.jungle_ingot), "SCS", "CQC", "SCS", 'S', ModItems.damascus_ingot, 'C', Blocks.VINE, 'Q', ModItems.energy_quartz);

            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "blue_longsword_recipe"), null, new ItemStack(ModWeapons.BlueLongSword), "  1", " 2 ", "34 ", '1', ModItems.BlueLongSwordBlade, '2', ModItems.BlueLongSwordGuard, '3', ModItems.BlueLongSwordRod, '4', Items.LAVA_BUCKET);
            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "jungle_axe_recipe"), null, new ItemStack(ModWeapons.JungleAxe), "  1", " 2 ", "34 ", '1', ModItems.JungleAxeHead, '2', ModItems.JungleAxeBinding, '3', ModItems.JungleAxeRod, '4', Items.LAVA_BUCKET);
            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "dark_dagger_recipe"), null, new ItemStack(ModWeapons.DarkDagger), "  1", " 2 ", "34 ", '1', ModItems.DarkDaggerBlade, '2', ModItems.VoidCharm, '3', ModItems.DarkDaggerHandle, '4', Items.LAVA_BUCKET);
            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "glass_pan_recipe"), null, new ItemStack(ModWeapons.GlassPan), "  1", " 2 ", "34 ", '1', ModItems.GlassDisc, '2', ModItems.GlassPanBinding, '3', ModItems.GlassPanHandle, '4', Items.LAVA_BUCKET);
            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "treasure_cutlass_recipe"), null, new ItemStack(ModWeapons.TreasureCutlass), "  1", " 2 ", "34 ", '1', ModItems.TreasureCutlassBlade, '2', ModItems.TreasureCutlassBinding, '3', ModItems.TreasureCutlassHandle, '4', Items.LAVA_BUCKET);
            GameRegistry.addShapedRecipe(new ResourceLocation("plug", "adaptors_spear_recipe"), null, new ItemStack(ModWeapons.AdaptorsSpear), "  1", " 2 ", "34 ", '1', ModItems.AdaptorsSpearPoint, '2', ModItems.AdaptorsSpearBinding, '3', ModItems.AdaptorsSpearHandle, '4', Items.LAVA_BUCKET);
        }

        if(ConfigItems.AppleItems){
            // Apple Recipes
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_0_recipe"), null, new ItemStack(ModItems.Apples, 1, 0), Ingredient.fromStacks(new ItemStack(Items.GOLDEN_APPLE, 1, 1)));

            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_3_recipe"), null, new ItemStack(ModItems.Apples, 1, 3), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.BLAZE_POWDER, 1, 0)));
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_4_recipe"), null, new ItemStack(ModItems.Apples, 1, 4), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.GHAST_TEAR, 1, 0)));
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_5_recipe"), null, new ItemStack(ModItems.Apples, 1, 5), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT, 1, 0)));
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_6_recipe"), null, new ItemStack(ModItems.Apples, 1, 6), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.GLOWSTONE_DUST, 1, 0)));
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_7_recipe"), null, new ItemStack(ModItems.Apples, 1, 7), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.SUGAR, 1, 0)));
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_8_recipe"), null, new ItemStack(ModItems.Apples, 1, 8), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.SPECKLED_MELON, 1, 0)));
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_9_recipe"), null, new ItemStack(ModItems.Apples, 1, 9), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.GOLDEN_CARROT, 1, 0)));
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_10_recipe"), null, new ItemStack(ModItems.Apples, 1, 10), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.MAGMA_CREAM, 1, 0)));
            GameRegistry.addShapelessRecipe(new ResourceLocation("plug", "apple_11_recipe"), null, new ItemStack(ModItems.Apples, 1, 11), Ingredient.fromStacks(new ItemStack(ModItems.Apples, 1, 1)), Ingredient.fromStacks(new ItemStack(Items.BREAD, 1, 0)));
        }
    }
}
