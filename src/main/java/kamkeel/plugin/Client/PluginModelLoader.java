package kamkeel.plugin.Client;

import com.google.common.collect.ImmutableList;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.Items.ModItems;
import kamkeel.plugin.Items.Weapons.ItemPluginBowInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.InputStreamReader;
import java.util.function.Function;

/**
 * Runtime item model loader. All plugin items use flat 2D item models
 * (mirroring the 1.7.10 IIcon rendering) resolved from the texture registry.
 * Block models referenced by ItemBlocks resolve through the generated
 * inline JSON files under models/block.
 *
 * Baked locations look like "plug:item/<key>" where key is:
 *   <itemname>[_meta]      - flat item model
 *   <bow>_pulling_0/1/2    - flat bow pull stage
 *   flat/<texture>         - flat model with "plug:blocks/<texture>"
 *   <blockmodel>           - inline JSON model from models/block/<blockmodel>.json
 */
@SideOnly(Side.CLIENT)
public class PluginModelLoader implements ICustomModelLoader {

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        // ModelLoaderRegistry.getModel() passes getActualLocation(), which prepends
        // "models/" to plain ResourceLocations. So the normal item location
        // "plug:item/<key>" arrives here as "plug:models/item/<key>".
        return "plug".equals(modelLocation.getResourceDomain())
                && modelLocation.getResourcePath().startsWith("models/item/");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        String key = modelLocation.getResourcePath().substring("models/item/".length());

        // Bow pulling stages: <bow>_pulling_0/1/2
        for (int i = 0; i <= 2; i++) {
            String suffix = "_pulling_" + i;
            if (key.endsWith(suffix)) {
                String bowName = key.substring(0, key.length() - suffix.length());
                Item bow = Item.REGISTRY.getObject(new ResourceLocation("plug", bowName));
                if (bow instanceof ItemPluginBowInterface) {
                    return new ItemLayerModel(ImmutableList.of(new ResourceLocation(ModItems.resolveTexturePath(((ItemPluginBowInterface) bow).textureName + suffix))));
                }
            }
        }

        // Explicit flat block-texture items (e.g. cave vines)
        if (key.startsWith("flat/")) {
            return new ItemLayerModel(ImmutableList.of(new ResourceLocation("plug:blocks/" + key.substring("flat/".length()))));
        }

        // <itemname>_<meta>
        String base = key;
        int meta = 0;
        int idx = key.lastIndexOf('_');
        if (idx > 0 && key.length() > idx + 1) {
            String trailing = key.substring(idx + 1);
            boolean allDigits = !trailing.isEmpty();
            for (char c : trailing.toCharArray()) {
                if (!Character.isDigit(c)) {
                    allDigits = false;
                    break;
                }
            }
            if (allDigits) {
                base = key.substring(0, idx);
                meta = Integer.parseInt(trailing);
            }
        }

        Item item = Item.REGISTRY.getObject(new ResourceLocation("plug", base));
        if (item != null) {
            if (item instanceof ItemPluginBowInterface) {
                return new ItemLayerModel(ImmutableList.of(new ResourceLocation(ModItems.resolveTexturePath(((ItemPluginBowInterface) item).textureName + "_standby"))));
            }
            if (item instanceof ItemVariantTexture) {
                return new ItemLayerModel(ImmutableList.of(new ResourceLocation(ModItems.resolveTexturePath(((ItemVariantTexture) item).getTextureName(meta)))));
            }
            String tex = ModItems.ITEM_TEXTURES.get(item);
            if (tex != null) {
                return new ItemLayerModel(ImmutableList.of(new ResourceLocation(ModItems.resolveTexturePath(tex))));
            }
            // Items without explicit texture: fall through to block model lookup
        }

        // Generated inline block model JSON?
        ResourceLocation blockModel = new ResourceLocation("plug", "models/block/" + key + ".json");
        if (resourceExists(blockModel)) {
            try (InputStreamReader reader = new InputStreamReader(
                    Minecraft.getMinecraft().getResourceManager().getResource(blockModel).getInputStream())) {
                return new PluginBlockModel(ModelBlock.deserialize(reader));
            }
        }

        // Last resort: flat model using the key as a block texture
        return new ItemLayerModel(ImmutableList.of(new ResourceLocation("plug:blocks/" + key)));
    }

    private static boolean resourceExists(ResourceLocation location) {
        try {
            Minecraft.getMinecraft().getResourceManager().getResource(location);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Bakes a flat item model for the given texture (used for bow pull stages). */
    public static IBakedModel bakeFlatModel(String texture, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter) {
        IModel model = new ItemLayerModel(ImmutableList.of(new ResourceLocation(texture)));
        return model.bake(net.minecraftforge.common.model.TRSRTransformation.identity(), format, getter);
    }
}
