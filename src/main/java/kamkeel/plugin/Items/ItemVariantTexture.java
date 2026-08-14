package kamkeel.plugin.Items;

/**
 * Implemented by metadata-variant items so the runtime model loader can resolve
 * the correct texture per damage value (mirrors 1.7.10 per-meta IIcon arrays).
 */
public interface ItemVariantTexture {
    /**
     * @param meta The item damage value.
     * @return The full texture path (e.g. "plug:pills/pills_basic") for that variant.
     */
    String getTextureName(int meta);

    /**
     * @return The number of metadata variants this item has.
     */
    int getVariantCount();
}
