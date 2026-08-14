package kamkeel.plugin.Items;

import kamkeel.plugin.Blocks.ModBlocks;
import kamkeel.plugin.ModRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

/**
 * Item registration. Block items are derived from {@link ModBlocks}; the
 * regular items (weapons, pills, coins, ...) are added here as the port grows.
 */
public final class ModItems {

    private ModItems() {}

    /** Registers a BlockItem for every registered block. */
    public static void registerBlockItems() {
        for (var block : ModBlocks.ALL) {
            ModRegistries.ITEMS.registerSimpleBlockItem(block);
        }
    }
}
