package kamkeel.plugin.Compat;

import kamkeel.plugin.Blocks.*;
import kamkeel.plugin.Blocks.ItemBlock.ItemBlockColorSlab;
import kamkeel.plugin.Blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CompatibilityExtraUtilities {

    // Stairs
    public static BlockColorStair EUStair;

    // Slabs
    public static Block euSingleSlab;
    public static Block euDoubleSlab;

    static {
        System.out.println("ExtraUtilities compatibility is enabled and running");
        registerItems();
        registerBlocks();
        registerRecipes();
    }

    private static void registerItems() {

    }

    private static void registerBlocks(){

        // Adding Stair Variants
        Block EUCobblestone = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ExtraUtilities", "color_stonebrick"));
        if (EUCobblestone == null) {
            EUCobblestone = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("extrautils2", "color_stonebrick"));
        }

        Block EUStoneBrick = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ExtraUtilities", "colorStoneBrick"));
        if (EUStoneBrick == null) {
            EUStoneBrick = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("extrautils2", "colorStoneBrick"));
        }

        if(EUCobblestone != null){
            for (int i = 0; i < 16; i++) {
                BlockColorStair stair = new BlockColorStair(EUCobblestone, i, "euCobble");
                stair.textureName = "plug:bw_cobblestone";
                EUStair = (BlockColorStair) ModBlocks.registerBlock(stair, "eu_cobble_stair_" + i);
            }

            for (int i = 0; i < 16; i++) {
                BlockColorSlab singleSlab = (BlockColorSlab)new BlockColorSlab(false, Material.ROCK, "euCobble","cobblestone", i);
                BlockColorSlab doubleSlab = (BlockColorSlab)new BlockColorSlab(true, Material.ROCK, "euCobble","cobblestone", i);
                doubleSlab.setDropSlab(singleSlab);

                euSingleSlab = ModBlocks.registerBlock(singleSlab, "eu_cobble_sslab_" + i, b -> new ItemBlockColorSlab(b, singleSlab, doubleSlab));
                euDoubleSlab = ModBlocks.registerBlock(doubleSlab, "eu_cobble_dslab_" + i, b -> new ItemBlockColorSlab(b, singleSlab, doubleSlab));
            }
        }

        if(EUStoneBrick != null){
            for (int i = 0; i < 16; i++) {
                BlockColorStair stair = new BlockColorStair(EUStoneBrick, i, "euStoneBrick");
                stair.textureName = "plug:bw_stonebrick";
                EUStair = (BlockColorStair) ModBlocks.registerBlock(stair, "eu_stonebrick_stair_" + i);
            }

            for (int i = 0; i < 16; i++) {
                BlockColorSlab singleSlab = (BlockColorSlab)new BlockColorSlab(false, Material.ROCK, "euStoneBrick","stonebrick", i);
                BlockColorSlab doubleSlab = (BlockColorSlab)new BlockColorSlab(true, Material.ROCK, "euStoneBrick","stonebrick", i);
                doubleSlab.setDropSlab(singleSlab);

                euSingleSlab = ModBlocks.registerBlock(singleSlab, "eu_stonebrick_sslab_" + i, b -> new ItemBlockColorSlab(b, singleSlab, doubleSlab));
                euDoubleSlab = ModBlocks.registerBlock(doubleSlab, "eu_stonebrick_dslab_" + i, b -> new ItemBlockColorSlab(b, singleSlab, doubleSlab));
            }
        }
    }

    private static void registerRecipes() {

    }
}
