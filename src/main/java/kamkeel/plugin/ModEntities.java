package kamkeel.plugin;

import kamkeel.plugin.Entity.EntityProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Entity types. The single "throwableitem" projectile mirrors the 1.12.2
 * EntityProjectile (used by kunai and the void rasenshuriken).
 */
public final class ModEntities {

    private ModEntities() {}

    public static final DeferredHolder<EntityType<?>, EntityType<EntityProjectile>> THROWABLE_ITEM =
            ModRegistries.ENTITY_TYPES.register("throwableitem",
                    () -> EntityType.Builder.<EntityProjectile>of(EntityProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(64)
                            .updateInterval(10)
                            .build("throwableitem"));

    /** No-op trigger to force class initialization (registers the entity type early). */
    public static void init() {}
}
