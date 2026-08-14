package kamkeel.plugin.Blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Cave vines: a climbable hanging plant with a head/body variant.
 * Mirrors the 1.12.2 BlockCaveVines head/body metadata states.
 */
public class CaveVinesBlock extends Block {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 1);

    public CaveVinesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }
}
