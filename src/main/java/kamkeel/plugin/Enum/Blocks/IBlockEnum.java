package kamkeel.plugin.Enum.Blocks;

import net.minecraft.util.StringRepresentable;

/**
 * Shared interface for block-variant enums. In 1.21.1 each variant is
 * registered as its own block, so the enum mainly supplies the serialized
 * name used for registry/model naming and the localization key.
 */
public interface IBlockEnum extends StringRepresentable {

    int getMeta();

    /** Lowercase display/registry name (e.g. "signature"). */
    String getName();

    int getHarvestLevel();

    @Override
    default String getSerializedName() {
        return getName();
    }
}
