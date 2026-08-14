package kamkeel.plugin;

import kamkeel.plugin.Blocks.ModBlocks;
import kamkeel.plugin.Compat.CompatibilityBiomesOPlenty;
import kamkeel.plugin.Compat.CompatibilityExtraUtilities;
import kamkeel.plugin.Config.ConfigCompat;
import kamkeel.plugin.Config.LoadConfiguration;
import kamkeel.plugin.Entity.EntityProjectile;
import kamkeel.plugin.Items.ModItems;
import kamkeel.plugin.Network.NetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod(modid = "plug", name = "The Plugin Mod", version = "5.4")
public class PluginMod {

    @SidedProxy(clientSide = "kamkeel.plugin.Client.ClientProxy", serverSide = "kamkeel.plugin.CommonProxy")
    public static CommonProxy proxy;

    private static int NewEntityStartId = 0;
    public static PluginMod instance;

    // Config
    public static String configPath;


    public PluginMod() {
        instance = this;
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        NetworkHandler.init();

        configPath = event.getModConfigurationDirectory() + File.separator + "PluginMod";
        configPath += File.separator;
        LoadConfiguration.init(configPath);

        proxy.preInnit();
        registerNewEntity(EntityProjectile.class, "throwableitem", 64, 3, true);

        if(ConfigCompat.BiomesOPlenty){
            registerCompat(CompatibilityBiomesOPlenty.class, "BiomesOPlenty");
        }
        if(ConfigCompat.ExtraUtilities){
            registerCompat(CompatibilityExtraUtilities.class, "ExtraUtilities");
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        //Proxy, TitleEntity, Entity, GUI and Packet Register
        proxy.load();
    }

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.registerBlocks(event);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        ModBlocks.registerItemBlocks(event);
        ModItems.registerItems(event);
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        proxy.registerModels();
    }

    public static CreativeTabs cardsTab = new CreativeTabs(LocalizationHelper.MOD_PREFIX + "playingCardTab") {
        @Override
        public ItemStack getTabIconItem() {
            if(ModItems.CardIcon == null){
                return new ItemStack(Items.STICK);
            }
            return new ItemStack(ModItems.CardIcon);
        }
    };

    public static CreativeTabs miscTab = new CreativeTabs(LocalizationHelper.MOD_PREFIX + "misc") {
        @Override
        public ItemStack getTabIconItem() {
            if(ModItems.TreasureMap == null){
                return new ItemStack(Items.STICK);
            }
            return new ItemStack(ModItems.TreasureMap);
        }
    };

    public static CreativeTabs weaponTab = new CreativeTabs(LocalizationHelper.MOD_PREFIX + "weapons") {
        @Override
        public ItemStack getTabIconItem() {
            if(ModItems.PaperBomb == null){
                return new ItemStack(Items.STICK);
            }
            return new ItemStack(ModItems.PaperBomb);
        }
    };


    public static CreativeTabs blocksTab = new CreativeTabs(LocalizationHelper.MOD_PREFIX + "blocks") {
        @Override
        public ItemStack getTabIconItem() {
            if(ModBlocks.Cherry_Barrel == null){
                return new ItemStack(Blocks.ENCHANTING_TABLE);
            }
            return new ItemStack(ModBlocks.Cherry_Barrel);
        }
    };

    public void registerNewEntity(Class<? extends net.minecraft.entity.Entity> cl, String name, int range, int update, boolean velocity) {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation("plug", name), cl, name, NewEntityStartId++, this, range, update, velocity);
    }

    public static void registerCompat(Class clazz, String modid) {
        if (Loader.isModLoaded(modid)) {
            try {
                Class.forName(clazz.getCanonicalName());
            } catch (ClassNotFoundException e) {
                System.out.println("Could not find compatibility class for mod { " + modid + " }. Please report this.");
            }
        }
    }

}
