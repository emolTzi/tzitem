package kamkeel.plugin.Enum.Blocks;

public enum EnumDeepCrystal implements IBlockEnum {

    dark(0, "Dark", 1),
    puredark(1, "PureDark", 1),
    toxic(2, "Toxic", 1),
    void_(3, "Void", 1),
    purevoid(4, "PureVoid", 1),
    ovy(5, "Ovy", 1),
    rune(6, "Rune", 1),
    grey(7, "Grey", 1),
    shadow(8, "Shadow", 1),
    destox(9, "Destox", 1);

    private final int meta;
    private final String name;
    private final int harvestLevel;

    private EnumDeepCrystal(int meta, String name, int harvestLevel){

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
