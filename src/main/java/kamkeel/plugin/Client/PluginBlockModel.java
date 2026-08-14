package kamkeel.plugin.Client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Thin IModel adapter around a deserialized inline ModelBlock, so generated
 * block model JSONs can be baked by the runtime model loader.
 */
public class PluginBlockModel implements IModel {

    private final ModelBlock block;

    public PluginBlockModel(ModelBlock block) {
        this.block = block;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        FaceBakery faceBakery = new FaceBakery();
        List<BakedQuad> generalQuads = new ArrayList<>();

        TextureAtlasSprite particle = bakedTextureGetter.apply(new ResourceLocation(block.resolveTextureName("particle")));
        TRSRTransformation transformation = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());

        for (BlockPart part : block.getElements()) {
            for (Map.Entry<EnumFacing, BlockPartFace> entry : part.mapFaces.entrySet()) {
                BlockPartFace face = entry.getValue();
                TextureAtlasSprite sprite = bakedTextureGetter.apply(new ResourceLocation(block.resolveTextureName(face.texture)));
                BakedQuad quad = faceBakery.makeBakedQuad(part.positionFrom, part.positionTo, face, sprite, entry.getKey(), transformation, part.partRotation, false, part.shade);
                generalQuads.add(quad);
            }
        }

        // Forge's SimpleBakedModel.getQuads() returns null for faces missing from the map,
        // which crashes renderLitItem (creative GUI) with an NPE. Fill every facing with
        // an empty list, mirroring SimpleBakedModel.Builder.
        Map<EnumFacing, List<BakedQuad>> faceQuads = new java.util.EnumMap<>(EnumFacing.class);
        for (EnumFacing facing : EnumFacing.values()) {
            faceQuads.put(facing, java.util.Collections.<BakedQuad>emptyList());
        }

        // Vanilla block item transforms (block/block.json): the GUI 30/225/0
        // rotation makes item-block icons render as proper 3D blocks, and the
        // first-person/third-person values give vanilla block holding.
        ItemCameraTransforms cameraTransforms = VanillaItemTransforms.BLOCK;
        return new SimpleBakedModel(generalQuads, faceQuads, block.isAmbientOcclusion(), block.isGui3d(), particle, cameraTransforms, block.createOverrides());
    }
}
