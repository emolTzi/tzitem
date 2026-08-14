package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumLightStone;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockLightStone extends BlockGeneric {

    public static final PropertyEnum<EnumLightStone> VARIANT = PropertyEnum.create("variant", EnumLightStone.class);

    @Override
    protected PropertyEnum<EnumLightStone> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public BlockLightStone(String blockName, Class enumClass) {
        super(blockName, enumClass, Material.GLASS);

        this.setHardness(0.3F);
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(1F);
    }
}
