package kamkeel.plugin.Blocks;

import kamkeel.plugin.Blocks.ItemBlock.*;
import kamkeel.plugin.Config.ConfigBlocks;
import kamkeel.plugin.Enum.Blocks.EnumAncientStone;
import kamkeel.plugin.Enum.Blocks.EnumCreate;
import kamkeel.plugin.Enum.Blocks.EnumEldritch;
import kamkeel.plugin.Enum.Blocks.EnumLightStone;
import kamkeel.plugin.TileEntity.TileEntityBlockColorData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * Stores, initializes, and registers items. Also adds recipes and ore dictionary entries. (Episode 4)
 */
public class ModBlocks {

    // Registered blocks (in registration order)
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<BlockItemSpec> ITEM_BLOCKS = new ArrayList<>();


    // Tab Icon
    public static Block Cherry_Barrel;

    // Blocks
    public static Block Dark;
    public static Block Midnight;
    public static Block Rage;
    public static Block DeepCrystal;
    public static Block Eldritch;
    public static Block AncientStone;
    public static Block Lightstone;
    public static Block Barrel;

    // Create
    public static Block Asurine;
    public static Block Crimsite;
    public static Block Deepslate;
    public static Block Dripstone;
    public static Block Limestone;
    public static Block Tuff;
    public static Block Ochrum;
    public static Block Veridium;
    public static Block Scoria;
    public static Block Scorchia;

    public static Block caveVines;
    public static Block caveVinesGrowing;


    // Sub-Blocks
    public static BlockConcrete concreteBlock;
    public static BlockConcretePowder concretePowder;

    public static BlockConcrete concreteBlock2;
    public static BlockConcretePowder concretePowder2;

    public static BlockEnergy energyBlock;

    // Color Data
    public static BlockColorData colorDataBlock;

    // Slabs
    public static Block regularSingleSlab;
    public static Block regularDoubleSlab;

    public static class BlockItemSpec {
        public final Block block;
        public final Function<Block, ItemBlock> factory;

        public BlockItemSpec(Block block, Function<Block, ItemBlock> factory) {
            this.block = block;
            this.factory = factory;
        }
    }

