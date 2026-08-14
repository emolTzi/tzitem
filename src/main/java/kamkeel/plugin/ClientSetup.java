package kamkeel.plugin;

import kamkeel.plugin.Client.RenderProjectile;
import kamkeel.plugin.Items.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client-only setup: register the vanilla bow pull/pulling predicates so the
 * plugin bows animate like the vanilla bow.
 */
@EventBusSubscriber(modid = PluginMod.MOD_ID, value = Dist.CLIENT)
public final class ClientSetup {

    private ClientSetup() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.THROWABLE_ITEM.get(), RenderProjectile::new);
        for (var holder : ModItems.BOWS) {
            var bow = holder.get();
            ItemProperties.register(bow, ResourceLocation.withDefaultNamespace("pulling"),
                    (stack, level, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
            ItemProperties.register(bow, ResourceLocation.withDefaultNamespace("pull"),
                    (stack, level, entity, seed) -> {
                        if (entity == null) {
                            return 0.0F;
                        }
                        return entity.getUseItem() != stack ? 0.0F
                                : (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
                    });
        }
    }
}
