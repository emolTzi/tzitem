package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumDark;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockDark extends BlockGeneric<EnumDark> {

    public static final PropertyEnum<EnumDark> VARIANT = PropertyEnum.create("variant", EnumDark.class);

    @Override
    protected PropertyEnum<EnumDark> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public BlockDark(){
        super("dark", EnumDark.class, Material.ROCK);

        this.setHardness(3.0f);
        this.setResistance(15.0f);
        this.setSoundType(SoundType.STONE);
    }
}
