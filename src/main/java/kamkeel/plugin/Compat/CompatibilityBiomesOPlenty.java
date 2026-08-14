package kamkeel.plugin.Compat;

import kamkeel.plugin.Blocks.BlockBarrel;
import kamkeel.plugin.Blocks.BlockPlugSlab;
import kamkeel.plugin.Blocks.BlockPlugStair;
import kamkeel.plugin.Blocks.ItemBlock.ItemBlockSlab;
import kamkeel.plugin.Blocks.ModBlocks;
import kamkeel.plugin.LocalizationHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CompatibilityBiomesOPlenty {

    public static Block BOPBarrel;
    public static Block BOPStair;

    // Slabs
    public static Block bopSingleSlab;
    public static Block bopDoubleSlab;

    static {
        System.out.println("Biomes O Plenty compatibility is enabled and running");
        registerItems();
        registerBlocks();
        registerRecipes();
    }

    private static void registerItems() {

    }

    private static void registerBlocks(){
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("ethereal"), "ethereal_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("hellbark"), "hellbark_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("fir"), "fir_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("palm"), "palm_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("willow"), "willow_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("jacaranda"), "jacaranda_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("magic"), "magic_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("mahogany"), "mahogany_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("mangrove"), "mangrove_barrel");
        BOPBarrel = ModBlocks.registerBlock(new BlockBarrel("redwood"), "redwood_barrel");

        // Adding Stair Variants
        Block BOPRocks = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("BiomesOPlenty", "rocks"));
        if (BOPRocks == null) {
            BOPRocks = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("biomesoplenty", "rocks"));
        }
        if(BOPRocks != null){
            String[] types = new String[] { "limestone", "limestonesmooth", "siltstone", "siltstonesmooth", "shale", "shalesmooth" };
            for (int i = 0; i < types.length; i++) {
                BlockPlugStair stair = new BlockPlugStair(BOPRocks, i, types[i]);
                stair.setHardness(1.8F);
                stair.textureName = "biomesoplenty:" + types[i];
                BOPStair = ModBlocks.registerBlock(stair, "stair_" + types[i]);
            }

            BlockPlugSlab singleSlab = (BlockPlugSlab)new BlockPlugSlab(false, Material.ROCK, BlockPlugSlab.SlabCategory.BOP).setUnlocalizedName(LocalizationHelper.MOD_PREFIX + "bopSingleSlab");
            BlockPlugSlab doubleSlab = (BlockPlugSlab)new BlockPlugSlab(true, Material.ROCK, BlockPlugSlab.SlabCategory.BOP).setUnlocalizedName(LocalizationHelper.MOD_PREFIX + "bopDoubleSlab");

            bopSingleSlab = ModBlocks.registerBlock(singleSlab, "bopsingleslab", b -> new ItemBlockSlab(b, singleSlab, doubleSlab));
            bopDoubleSlab = ModBlocks.registerBlock(doubleSlab, "bopdoubleslab", b -> new ItemBlockSlab(b, singleSlab, doubleSlab));
        }
    }

    private static void registerRecipes() {

    }

}
