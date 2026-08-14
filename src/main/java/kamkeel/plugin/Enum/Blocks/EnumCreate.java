package kamkeel.plugin.Enum.Blocks;

public enum EnumCreate implements IBlockEnum {

    raw(0, "Raw", 1),
    polished(1, "Polished", 1),
    layer(2, "Layer", 1),
    brick(3, "Brick", 1),
    small_brick(4, "Small_Brick", 1),
    cut(5, "Cut", 1);

    private final int meta;
    private final String name;
    private final int harvestLevel;

    private EnumCreate(int meta, String name, int harvestLevel){

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
