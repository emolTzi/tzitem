package kamkeel.plugin.Client;

import kamkeel.plugin.Items.Misc.MassiveRasengan;
import kamkeel.plugin.Items.Misc.VoidRasenshuriken;
import kamkeel.plugin.Items.PluginItemInterface;
import kamkeel.plugin.Items.Weapons.*;
import kamkeel.plugin.Items.Weapons.Broken.ItemBrokenDagger;
import kamkeel.plugin.Items.Weapons.Broken.ItemBrokenJungleAxe;
import kamkeel.plugin.Items.Weapons.Broken.ItemBrokenReversedDagger;
import kamkeel.plugin.Items.Weapons.Glass.*;
import kamkeel.plugin.Items.Weapons.Unique.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

/**
 * Replicates the 1.7.10 custom hand rendering: the fixed PluginItemRenderer /
 * TransparentItemRenderer transform stacks plus each item's renderSpecial()
 * GL operations, expressed as javax.vecmath matrices.
 */
@SideOnly(Side.CLIENT)
public class HandTransformRegistry {

    public static Matrix4f identity() {
        Matrix4f m = new Matrix4f();
        m.setIdentity();
        return m;
    }

    public static Matrix4f translate(Matrix4f m, float x, float y, float z) {
        Matrix4f t = new Matrix4f();
        t.setIdentity();
        t.m03 = x;
        t.m13 = y;
        t.m23 = z;
        Matrix4f out = new Matrix4f();
        out.mul(m, t);
        return out;
    }

    public static Matrix4f rotate(Matrix4f m, float angle, float x, float y, float z) {
        Matrix4f r = new Matrix4f();
        if (angle != 0.0F) {
            r.set(new AxisAngle4f(new Vector3f(x, y, z), (float) Math.toRadians(angle)));
        } else {
            r.setIdentity();
        }
        Matrix4f out = new Matrix4f();
        out.mul(m, r);
        return out;
    }

    public static Matrix4f scale(Matrix4f m, float x, float y, float z) {
        Matrix4f s = new Matrix4f();
        s.setIdentity();
        s.m00 = x;
        s.m11 = y;
        s.m22 = z;
        Matrix4f out = new Matrix4f();
        out.mul(m, s);
        return out;
    }

