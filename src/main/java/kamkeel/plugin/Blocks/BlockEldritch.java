package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumEldritch;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockEldritch extends BlockGeneric<EnumEldritch> {

    public static final PropertyEnum<EnumEldritch> VARIANT = PropertyEnum.create("variant", EnumEldritch.class);

    @Override
    protected PropertyEnum<EnumEldritch> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public BlockEldritch(String blockName, Class enumClass) {
        super(blockName, enumClass, Material.ROCK);

        this.setHardness(3.0f);
        this.setResistance(15.0f);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public int getLightValue(IBlockState state) {
        if(((EnumEldritch) state.getValue(VARIANT)).getMeta() == 6)
            return 10;

        return 0;
    }
}
