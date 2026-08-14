package kamkeel.plugin.Enum.Blocks;

public enum EnumRage implements IBlockEnum {

    lucid(0, "Lucid", 1),
    base(1, "Base", 1),
    natural(2, "Natural", 1),
    pure(3, "Pure", 1),
    danger(4, "Danger", 1),
    compressed(5, "Compressed", 1);

    private final int meta;
    private final String name;
    private final int harvestLevel;

    private EnumRage(int meta, String name, int harvestLevel){

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
