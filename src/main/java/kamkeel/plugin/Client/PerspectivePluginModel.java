package kamkeel.plugin.Client;

import kamkeel.plugin.Items.ItemCustomTransparent;
import kamkeel.plugin.Items.ItemRenderInterface;
import kamkeel.plugin.Items.Weapons.ItemGlass;
import kamkeel.plugin.Items.Weapons.ItemPluginBowInterface;
import kamkeel.plugin.Items.Weapons.ItemPluginWeaponInterface;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;

/**
 * Wraps baked plugin item models and supplies the vanilla 1.12.2 camera
 * transforms (item/generated, item/handheld, item/bow), so items hold in hand
 * exactly like their vanilla counterparts. The old 1.7.10 renderer transform
 * stacks are no longer used.
 */
@SideOnly(Side.CLIENT)
public class PerspectivePluginModel implements IBakedModel {

    private final IBakedModel parent;
    private final Item item;
    private final ItemOverrideList overrides;
    private final ItemCameraTransforms cameraTransforms;

    public PerspectivePluginModel(IBakedModel parent, Item item, ItemOverrideList overrides, boolean bow) {
        this.parent = parent;
        this.item = item;
        this.overrides = overrides;
        if (bow) {
            this.cameraTransforms = VanillaItemTransforms.BOW;
        } else if (item instanceof ItemPluginWeaponInterface || item instanceof ItemGlass) {
            this.cameraTransforms = VanillaItemTransforms.HANDHELD;
        } else {
            this.cameraTransforms = VanillaItemTransforms.GENERATED;
        }
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return parent.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return parent.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return parent.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return parent.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return parent.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return cameraTransforms;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type) {
        return ForgeHooksClient.handlePerspective(this, type);
    }
}
