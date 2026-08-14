package kamkeel.plugin.Enum.Blocks;

public enum EnumLightStone implements IBlockEnum {

    cobble(0, "Cobble", 1),
    brick(1, "Brick", 1),
    bismuth(2, "Bismuth", 1),
    layer(3, "Layer", 1),
    neon(4, "Neon", 1),
    prism(5, "Prism", 1),
    spiral(6, "Spiral", 1),
    tile(7, "Tile", 1),
    raw(8, "Raw", 1),
    crack(9, "Crack", 1);

    private final int meta;
    private final String name;
    private final int harvestLevel;

    private EnumLightStone(int meta, String name, int harvestLevel){

        this.meta = meta;
        this.name = name;
        this.harvestLevel = harvestLevel;
    }

    public int getMeta(){
        return this.meta;
    }

    public String getName(){
        return this.name.toLowerCase();
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }
}
