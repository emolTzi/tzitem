package kamkeel.plugin.Client;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Bow pull-stage selection, replicating the 1.7.10 tick thresholds exactly:
 * j >= 18 -> pulling_2, j > 13 -> pulling_1, j > 0 -> pulling_0.
 */
@SideOnly(Side.CLIENT)
public class BowPullOverrideList extends ItemOverrideList {

    private final IBakedModel pull0;
    private final IBakedModel pull1;
    private final IBakedModel pull2;

    public BowPullOverrideList(IBakedModel pull0, IBakedModel pull1, IBakedModel pull2) {
        super(java.util.Collections.emptyList());
        this.pull0 = pull0;
        this.pull1 = pull1;
        this.pull2 = pull2;
    }

    @Override
    public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
        if (entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack) {
            int j = stack.getMaxItemUseDuration() - entity.getItemInUseCount();
            if (j >= 18) {
                return pull2;
            }
            if (j > 13) {
                return pull1;
            }
            if (j > 0) {
                return pull0;
            }
        }
        return originalModel;
    }
}
