package kamkeel.plugin;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Tab icons and item population. Icons fall back to vanilla items until the
 * corresponding plugin items are registered; population is wired up as the
 * item/block registry fills in.
 */
final class CreativeTabIcons {

    private CreativeTabIcons() {}

    static ItemStack iconFor(String name) {
        switch (name) {
            case "playing_card_tab":
                return new ItemStack(Items.STICK);
            case "misc":
                return new ItemStack(Items.STICK);
            case "weapons":
                return new ItemStack(Items.STICK);
            case "blocks":
                return new ItemStack(Items.ENCHANTING_TABLE);
            default:
                return new ItemStack(Items.STICK);
        }
    }

    static void populate(String name, CreativeModeTab.Output output) {
        // Populated progressively as the registry is ported.
    }
}
