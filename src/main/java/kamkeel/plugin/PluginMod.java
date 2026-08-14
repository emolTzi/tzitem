package kamkeel.plugin;

import kamkeel.plugin.Blocks.ModBlocks;
import kamkeel.plugin.Items.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * The Plugin Mod, ported to Minecraft 1.21.1 NeoForge (modid: plug).
 */
@Mod(PluginMod.MOD_ID)
public class PluginMod {

    public static final String MOD_ID = "plug";

    public PluginMod(IEventBus modEventBus) {
        // Populate the deferred-registry entries before wiring the bus.
        ModBlocks.register();
        ModItems.registerBlockItems();
        ModCreativeTabs.init();

        ModRegistries.BLOCKS.register(modEventBus);
        ModRegistries.ITEMS.register(modEventBus);
        ModRegistries.CREATIVE_MODE_TABS.register(modEventBus);
        ModRegistries.BLOCK_ENTITIES.register(modEventBus);
        ModRegistries.ENTITY_TYPES.register(modEventBus);
    }
}
