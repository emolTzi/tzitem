package kamkeel.plugin;

import kamkeel.plugin.Blocks.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

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
            case "misc":
            case "weapons":
                return new ItemStack(net.minecraft.world.item.Items.STICK);
            case "blocks":
                if (ModBlocks.CHERRY_BARREL != null) {
                    return new ItemStack(ModBlocks.CHERRY_BARREL.asItem());
                }
                return new ItemStack(net.minecraft.world.item.Items.ENCHANTING_TABLE);
            default:
                return new ItemStack(net.minecraft.world.item.Items.STICK);
        }
    }

    static void populate(String name, CreativeModeTab.Output output) {
        if ("blocks".equals(name)) {
            for (var block : ModBlocks.ALL) {
                output.accept(new ItemStack(block.asItem()));
            }
        }
        // Other tabs are populated as the item registry is ported.
    }
}
