package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumAncientStone;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockAncientStone extends BlockBase {

    public static final PropertyEnum<EnumAncientStone> VARIANT = PropertyEnum.create("variant", EnumAncientStone.class);

    @Override
    protected PropertyEnum<EnumAncientStone> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public BlockAncientStone(String blockName, Class enumClass) {
        super(blockName, enumClass);

        this.setHardness(3.0f);
        this.setResistance(15.0f);
        this.setSoundType(SoundType.STONE);
    }
}
