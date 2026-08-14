package kamkeel.plugin;

import kamkeel.plugin.Blocks.ModBlocks;
import kamkeel.plugin.Items.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;

/**
 * Tab icons and item population for the four creative tabs.
 */
final class CreativeTabIcons {

    private CreativeTabIcons() {}

    private static ItemStack stack(DeferredItem<Item> item, Item fallback) {
        return item != null ? new ItemStack(item.get()) : new ItemStack(fallback);
    }

    static ItemStack iconFor(String name) {
        switch (name) {
            case "playing_card_tab":
                return stack(ModItems.find("cardicon"), Items.STICK);
            case "misc":
                return stack(ModItems.find("treasuremap"), Items.STICK);
            case "weapons":
                return stack(ModItems.find("paperbomb"), Items.STICK);
            case "blocks":
                if (ModBlocks.CHERRY_BARREL != null) {
                    return new ItemStack(ModBlocks.CHERRY_BARREL.asItem());
                }
                return new ItemStack(Items.ENCHANTING_TABLE);
            default:
                return new ItemStack(Items.STICK);
        }
    }

    static void populate(String name, CreativeModeTab.Output output) {
        switch (name) {
            case "playing_card_tab":
                for (var item : ModItems.CARDS) {
                    output.accept(new ItemStack(item.get()));
                }
                break;
            case "misc":
                for (var item : ModItems.MISC) {
                    output.accept(new ItemStack(item.get()));
                }
                break;
            case "weapons":
                for (var item : ModItems.WEAPONS) {
                    output.accept(new ItemStack(item.get()));
                }
                break;
            case "blocks":
                for (var block : ModBlocks.ALL) {
                    output.accept(new ItemStack(block.asItem()));
                }
                break;
        }
    }
}
