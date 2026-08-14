package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumMidnight;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockMidnight extends BlockGeneric<EnumMidnight> {

    public static final PropertyEnum<EnumMidnight> VARIANT = PropertyEnum.create("variant", EnumMidnight.class);

    @Override
    protected PropertyEnum<EnumMidnight> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public BlockMidnight(){
        super("midnight", EnumMidnight.class, Material.ROCK);

        this.setHardness(3.0f);
        this.setResistance(15.0f);
        this.setSoundType(SoundType.STONE);
    }
}
