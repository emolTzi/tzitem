package kamkeel.plugin;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * The Plugin Mod, ported to Minecraft 1.21.1 NeoForge (modid: plug).
 */
@Mod(PluginMod.MOD_ID)
public class PluginMod {

    public static final String MOD_ID = "plug";

    public PluginMod(IEventBus modEventBus) {
        ModRegistries.BLOCKS.register(modEventBus);
        ModRegistries.ITEMS.register(modEventBus);
        ModRegistries.CREATIVE_MODE_TABS.register(modEventBus);
        ModRegistries.BLOCK_ENTITIES.register(modEventBus);
        ModRegistries.ENTITY_TYPES.register(modEventBus);

        // Static init of the creative tabs (and, progressively, items/blocks).
        ModCreativeTabs.CARDS_TAB.hashCode();
    }
}
