package kamkeel.plugin.Items;

import kamkeel.plugin.LocalizationHelper;
import kamkeel.plugin.PluginMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import org.lwjgl.opengl.GL11;

public class PluginItemFoodInterface extends ItemFood implements ItemRenderInterface {

    public PluginItemFoodInterface(int saturation, float eatTime, boolean wolfFeed) {
        super(saturation, eatTime, wolfFeed);
        PluginMod.proxy.registerItem(this);
    }

    public void renderSpecial(){
        GL11.glScalef(0.66f, 0.66f,0.66f);
        GL11.glTranslatef(0, 0.3f, 0);
    }

    @Override
    public Item setUnlocalizedName(String name){
        return super.setUnlocalizedName(LocalizationHelper.MOD_PREFIX + name);
    }
}
