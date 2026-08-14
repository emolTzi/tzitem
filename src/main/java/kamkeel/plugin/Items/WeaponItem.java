package kamkeel.plugin.Items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/**
 * A weapon with the 1.7.10 feel: no attack cooldown (huge attack-speed
 * modifier, mirroring the 1.12.2 NO_COOLDOWN patch).
 */
public class WeaponItem extends SwordItem {

    private final ItemAttributeModifiers attributes;

    public WeaponItem(Tier tier, Properties properties) {
        super(tier, properties);
        this.attributes = ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_damage"),
                                3.0D + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_speed"),
                                1024.0D, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return this.attributes;
    }
}
