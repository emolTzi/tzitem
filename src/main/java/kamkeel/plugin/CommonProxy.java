package kamkeel.plugin;

import kamkeel.plugin.Blocks.ModBlocks;
import kamkeel.plugin.Items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class CommonProxy {

    public void preInnit() {
        //Item/Block Initiating
        ModBlocks.init();
        ModItems.init();
    }

    public void load() {

        // Load Recipes
        ModItems.initRecipes();
        ModBlocks.initRecipes();

        // Register GUI
        ModGUIs.register();
    }

    public void postInnit() { }

    public boolean hasClient() {
        return false;
    }

    public EntityPlayer getPlayer() {
        return null;
    }

    public void registerItem(Item item) { }

    public void registerBow(Item item) { }

    public void registerModels() { }

}
