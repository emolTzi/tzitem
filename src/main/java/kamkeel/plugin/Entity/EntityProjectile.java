package kamkeel.plugin.Entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * A thrown item that damages on impact and breaks. Mirrors the 1.12.2
 * EntityProjectile in its simplest form (kunai / void rasenshuriken).
 */
public class EntityProjectile extends ThrowableItemProjectile {

    private float damage = 5.0F;

    public EntityProjectile(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public EntityProjectile(EntityType<? extends ThrowableItemProjectile> type, LivingEntity owner, Level level, ItemStack item, float damage) {
        super(type, owner, level);
        this.damage = damage;
        this.setItem(item.copy());
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            if (result instanceof EntityHitResult entityHit && entityHit.getEntity() != this.getOwner()) {
                Entity target = entityHit.getEntity();
                target.hurt(this.damageSources().thrown(this, this.getOwner()), this.damage);
            }
            this.discard();
        }
    }
}
