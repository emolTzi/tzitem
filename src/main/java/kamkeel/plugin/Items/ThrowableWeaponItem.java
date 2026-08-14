package kamkeel.plugin.Items;

import kamkeel.plugin.Entity.EntityProjectile;
import kamkeel.plugin.ModEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

/**
 * A weapon that can be thrown on release (kunai, void rasenshuriken).
 * Mirrors the 1.12.2 onPlayerStoppedUsing throw logic.
 */
public class ThrowableWeaponItem extends SwordItem {

    public ThrowableWeaponItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) {
            return;
        }
        if (level.isClientSide) {
            player.swing(InteractionHand.MAIN_HAND);
            return;
        }
        float damage = 3.0F + getTier().getAttackDamageBonus();
        EntityProjectile projectile = new EntityProjectile(ModEntities.THROWABLE_ITEM.get(), player, level, stack, damage);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        level.addFreshEntity(projectile);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }
}
