package kamkeel.plugin.Blocks;

import kamkeel.plugin.Enum.Blocks.EnumAncientStone;
import kamkeel.plugin.Enum.Blocks.EnumCreate;
import kamkeel.plugin.Enum.Blocks.EnumDark;
import kamkeel.plugin.Enum.Blocks.EnumDeepCrystal;
import kamkeel.plugin.Enum.Blocks.EnumEldritch;
import kamkeel.plugin.Enum.Blocks.EnumLightStone;
import kamkeel.plugin.Enum.Blocks.EnumMidnight;
import kamkeel.plugin.Enum.Blocks.EnumRage;
import kamkeel.plugin.Enum.Blocks.IBlockEnum;
import kamkeel.plugin.ModRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Block registration. Each variant of the old meta-blocks is registered as a
 * separate block (the 1.21.1 idiom), reusing the generated models.
 */
public final class ModBlocks {

    private ModBlocks() {}

    /** Every registered block, in registration order (used for item + tab wiring). */
    public static final List<DeferredBlock<Block>> ALL = new ArrayList<>();

    private static final BlockBehaviour.Properties STONE = BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE).strength(3.0F, 15.0F).sound(SoundType.STONE)
            .requiresCorrectToolForDrops();

    private static final BlockBehaviour.Properties CREATE = BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE).strength(1.5F, 10.0F).sound(SoundType.STONE)
            .requiresCorrectToolForDrops();

    private static final BlockBehaviour.Properties DEEP_CRYSTAL = BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK).strength(0.3F, 0.3F).sound(SoundType.GLASS)
            .noOcclusion().lightLevel(s -> 6);

    private static final BlockBehaviour.Properties LIGHTSTONE = BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE).strength(0.3F).sound(SoundType.GLASS)
            .noOcclusion().lightLevel(s -> 15);

    private static final BlockBehaviour.Properties CONCRETE = BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE).strength(1.8F).sound(SoundType.STONE)
            .requiresCorrectToolForDrops();

    private static final BlockBehaviour.Properties CONCRETE_POWDER = BlockBehaviour.Properties.of()
            .mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND);

    private static final BlockBehaviour.Properties ENERGY = BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE).strength(1.8F).sound(SoundType.STONE)
            .requiresCorrectToolForDrops();

    // ---- Enum-variant blocks ----
    public static final List<DeferredBlock<Block>> DARK = enumBlocks("dark", EnumDark.class, STONE);
    public static final List<DeferredBlock<Block>> MIDNIGHT = enumBlocks("midnight", EnumMidnight.class, STONE);
    public static final List<DeferredBlock<Block>> RAGE = enumBlocks("rage", EnumRage.class, STONE);
    public static final List<DeferredBlock<Block>> DEEP_CRYSTAL_BLOCKS = enumBlocks("deep_crystal", EnumDeepCrystal.class, DEEP_CRYSTAL);
    public static final List<DeferredBlock<Block>> ELDRITCH = eldritchBlocks();
    public static final List<DeferredBlock<Block>> ANCIENT_STONE = enumBlocks("ancient_stone", EnumAncientStone.class, STONE);
    public static final List<DeferredBlock<Block>> LIGHTSTONE_BLOCKS = enumBlocks("lightstone", EnumLightStone.class, LIGHTSTONE);

    // ---- Create (10 stone types x 6 variants) ----
    public static final List<DeferredBlock<Block>> CREATE_BLOCKS = new ArrayList<>();
    static {
        String[] bases = {"asurine", "crimsite", "deepslate", "dripstone", "limestone",
                "tuff", "ochrum", "veridium", "scoria", "scorchia"};
        for (String base : bases) {
            CREATE_BLOCKS.addAll(enumBlocks(base, EnumCreate.class, CREATE));
        }
    }

    // ---- Barrels ----
    public static final List<DeferredBlock<Block>> BARRELS = new ArrayList<>();
    public static DeferredBlock<Block> CHERRY_BARREL;

    // ---- Color blocks (concrete / powder / energy) + cave vines ----
    public static final List<DeferredBlock<Block>> CONCRETE_BLOCKS = new ArrayList<>();
    public static final List<DeferredBlock<Block>> CONCRETE_POWDER_BLOCKS = new ArrayList<>();
    public static final List<DeferredBlock<Block>> ENERGY_BLOCKS = new ArrayList<>();
    public static DeferredBlock<Block> CAVE_VINES;
    public static DeferredBlock<Block> CAVE_VINES_GROWING;

    public static void register() {
        String[] woods = {"oak", "spruce", "birch", "jungle", "dark_oak", "acacia", "warped", "crimson", "cherry"};
        for (String wood : woods) {
            DeferredBlock<Block> barrel = ModRegistries.BLOCKS.register(wood + "_barrel", BlockBarrel::new);
            BARRELS.add(barrel);
            ALL.add(barrel);
            if ("cherry".equals(wood)) {
                CHERRY_BARREL = barrel;
            }
        }
        registerColorBlocks();
    }

    /** Concrete/powder (28 colors each), energy (16 colors), and cave vines. */
    private static void registerColorBlocks() {
        String[] concreteColors = {"burgundy", "caramel", "chocolate", "denim", "haze", "mint",
                "peanut", "clover", "pearl", "mustard", "sky_blue", "periwinkle", "peach", "plum",
                "avocado", "red_brown", "blood", "seafoam", "mauve", "seaweed", "carbon", "indigo",
                "khaki", "ash", "ivy", "ivory", "camel", "salmon"};
        for (String color : concreteColors) {
            DeferredBlock<Block> concrete = ModRegistries.BLOCKS.register("concrete_" + color,
                    () -> new Block(CONCRETE));
            DeferredBlock<Block> powder = ModRegistries.BLOCKS.register("concrete_powder_" + color,
                    () -> new ConcretePowderBlock(concrete.get(), CONCRETE_POWDER));
            CONCRETE_BLOCKS.add(concrete);
            CONCRETE_POWDER_BLOCKS.add(powder);
            ALL.add(concrete);
            ALL.add(powder);
        }

        String[] energyColors = {"blood_red", "baby_blue", "dark_blue", "deep_blue", "green",
                "hot_pink", "lemon", "light_blue", "lime", "magenta", "orange", "purple", "red",
                "teal", "turquoise", "yellow"};
        int[] lightLevels = {15, 7, 10, 6, 10, 5, 11, 7, 12, 10, 9, 5, 3, 3, 5, 0};
        for (int i = 0; i < energyColors.length; i++) {
            final int light = lightLevels[i];
            DeferredBlock<Block> energy = ModRegistries.BLOCKS.register("energy_block_" + energyColors[i],
                    () -> new Block(ENERGY.lightLevel(s -> light)));
            ENERGY_BLOCKS.add(energy);
            ALL.add(energy);
        }

        CAVE_VINES = ModRegistries.BLOCKS.register("cave_vines",
                () -> new CaveVinesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT)
                        .strength(0.2F).sound(SoundType.GRASS).noOcclusion().noCollission()
                        .replaceable()));
        CAVE_VINES_GROWING = ModRegistries.BLOCKS.register("cave_vines_growing",
                () -> new CaveVinesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT)
                        .strength(0.2F).sound(SoundType.GRASS).noOcclusion().noCollission()
                        .replaceable()));
        ALL.add(CAVE_VINES);
        ALL.add(CAVE_VINES_GROWING);
    }

    /** Registers one block per enum constant, named "<base>_<variant>". */
    private static <T extends Enum<T> & IBlockEnum> List<DeferredBlock<Block>> enumBlocks(
            String base, Class<T> enumClass, BlockBehaviour.Properties properties) {
        List<DeferredBlock<Block>> list = new ArrayList<>();
        for (T variant : enumClass.getEnumConstants()) {
            String name = base + "_" + variant.getSerializedName();
            DeferredBlock<Block> block = ModRegistries.BLOCKS.register(name, () -> new Block(properties));
            list.add(block);
            ALL.add(block);
        }
        return list;
    }

    /** Eldritch: the "lamp" variant emits light. */
    private static List<DeferredBlock<Block>> eldritchBlocks() {
        List<DeferredBlock<Block>> list = new ArrayList<>();
        for (EnumEldritch variant : EnumEldritch.class.getEnumConstants()) {
            String name = "eldritch_" + variant.getSerializedName();
            final boolean lamp = variant.getMeta() == 6;
            DeferredBlock<Block> block = ModRegistries.BLOCKS.register(name, () -> new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.STONE)
                            .strength(3.0F, 15.0F)
                            .sound(SoundType.STONE)
                            .requiresCorrectToolForDrops()
                            .lightLevel(s -> lamp ? 10 : 0)));
            list.add(block);
            ALL.add(block);
        }
        return list;
    }
}
