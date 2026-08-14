package kamkeel.plugin.Enum.Blocks;

public enum EnumMidnight implements IBlockEnum {

    base(0, "Base", 1),
    natural(1, "Natural", 1),
    pure(2, "Pure", 1),
    hyper(3, "Hyper", 1),
    compressed(4, "Compressed", 1);

    private final int meta;
    private final String name;
    private final int harvestLevel;

    private EnumMidnight(int meta, String name, int harvestLevel){

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

    public static int count(){
        return values().length;
    }
}
