package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumRage;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockRage extends BlockGeneric<EnumRage> {

    public static final PropertyEnum<EnumRage> VARIANT = PropertyEnum.create("variant", EnumRage.class);

    @Override
    protected PropertyEnum<EnumRage> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public BlockRage(){
        super("rage", EnumRage.class, Material.ROCK);

        this.setHardness(3.0f);
        this.setResistance(15.0f);
        this.setSoundType(SoundType.STONE);
    }
}
