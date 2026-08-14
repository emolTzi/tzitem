package kamkeel.plugin.Client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;

/**
 * Forces full brightness (lightmap 240/240) and disables diffuse lighting on
 * all quads — the 1.12.2 equivalent of RenderBlockFullBright's
 * setBrightness(240) + enableAO = false.
 */
@SideOnly(Side.CLIENT)
public class FullbrightBakedModel implements IBakedModel {

    private static final float FULLBRIGHT = Float.intBitsToFloat(0xF000F0); // skylight 240, blocklight 240

    private final IBakedModel parent;

    public FullbrightBakedModel(IBakedModel parent) {
        this.parent = parent;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        List<BakedQuad> quads = parent.getQuads(state, side, rand);
        List<BakedQuad> out = new ArrayList<>(quads.size());
        for (BakedQuad quad : quads) {
            if (quad.getFormat() != DefaultVertexFormats.BLOCK) {
                out.add(quad);
                continue;
            }
            int[] vertexData = quad.getVertexData();
            int stride = vertexData.length / 4;
            float[][][] unpacked = new float[4][DefaultVertexFormats.BLOCK.getElementCount()][4];
            for (int v = 0; v < 4; v++) {
                int base = v * stride;
                unpacked[v][0][0] = Float.intBitsToFloat(vertexData[base]);
                unpacked[v][0][1] = Float.intBitsToFloat(vertexData[base + 1]);
                unpacked[v][0][2] = Float.intBitsToFloat(vertexData[base + 2]);
                unpacked[v][1][0] = Float.intBitsToFloat(vertexData[base + 3]);
                unpacked[v][2][0] = Float.intBitsToFloat(vertexData[base + 4]);
                unpacked[v][2][1] = Float.intBitsToFloat(vertexData[base + 5]);
                unpacked[v][3][0] = FULLBRIGHT; // lightmap
                unpacked[v][4][0] = Float.intBitsToFloat(vertexData[base + 7]); // normal
            }
            out.add(new UnpackedBakedQuad(unpacked, quad.getTintIndex(), quad.getFace(), quad.getSprite(), false, DefaultVertexFormats.BLOCK));
        }
        return out;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
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
        return parent.getItemCameraTransforms();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return parent.getOverrides();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return ForgeHooksClient.handlePerspective(parent, cameraTransformType);
    }
}
