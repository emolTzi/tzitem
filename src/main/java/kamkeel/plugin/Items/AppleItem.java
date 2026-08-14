package kamkeel.plugin.Items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * One of the 19 plugin apples. Mirrors the 1.12.2 Apple.onFoodEaten
 * potion-effect table, keyed by the variant index (0 = Classic ... 18 = Odd).
 */
public class AppleItem extends Item {

    private final int variant;

    public AppleItem(int variant, Properties properties) {
        super(properties);
        this.variant = variant;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (entity instanceof Player player && !level.isClientSide) {
            applyEffects(player);
        }
        return result;
    }

    private void applyEffects(Player player) {
        int dmg = variant;
        if (dmg == 2) {
            // Vintage
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 24, 0));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 3));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
        } else if (dmg == 3) {
            // Rage
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 2));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else if (dmg == 4) {
            // Mend
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 10, 0));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 700, 4));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
        } else if (dmg == 5) {
            // Shield
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 6, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else if (dmg == 6) {
            // Buffer
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 20, 0));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 900, 9));
        } else if (dmg == 7) {
            // Swift
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else if (dmg == 8) {
            // Boost
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 40, 0));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
        } else if (dmg == 9) {
            // Alert
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 3600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else if (dmg == 10) {
            // Element
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else if (dmg == 11) {
            // Full
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 3600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else if (dmg == 12) {
            // Drunk
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 900, 1));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 4));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
        } else if (dmg == 13) {
            // Tank
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 900, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 900, 9));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 6, 0));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else if (dmg == 14) {
            // Rotten
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 900, 4));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 40, 0));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 2));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
        } else if (dmg == 15) {
            // Corrupt
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
            useRandomEffect(player);
        } else if (dmg == 16) {
            // Reinforced
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 900, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 5));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else if (dmg == 17) {
            // Toxic
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 900, 1));
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 3600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 110, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
        } else if (dmg == 18) {
            // Odd
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800, 0));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1800, 0));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
        } else {
            // Classic / Founding (default)
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 13, 0));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
        }
    }

    private static void useRandomEffect(Player player) {
        int r = player.getRandom().nextInt(20);
        if (r == 1) {
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 7200, 0));
        } else if (r == 2) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 7200, 1));
        } else if (r == 3) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1, 7));
        } else if (r == 4) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 900, 3));
        } else if (r == 5) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 7200, 0));
        } else if (r == 6) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 900, 2));
        } else if (r == 7) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 4));
        } else if (r == 8) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 3));
        } else if (r == 9) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 4));
        } else if (r == 10) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 3));
        } else if (r == 11) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 7));
        } else if (r == 12) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 900, 2));
        } else if (r == 13) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 3));
        } else if (r == 14) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 900, 6));
        } else if (r == 15) {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 7200, 0));
        } else if (r == 16) {
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 110, 0));
        } else if (r == 17) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 7200, 0));
        } else if (r == 18) {
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 900, 19));
        } else if (r == 19) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 900, 0));
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 7200, 0));
        }
    }
}
