package kamkeel.plugin.Client;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.util.vector.Vector3f;

/**
 * The vanilla 1.12.2 item display transforms, copied verbatim from the
 * corresponding vanilla model JSONs (item/generated.json, item/handheld.json,
 * item/bow.json and block/block.json).
 *
 * The vanilla pipeline stores these JSON values but the translation component
 * ends up divided by 16 in the effective perspective matrix (verified at
 * runtime against the vanilla minecraft:paper and minecraft:iron_sword baked
 * models, whose FIRST_PERSON matrices translate by (0.0706, 0.2, 0.0706) for
 * the JSON values (1.13, 3.2, 1.13)). We mirror that here so plugin items hold
 * exactly like vanilla items.
 */
@SideOnly(Side.CLIENT)
public final class VanillaItemTransforms {

    private VanillaItemTransforms() {}

    private static final float INV16 = 1.0F / 16.0F;

    private static ItemTransformVec3f tf(float rx, float ry, float rz, float tx, float ty, float tz, float s) {
        return new ItemTransformVec3f(new Vector3f(rx, ry, rz), new Vector3f(tx * INV16, ty * INV16, tz * INV16), new Vector3f(s, s, s));
    }

    private static final ItemTransformVec3f IDENTITY = ItemTransformVec3f.DEFAULT;

    /** item/generated.json - plain flat items. */
    public static final ItemCameraTransforms GENERATED = new ItemCameraTransforms(
            IDENTITY,                                                            // thirdperson_lefthand
            tf(0, 0, 0, 0, 3, 1, 0.55f),                                        // thirdperson_righthand
            tf(0, 90, -25, 1.13f, 3.2f, 1.13f, 0.68f),                          // firstperson_lefthand
            tf(0, -90, 25, 1.13f, 3.2f, 1.13f, 0.68f),                          // firstperson_righthand
            tf(0, 180, 0, 0, 13, 7, 1),                                         // head
            IDENTITY,                                                            // gui
            tf(0, 0, 0, 0, 2, 0, 0.5f),                                         // ground
            tf(0, 180, 0, 0, 0, 0, 1)                                           // fixed
    );

    /** item/handheld.json - swords and tools. */
    public static final ItemCameraTransforms HANDHELD = new ItemCameraTransforms(
            tf(0, 90, -55, 0, 4.0f, 0.5f, 0.85f),                               // thirdperson_lefthand
            tf(0, -90, 55, 0, 4.0f, 0.5f, 0.85f),                               // thirdperson_righthand
            tf(0, 90, -25, 1.13f, 3.2f, 1.13f, 0.68f),                          // firstperson_lefthand
            tf(0, -90, 25, 1.13f, 3.2f, 1.13f, 0.68f),                          // firstperson_righthand
            tf(0, 180, 0, 0, 13, 7, 1),                                         // head
            IDENTITY,                                                            // gui
            tf(0, 0, 0, 0, 2, 0, 0.5f),                                         // ground
            tf(0, 180, 0, 0, 0, 0, 1)                                           // fixed
    );

    /** item/bow.json - bows (first person identical to handheld, third person bow-specific). */
    public static final ItemCameraTransforms BOW = new ItemCameraTransforms(
            tf(-80, -280, 40, -1, -2, 2.5f, 0.9f),                              // thirdperson_lefthand
            tf(-80, 260, -40, -1, -2, 2.5f, 0.9f),                              // thirdperson_righthand
            tf(0, 90, -25, 1.13f, 3.2f, 1.13f, 0.68f),                          // firstperson_lefthand
            tf(0, -90, 25, 1.13f, 3.2f, 1.13f, 0.68f),                          // firstperson_righthand
            tf(0, 180, 0, 0, 13, 7, 1),                                         // head
            IDENTITY,                                                            // gui
            tf(0, 0, 0, 0, 2, 0, 0.5f),                                         // ground
            tf(0, 180, 0, 0, 0, 0, 1)                                           // fixed
    );

    /** block/block.json - blocks (and their item forms). */
    public static final ItemCameraTransforms BLOCK = new ItemCameraTransforms(
            IDENTITY,                                                            // thirdperson_lefthand
            tf(75, 45, 0, 0, 2.5f, 0, 0.375f),                                  // thirdperson_righthand
            tf(0, 225, 0, 0, 0, 0, 0.40f),                                      // firstperson_lefthand
            tf(0, 45, 0, 0, 0, 0, 0.40f),                                       // firstperson_righthand
            IDENTITY,                                                            // head
            tf(30, 225, 0, 0, 0, 0, 0.625f),                                    // gui
            tf(0, 0, 0, 0, 3, 0, 0.25f),                                        // ground
            tf(0, 0, 0, 0, 0, 0, 0.5f)                                          // fixed
    );
}
