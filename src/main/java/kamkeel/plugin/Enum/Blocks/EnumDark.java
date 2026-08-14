package kamkeel.plugin.Enum.Blocks;

public enum EnumDark implements IBlockEnum {

    signature(0, "Signature", 1),
    puresignature(1, "PureSignature", 1),
    impure(2, "Impure", 1),
    pure(3, "Pure", 1);

    private final int meta;
    private final String name;
    private final int harvestLevel;

    private EnumDark(int meta, String name, int harvestLevel){

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
