package kamkeel.plugin.Enum.Blocks;

public enum EnumEldritch implements IBlockEnum {

    base(0, "Base", 1),
    natural(1, "Natural", 1),
    engraved(2, "Engraved", 1),
    brick(3, "Brick", 1),
    tile(4, "Tile", 1),
    stone(5, "Stone", 1),
    lamp(6, "Lamp", 1);

    private final int meta;
    private final String name;
    private final int harvestLevel;

    private EnumEldritch(int meta, String name, int harvestLevel){

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
