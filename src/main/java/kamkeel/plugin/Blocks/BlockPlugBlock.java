package kamkeel.plugin.Blocks;

import kamkeel.plugin.PluginMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPlugBlock extends Block {

    public String textureName;

    public BlockPlugBlock(String texture){
        super(Material.ROCK);

        this.textureName = texture;

        this.setHardness(3.0f);
        this.setResistance(15.0f);
        this.setSoundType(SoundType.STONE);
        this.setLightLevel(1.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setCreativeTab(PluginMod.blocksTab);
    }
}
