package kamkeel.plugin.Enum.Blocks;

public enum EnumAncientStone implements IBlockEnum {

    base(0, "Base", 1),
    bricks(1, "Bricks", 1),
    sigil(2, "Sigil", 1),
    tiles(3, "Tiles", 1),
    embossed(4, "Embossed", 1),
    tile(5, "Tile", 1);

    private final int meta;
    private final String name;
    private final int harvestLevel;

    private EnumAncientStone(int meta, String name, int harvestLevel){

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
