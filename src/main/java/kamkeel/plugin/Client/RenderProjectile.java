package kamkeel.plugin.Client;

import kamkeel.plugin.Entity.EntityProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

/**
 * Renders thrown items (kunai, void rasenshuriken) as their item sprite.
 */
public class RenderProjectile extends ThrownItemRenderer<EntityProjectile> {

    public RenderProjectile(EntityRendererProvider.Context context) {
        super(context, 1.0F, false);
    }
}
