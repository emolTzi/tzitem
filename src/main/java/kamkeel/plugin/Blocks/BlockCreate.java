package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumCreate;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockCreate extends BlockGeneric {

    public static final PropertyEnum<EnumCreate> VARIANT = PropertyEnum.create("variant", EnumCreate.class);

    @Override
    protected PropertyEnum<EnumCreate> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public BlockCreate(String blockName, Class enumClass) {
        super(blockName, enumClass, Material.ROCK);

        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
    }
}
