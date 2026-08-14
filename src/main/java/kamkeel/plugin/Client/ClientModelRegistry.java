package kamkeel.plugin.Client;

import kamkeel.plugin.Blocks.*;
import kamkeel.plugin.Items.ItemCustomTransparent;
import kamkeel.plugin.Items.ItemRenderInterface;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.Items.ModItems;
import kamkeel.plugin.Items.Weapons.ItemGlass;
import kamkeel.plugin.Items.Weapons.ItemPluginBowInterface;
import kamkeel.plugin.Texture.TextureColorBlockBase;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Client-side model/color registration for the whole mod.
 * Runs during ModelRegistryEvent (via CommonProxy.registerModels).
 */
@SideOnly(Side.CLIENT)
public class ClientModelRegistry {

    public static void registerModels() {
        ModelLoaderRegistry.registerLoader(new PluginModelLoader());

        // ---- Items ----
        for (Item item : ModItems.ITEMS) {
            registerItemModels(item);
        }

        // ---- ItemBlocks ----
        for (Block block : ModBlocks.BLOCKS) {
            registerItemBlockModels(block);
        }

        registerColorHandlers();
    }

    private static void registerItemModels(final Item item) {
        final String name = item.getRegistryName().getResourcePath();
        List<ResourceLocation> variants = new ArrayList<>();

        if (item instanceof ItemVariantTexture) {
            int count = ((ItemVariantTexture) item).getVariantCount();
            for (int meta = 0; meta < count; meta++) {
                variants.add(new ResourceLocation("plug", name + "_" + meta));
            }
        } else {
            variants.add(new ResourceLocation("plug", name));
        }

        // Bows: pull-stage models are selected by BowPullOverrideList at render time.
        // Register them as variants too so their textures are collected and stitched.
        if (item instanceof ItemPluginBowInterface) {
            for (int i = 0; i <= 2; i++) {
                variants.add(new ResourceLocation("plug", name + "_pulling_" + i));
            }
        }

        net.minecraft.client.renderer.block.model.ModelBakery.registerItemVariants(item, variants.toArray(new ResourceLocation[0]));
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                String path = name;
                if (item instanceof ItemVariantTexture) {
                    path = name + "_" + stack.getItemDamage();
                }
                // Must match the registered variant location "plug:<path>#inventory".
                // Forge's getItemLocation() prepends "item/" itself when loading the
                // model, so including it here causes a registry lookup miss at render time.
                return new ModelResourceLocation(new ResourceLocation("plug", path), "inventory");
            }
        });
    }

    private static void registerItemBlockModels(final Block block) {
        Item item = Item.getItemFromBlock(block);
        if (item == null || block instanceof BlockColorData) {
            return;
        }

        List<ResourceLocation> variants = new ArrayList<>();

        if (block instanceof BlockCaveVines || block instanceof BlockCaveVinesGrowing) {
            variants.add(new ResourceLocation("plug", "flat/cave_vines_head"));
            registerBlockItem(block, item, variants);
            return;
        }

        if (block instanceof BlockPlugStair || block instanceof BlockColorStair) {
            // model name == registry name
            variants.add(block.getRegistryName());
            registerBlockItem(block, item, variants);
            return;
        }

        if (block instanceof BlockColorSlab) {
            String suffix = ((BlockColorSlab) block).isDouble() ? "_full" : "_half";
            variants.add(new ResourceLocation("plug", block.getRegistryName().getResourcePath() + suffix));
            registerBlockItem(block, item, variants);
            return;
        }

        if (block instanceof BlockPlugSlab) {
            final BlockPlugSlab slab = (BlockPlugSlab) block;
            if (slab.isDouble()) {
                variants.add(new ResourceLocation("plug", "bop_slab_double"));
            } else {
                for (int meta = 0; meta < BlockPlugSlab.bop.length; meta++) {
                    variants.add(new ResourceLocation("plug", "bop_slab_" + BlockPlugSlab.bop[meta]));
                }
            }
            registerBlockItem(block, item, variants);
            return;
        }

        if (block instanceof BlockBarrel) {
            variants.add(new ResourceLocation("plug", "barrel_" + ((BlockBarrel) block).name));
            registerBlockItem(block, item, variants);
            return;
        }

        if (block instanceof BlockGeneric) {
            final BlockGeneric generic = (BlockGeneric) block;
            for (int meta = 0; meta < generic.getModelCount(); meta++) {
                variants.add(new ResourceLocation("plug", generic.getModelNameForMeta(meta)));
            }
            registerBlockItem(block, item, variants);
            return;
        }

        if (block instanceof BlockConcrete) {
            final BlockConcrete concrete = (BlockConcrete) block;
            for (int meta = 0; meta <= concrete.META_AMOUNT; meta++) {
                variants.add(new ResourceLocation("plug", concrete.getModelNameForMeta(meta)));
            }
            registerBlockItem(block, item, variants);
            return;
        }

        if (block instanceof BlockConcretePowder) {
            final BlockConcretePowder powder = (BlockConcretePowder) block;
            for (int meta = 0; meta <= powder.META_AMOUNT; meta++) {
                variants.add(new ResourceLocation("plug", powder.getModelNameForMeta(meta)));
            }
            registerBlockItem(block, item, variants);
            return;
        }

        if (block instanceof BlockEnergy) {
            final BlockEnergy energy = (BlockEnergy) block;
            for (int meta = 0; meta <= BlockEnergy.META_MAX; meta++) {
                variants.add(new ResourceLocation("plug", energy.getModelNameForMeta(meta)));
            }
            registerBlockItem(block, item, variants);
            return;
        }

        // Default: same as registry name
        variants.add(block.getRegistryName());
        registerBlockItem(block, item, variants);
    }

    private static void registerBlockItem(final Block block, final Item item, List<ResourceLocation> variants) {
        net.minecraft.client.renderer.block.model.ModelBakery.registerItemVariants(item, variants.toArray(new ResourceLocation[0]));
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                String path = resolveBlockItemPath(block, stack.getItemDamage());
                // Same rule as items: the registered variant location is "plug:<path>#inventory"
                // (Forge adds the "item/" prefix itself while loading the model).
                return new ModelResourceLocation(new ResourceLocation("plug", path), "inventory");
            }
        });
    }

    private static String resolveBlockItemPath(Block block, int meta) {
        if (block instanceof BlockCaveVines || block instanceof BlockCaveVinesGrowing) {
            return "flat/cave_vines_head";
        }
        if (block instanceof BlockPlugStair || block instanceof BlockColorStair) {
            return block.getRegistryName().getResourcePath();
        }
        if (block instanceof BlockColorSlab) {
            String suffix = ((BlockColorSlab) block).isDouble() ? "_full" : "_half";
            return block.getRegistryName().getResourcePath() + suffix;
        }
        if (block instanceof BlockPlugSlab) {
            BlockPlugSlab slab = (BlockPlugSlab) block;
            if (slab.isDouble()) {
                return "bop_slab_double";
            }
            return "bop_slab_" + slab.getUnlocalizedName(meta);
        }
        if (block instanceof BlockBarrel) {
            return "barrel_" + ((BlockBarrel) block).name;
        }
        if (block instanceof BlockGeneric) {
            return ((BlockGeneric) block).getModelNameForMeta(meta);
        }
        if (block instanceof BlockConcrete) {
            return ((BlockConcrete) block).getModelNameForMeta(meta);
        }
        if (block instanceof BlockConcretePowder) {
            return ((BlockConcretePowder) block).getModelNameForMeta(meta);
        }
        if (block instanceof BlockEnergy) {
            return ((BlockEnergy) block).getModelNameForMeta(meta);
        }
        return block.getRegistryName().getResourcePath();
    }

    private static void registerColorHandlers() {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof BlockColorSlab) {
                final BlockColorSlab slab = (BlockColorSlab) block;
                blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                                packColor(BlockColorData.getColorData(worldIn, pos, slab.metaColor)),
                        block);
            } else if (block instanceof BlockColorStair) {
                final BlockColorStair stair = (BlockColorStair) block;
                blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                                packColor(BlockColorData.getColorData(worldIn, pos, stair.curMetadata)),
                        block);
            } else if (block instanceof BlockColor) {
                blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                                packColor(BlockColorData.getColorData(worldIn, pos, state.getValue(BlockColor.META))),
                        block);
                Item item = Item.getItemFromBlock(block);
                if (item != null) {
                    itemColors.registerItemColorHandler((stack, tintIndex) -> BlockColor.getRenderColor(stack.getItemDamage() & 0xF), item);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
        boolean colorBlocksPresent = false;
        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof BlockColorSlab || block instanceof BlockColorStair) {
                colorBlocksPresent = true;
                break;
            }
        }
        if (!colorBlocksPresent) {
            return;
        }
        TextureMap map = event.getMap();
        map.setTextureEntry(new TextureColorBlockBase("cobblestone"));
        map.setTextureEntry(new TextureColorBlockBase("stonebrick"));
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        for (ModelResourceLocation location : event.getModelRegistry().getKeys()) {
            if (!"plug".equals(location.getResourceDomain()) || !"inventory".equals(location.getVariant())) {
                continue;
            }
            wrapItemModel(event, location);
        }

        // Energy blocks: force full brightness
        if (ModBlocks.energyBlock != null) {
            for (int meta = 0; meta <= BlockEnergy.META_MAX; meta++) {
                ModelResourceLocation location = new ModelResourceLocation(ModBlocks.energyBlock.getRegistryName(), "meta=" + meta);
                IBakedModel model = event.getModelRegistry().getObject(location);
                if (model != null && !(model instanceof FullbrightBakedModel)) {
                    event.getModelRegistry().putObject(location, new FullbrightBakedModel(model));
                }
            }
        }
    }

    private static void wrapItemModel(ModelBakeEvent event, ModelResourceLocation location) {
        String key = location.getResourcePath();
        boolean pullingStage = key.endsWith("_pulling_0") || key.endsWith("_pulling_1") || key.endsWith("_pulling_2");
        if (pullingStage) {
            key = key.substring(0, key.lastIndexOf("_pulling_"));
        }
        Item item = resolveItem(key);
        if (item == null) {
            return;
        }

        IBakedModel model = event.getModelRegistry().getObject(location);
        if (model == null || model instanceof PerspectivePluginModel || model instanceof FullbrightBakedModel) {
            return;
        }

        if (item instanceof ItemPluginBowInterface) {
            ItemPluginBowInterface bow = (ItemPluginBowInterface) item;
            IBakedModel pull0 = PluginModelLoader.bakeFlatModel(ModItems.resolveTexturePath(bow.textureName + "_pulling_0"), DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter());
            IBakedModel pull1 = PluginModelLoader.bakeFlatModel(ModItems.resolveTexturePath(bow.textureName + "_pulling_1"), DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter());
            IBakedModel pull2 = PluginModelLoader.bakeFlatModel(ModItems.resolveTexturePath(bow.textureName + "_pulling_2"), DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter());
            pull0 = new PerspectivePluginModel(pull0, bow, pull0.getOverrides(), true);
            pull1 = new PerspectivePluginModel(pull1, bow, pull1.getOverrides(), true);
            pull2 = new PerspectivePluginModel(pull2, bow, pull2.getOverrides(), true);
            event.getModelRegistry().putObject(location,
                    new PerspectivePluginModel(model, item, new BowPullOverrideList(pull0, pull1, pull2), true));
            return;
        }

        // All plugin items (weapons, cards, pills, materials, ...) get the
        // vanilla camera transforms so they hold/scale like vanilla items.
        event.getModelRegistry().putObject(location,
                new PerspectivePluginModel(model, item, model.getOverrides(), false));
    }

    private static int packColor(float[] col) {
        return (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F);
    }

    private static Item resolveItem(String key) {
        String base = key;
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
            }
        }
        return Item.REGISTRY.getObject(new ResourceLocation("plug", base));
    }
}
