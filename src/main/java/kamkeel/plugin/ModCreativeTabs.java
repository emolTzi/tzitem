package kamkeel.plugin;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * The four creative tabs, mirroring the 1.12.2 CreativeTabs.
 * Icons are wired lazily once the corresponding items are registered.
 */
public final class ModCreativeTabs {

    private ModCreativeTabs() {}

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CARDS_TAB = register("playing_card_tab",
            "itemGroup.plug.cards");
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MISC_TAB = register("misc",
            "itemGroup.plug.misc");
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WEAPONS_TAB = register("weapons",
            "itemGroup.plug.weapons");
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS_TAB = register("blocks",
            "itemGroup.plug.blocks");

    private static DeferredHolder<CreativeModeTab, CreativeModeTab> register(String name, String titleKey) {
        return ModRegistries.CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable(titleKey))
                .icon(() -> CreativeTabIcons.iconFor(name))
                .displayItems((params, output) -> CreativeTabIcons.populate(name, output))
                .build());
    }
}