    /**
     * The per-item renderSpecial() operations, mirrored as matrices.
     */
    public static Matrix4f getSpecialTransform(Item item) {
        Matrix4f m = identity();

        // PluginItemInterface / PluginItemFoodInterface default
        if (item instanceof PluginItemInterface) {
            m = scale(m, 0.66f, 0.66f, 0.66f);
            m = translate(m, 0, 0.3f, 0);
            return m;
        }

        // ItemPluginWeaponInterface default
        if (item instanceof ItemPluginWeaponInterface) {
            m = scale(m, 0.66f, 0.66f, 0.66f);
            m = translate(m, 0.16f, 0.26f, 0.06f);
            return m;
        }

        if (item instanceof ItemKunai || item instanceof ItemGlassKunai) {
            m = scale(m, 0.4f, 0.4f, 0.4f);
            m = translate(m, -0.4f, 0.5f, 0.1f);
            return m;
        }

        if (item instanceof ItemGlassKunaiReversed) {
            m = scale(m, 0.4f, 0.4f, 0.4f);
            m = rotate(m, 180, 1, 0, 0);
            m = translate(m, -0.4f, -0.9f, 0.2f);
            return m;
        }

        if (item instanceof ItemGlassDaggerReversed || item instanceof ItemUniqueDaggerReversed
                || item instanceof ItemDaggerReversed || item instanceof ItemBrokenReversedDagger) {
            m = scale(m, 0.6f, 0.6f, 0.6f);
            m = translate(m, 0.16f, 0.6f, -0.16f);
            m = rotate(m, 180, 1, 0, 0);
            return m;
        }

        if (item instanceof ItemGlassDagger || item instanceof ItemUniqueDagger
                || item instanceof ItemDagger || item instanceof ItemBrokenDagger) {
            m = scale(m, 0.6f, 0.6f, 0.6f);
            m = translate(m, 0.14f, 0.22f, 0.06f);
            return m;
        }

        if (item instanceof ItemBroadSword) {
            m = scale(m, 1f, 1.2f, 1f);
            m = translate(m, -0.12f, 0.14f, -0.16f);
            m = rotate(m, 180, 0, 1, 0);
            return m;
        }

        if (item instanceof ItemKatana) {
            m = scale(m, 1f, 1.1f, 1f);
            m = translate(m, 0.08f, 0.22f, 0.10f);
            return m;
        }

        if (item instanceof ItemHammer) {
            m = scale(m, 1.2f, 1.4f, 1f);
            m = translate(m, 0.2f, -0.08f, 0.08f);
            return m;
        }

        if (item instanceof ItemWarAxe) {
            m = scale(m, 1.2f, 1.2f, 1.2f);
            m = translate(m, 0.14f, -0.1f, 0.06f);
            return m;
        }

        if (item instanceof ItemBattleAxe || item instanceof ItemUniqueAxe || item instanceof ItemBrokenJungleAxe) {
            m = scale(m, 1f, 0.8f, 1f);
            if (item instanceof ItemBattleAxe) {
                m = translate(m, -0.04f, 0.2f, -0.16f);
                m = rotate(m, 180, 0, 1, 0);
            } else {
                m = translate(m, 0.14f, 0.22f, 0.06f);
            }
            return m;
        }

        if (item instanceof ItemCrystalSpear || item instanceof ItemUniqueSpear || item instanceof ItemSpear) {
            m = scale(m, 1f, 1.3f, 1f);
            m = translate(m, -0.12f, -0.24f, -0.16f);
            m = rotate(m, 180, 0, 1, 0);
            return m;
        }

        if (item instanceof ItemUniqueScythe || item instanceof ItemScythe) {
            m = scale(m, 1f, 1.3f, 1f);
            m = translate(m, 0.0f, -0.2f, -0.16f);
            m = rotate(m, 180, 0, 1, 0);
            return m;
        }

        if (item instanceof ItemLongStaff) {
            m = scale(m, 1f, 1.3f, 1f);
            m = translate(m, -0.12f, -0.5f, -0.10f);
            m = rotate(m, 180, 0, 1, 0);
            return m;
        }

        if (item instanceof ItemGlaive) {
            m = translate(m, 0.03f, -0.4f, 0.08f);
            return m;
        }

        if (item instanceof ItemClaw) {
            m = scale(m, 0.6f, 0.6f, 0.6f);
            m = translate(m, -0.6f, 0.2f, -0.2f);
            m = rotate(m, 90, 0, 0, -1);
            m = rotate(m, 6, 1, 0, 0);
            return m;
        }

        if (item instanceof ItemRotatedShield) {
            m = scale(m, 0.6f, 0.6f, 0.6f);
            m = translate(m, 0.4f, 1f, -0.18f);
            m = rotate(m, -6, 0, 1, 0);
            m = rotate(m, 120, 0, 0, 1);
            return m;
        }

        if (item instanceof ItemShield) {
            m = scale(m, 0.6f, 0.6f, 0.6f);
            m = translate(m, 0f, 0f, -0.2f);
            m = rotate(m, -6, 0, 1, 0);
            return m;
        }

        if (item instanceof MassiveRasengan) {
            m = scale(m, 1.6f, 1.6f, 1.6f);
            m = translate(m, 0.2f, 0, 0);
            return m;
        }

        if (item instanceof VoidRasenshuriken) {
            m = scale(m, 1.3f, 1.3f, 1.3f);
            return m;
        }

        if (item instanceof ItemGlassBlade) {
            m = scale(m, 0.8f, 0.8f, 0.8f);
            m = translate(m, -0.2f, 0.28f, -0.12f);
            m = rotate(m, 180, 0, 1, 0);
            m = rotate(m, -16, 0, 0, 1);
            return m;
        }

        // ItemGlassPan / ItemGlassPanBroken
        if (item instanceof ItemGlassPan) {
            m = scale(m, 0.66f, 0.66f, 0.66f);
            m = translate(m, 0.16f, 0.26f, 0.06f);
            return m;
        }

        return m;
    }

    /**
     * PluginItemRenderer / TransparentItemRenderer fixed transform stack:
     * translate(0.9375, 0.0625, 0), rotate(-315, z), [special], rotate(-20, z),
     * rotate(-50, y), translate(-0.09375, 0.0625, 0), translate(0, -0.3, 0),
     * scale(1.5), rotate(50, y), rotate(335, z), translate(-0.9375, -0.0625, 0)
     */
    public static Matrix4f getEquippedTransform(Item item) {
        Matrix4f m = identity();
        m = translate(m, 0.9375f, 0.0625f, 0.0f);
        m = rotate(m, -315.0f, 0.0f, 0.0f, 1.0f);
        m = applySpecial(m, item);
        m = rotate(m, -20.0f, 0.0f, 0.0f, 1.0f);
        m = rotate(m, -50.0f, 0.0f, 1.0f, 0.0f);
        m = translate(m, -0.09375f, 0.0625f, 0.0f);
        m = translate(m, 0.0f, -0.3f, 0.0f);
        m = scale(m, 1.5f, 1.5f, 1.5f);
        m = rotate(m, 50.0f, 0.0f, 1.0f, 0.0f);
        m = rotate(m, 335.0f, 0.0f, 0.0f, 1.0f);
        m = translate(m, -0.9375f, -0.0625f, 0.0f);
        return m;
    }

    private static Matrix4f applySpecial(Matrix4f m, Item item) {
        Matrix4f out = new Matrix4f();
        out.mul(m, getSpecialTransform(item));
        return out;
    }

    /**
     * Bows use the vanilla first-person transform: the vanilla ItemRenderer
     * already applies the two-handed bow pose and the pull-back arm animation
     * for ItemBow subclasses (getItemUseAction() == BOW). The old 1.7.10
     * ItemBowRenderer offset (translate 0.2,-0.3,0.1) was relative to the 1.7.10
     * render stack and misplaces the bow in 1.12.2.
     */
    public static Matrix4f getBowTransform() {
        return identity();
    }

    /** Composes base * stack. */
    public static Matrix4f compose(Matrix4f base, Matrix4f stack) {
        Matrix4f out = new Matrix4f();
        out.mul(base, stack);
        return out;
    }
}
