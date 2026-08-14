package kamkeel.plugin.Client;

import kamkeel.plugin.Client.Render.RenderProjectile;
import kamkeel.plugin.CommonProxy;
import kamkeel.plugin.Config.ConfigCompat;
import kamkeel.plugin.Entity.EntityProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInnit() {
        super.preInnit();

        MinecraftForge.EVENT_BUS.register(ClientModelRegistry.class);

        if(ConfigCompat.Keybindings){
            // Register KeyHandler
            for(Keybindings key : Keybindings.values()) {
                ClientRegistry.registerKeyBinding(key.getKeybind());
            }

            FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        }

        RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, new IRenderFactory<EntityProjectile>() {
            @Override
            public Render<? super EntityProjectile> createRenderFor(RenderManager manager) {
                return new RenderProjectile(manager);
            }
        });
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public EntityPlayer getPlayer(){
        return Minecraft.getMinecraft().player;
    }

    @Override
    public void registerModels() {
        ClientModelRegistry.registerModels();
    }

}