    /**
     * Declare and register items. Do NOT add recipes here!
     */
    public static void init() {
        if(ConfigBlocks.DisableAllBlocks){
           return;
        }

        // Blocks:
        if(ConfigBlocks.DarkBlocks){
            Dark = registerBlock(new BlockDark(), "dark", ItemBlockDark::new);
        }

        if(ConfigBlocks.MidnightBlocks){
            Midnight = registerBlock(new BlockMidnight(), "midnight", ItemBlockMidnight::new);
        }

        if(ConfigBlocks.RageBlocks){
            Rage = registerBlock(new BlockRage(), "rage", ItemBlockRage::new);
        }

        if(ConfigBlocks.DeepCrystalBlocks){
            DeepCrystal = registerBlock(new BlockDeepCrystal(), "deep_crystal", ItemBlockDeepCrystal::new);
        }

        if(ConfigBlocks.CaveVinesBlocks){
            caveVines = registerBlock(new BlockCaveVines(), "cave_vines", ItemBlockCaveVines::new);
            caveVinesGrowing = registerBlock(new BlockCaveVinesGrowing(), "cave_vines_growing", ItemBlockCaveVinesGrowing::new);
        }

        if(ConfigBlocks.ConcreteBlocks){
            concreteBlock = (BlockConcrete) registerBlock(new BlockConcrete(0, "concrete"), "concrete", b -> new ItemBlockConcrete(b, 0));
            concretePowder = (BlockConcretePowder) registerBlock(new BlockConcretePowder(0, "concrete_powder", concreteBlock), "concrete_powder", b -> new ItemBlockConcrete(b, 0));

            concreteBlock2 = (BlockConcrete) registerBlock(new BlockConcrete(1, "concrete2"), "concrete2", b -> new ItemBlockConcrete(b, 1));
            concretePowder2 = (BlockConcretePowder) registerBlock(new BlockConcretePowder(1, "concrete_powder2", concreteBlock2), "concrete_powder2", b -> new ItemBlockConcrete(b, 1));
        }

        if(ConfigBlocks.EnergyBlocks){
            energyBlock = (BlockEnergy) registerBlock(new BlockEnergy(), "energy_block", ItemBlockEnergy::new);
        }

        if(ConfigBlocks.EldritchBlocks){
            Eldritch = registerBlock(new BlockEldritch("eldritch", EnumEldritch.class), "eldritch", ItemBlockEldritch::new);
        }

        if (ConfigBlocks.AncientStoneBlocks) {
            AncientStone = registerBlock(new BlockAncientStone("ancient_stone", EnumAncientStone.class), "ancient_stone", ItemBlockAncientStone::new);
        }

        if (ConfigBlocks.LightstoneBlocks) {
            Lightstone = registerBlock(new BlockLightStone("lightstone", EnumLightStone.class), "lightstone", ItemBlockLightStone::new);
        }

        if (ConfigBlocks.CreateBlocks) {
            Crimsite = registerBlock(new BlockCreate("crimsite", EnumCreate.class), "crimsite", ItemBlockCreateCrimsite::new);

            Deepslate = registerBlock(new BlockCreate("deepslate", EnumCreate.class), "deepslate", ItemBlockCreateDeepslate::new);

            Asurine = registerBlock(new BlockCreate("asurine", EnumCreate.class), "asurine", ItemBlockCreateAsurine::new);

            Dripstone = registerBlock(new BlockCreate("dripstone", EnumCreate.class), "dripstone", ItemBlockCreateDripstone::new);

            Limestone = registerBlock(new BlockCreate("limestone", EnumCreate.class), "limestone", ItemBlockCreateLimestone::new);

            Tuff = registerBlock(new BlockCreate("tuff", EnumCreate.class), "tuff", ItemBlockCreateTuff::new);

            Ochrum = registerBlock(new BlockCreate("ochrum", EnumCreate.class), "ochrum", ItemBlockCreateOchrum::new);

            Veridium = registerBlock(new BlockCreate("veridium", EnumCreate.class), "veridium", ItemBlockCreateVeridium::new);

            Scoria = registerBlock(new BlockCreate("scoria", EnumCreate.class), "scoria", ItemBlockCreateScoria::new);

            Scorchia = registerBlock(new BlockCreate("scorchia", EnumCreate.class), "scorchia", ItemBlockCreateScorchia::new);
        }


        if(ConfigBlocks.BarrelBlocks){
            //////////////////////////////////////
            //             BARRELS
            //////////////////////////////////////
            // Name = WOOD_barrel
            Barrel = registerBlock(new BlockBarrel("oak"), "oak_barrel");
            Barrel = registerBlock(new BlockBarrel("spruce"), "spruce_barrel");
            Barrel = registerBlock(new BlockBarrel("birch"), "birch_barrel");
            Barrel = registerBlock(new BlockBarrel("jungle"), "jungle_barrel");
            Barrel = registerBlock(new BlockBarrel("dark_oak"), "dark_oak_barrel");
            Barrel = registerBlock(new BlockBarrel("acacia"), "acacia_barrel");
            Barrel = registerBlock(new BlockBarrel("warped"), "warped_barrel");
            Barrel = registerBlock(new BlockBarrel("crimson"), "crimson_barrel");

            // Custom Barrels
            Cherry_Barrel = registerBlock(new BlockBarrel("cherry"), "cherry_barrel");

            //////////////////////////////////////
        }

        // Hidden color data carrier block (no item, invisible)
        colorDataBlock = (BlockColorData) registerBlock(new BlockColorData(), "datablock");
        registerTile(TileEntityBlockColorData.class, "blockcolordata");
    }

    public static void initRecipes() {

    }

    public static Block registerBlock(Block block, String registryName) {
        return registerBlock(block, registryName, null);
    }

    public static Block registerBlock(Block block, String registryName, Function<Block, ItemBlock> itemBlockFactory) {
        block.setRegistryName(new ResourceLocation("plug", registryName));
        BLOCKS.add(block);
        if (itemBlockFactory != null) {
            ITEM_BLOCKS.add(new BlockItemSpec(block, itemBlockFactory));
        }
        return block;
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (Block block : BLOCKS) {
            if (block.getRegistryName() != null) {
                event.getRegistry().register(block);
            }
        }
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        for (BlockItemSpec spec : ITEM_BLOCKS) {
            ItemBlock itemBlock = spec.factory.apply(spec.block);
            itemBlock.setRegistryName(spec.block.getRegistryName());
            event.getRegistry().register(itemBlock);
        }
    }

    public static void registerTile(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation("plug", name));
    }

    public static void registerTile(Class<? extends TileEntity> clazz) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation("plug", clazz.getSimpleName().toLowerCase()));
    }
}
