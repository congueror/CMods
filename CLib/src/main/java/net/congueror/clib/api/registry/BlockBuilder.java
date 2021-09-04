package net.congueror.clib.api.registry;

import net.congueror.clib.api.data.BlockModelDataGenerator;
import net.congueror.clib.api.data.ItemModelDataGenerator;
import net.congueror.clib.api.data.LootTableDataGenerator;
import net.congueror.clib.api.objects.blocks.ICLibBlock;
import net.congueror.clib.util.ModItemGroups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.datafix.fixes.BlockEntityKeepPacked;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * A class that can be used for registering blocks and block items to the game. This builder class provides many methods that can be used to ease the burden of registering blocks.
 */
@SuppressWarnings("unused")
public class BlockBuilder {
    private final ResourceLocation name;
    private final Block block;

    /**
     * All the block tags added via the {@link #withNewItemTag(String)} method. The string is simply the full name of the tag, e.g. "forge:storage_blocks/steel".
     */
    public static final Map<String, Tag.Named<Block>> BLOCK_TAGS = new HashMap<>();

    /**
     * A list of all the "built" BlockBuilder objects. This is used to iterate through all the objects.
     */
    public static final List<BlockBuilder> OBJECTS = new ArrayList<>();

    public final Map<Tag.Named<Item>, Tag.Named<Item>> itemTagsGen = new HashMap<>();
    public final Map<String, Tag.Named<Item>> itemTags = new HashMap<>();
    public final Map<Tag.Named<Block>, Tag.Named<Block>> blockTagsGen = new HashMap<>();
    public final Map<String, Tag.Named<Block>> blockTags = new HashMap<>();
    @Nullable public BiConsumer<BlockModelDataGenerator, Block> blockModel;
    @Nullable public BiConsumer<ItemModelDataGenerator, Block> itemModel;
    public BiConsumer<LootTableDataGenerator, Block> lootTable;
    public final Map<String, String> locale = new HashMap<>();

    public boolean generateBlockItem = true;
    public CreativeModeTab tab = ModItemGroups.BlocksIG.instance;
    public int burnTime;
    public int containerType;

    /**
     * Convenience constructor for those who don't want to make a new resource location object everytime.
     * @param name The name of your block, this string MUST include a modid and a path like so: "modid:my_block"
     * @param block See {@link #BlockBuilder(ResourceLocation, Block)}
     */
    public BlockBuilder(String name, Block block) {
        this(new ResourceLocation(name), block);
    }

    /**
     * This is the main constructor of the builder.
     * @param name The {@link ResourceLocation} of the item, which includes your mod's id and the path.
     * @param block A new instance of a class which extends {@link Block}.
     */
    public BlockBuilder(ResourceLocation name, Block block) {
        this.name = name;
        this.block = block;
    }

    /**
     * Getter for the block
     * @return the block.
     */
    public Block getBlock() {
        return block;
    }

    /**
     * Registers a block item only if the {@link #withGeneratedBlockItem(boolean)} is called. Otherwise, you need to register your block item manually (or not, it's your choice).
     */
    public static void registerBlockItems(final RegistryEvent.Register<Item> e) {
        final IForgeRegistry<Item> registry = e.getRegistry();
        OBJECTS.stream().filter(blockBuilder -> blockBuilder.generateBlockItem).forEach(builder -> {
            Block block = builder.getBlock();
            Item.Properties properties = new Item.Properties().tab(builder.tab);
            BlockItem item = new BlockItem(block, properties) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return builder.burnTime;
                }

                @Override
                public boolean hasContainerItem(ItemStack stack) {
                    return builder.containerType > 0;
                }

                @Override
                public ItemStack getContainerItem(ItemStack itemStack) {
                    if (builder.containerType == 2) {
                        ItemStack stack1 = new ItemStack(this.asItem());
                        stack1.setDamageValue(itemStack.getDamageValue() + 1);
                        if (stack1.getDamageValue() > stack1.getMaxDamage()) {
                            return new ItemStack(Items.AIR);
                        } else {
                            return stack1;
                        }
                    }
                    return super.getContainerItem(itemStack);
                }
            };
            item.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
            registry.register(item);
        });
    }

    /**
     * Builds/Registers this BlockBuilder object to the DeferredRegister passed in.
     * @param register The block {@link DeferredRegister} of your mod.
     * @return The registered {@link RegistryObject}.
     */
    public RegistryObject<Block> build(DeferredRegister<Block> register) {
        OBJECTS.add(this);
        return register.register(name.getPath(), () -> block);
    }

    /**
     * Adds an existing tag to this block's item, for example "minecraft:logs"
     * @param tags An array of tags to be added to the block's item.
     */
    @SafeVarargs
    public final BlockBuilder withExistingItemTags(Tag.Named<Item>... tags) {
        for (Tag.Named<Item> tag : tags) {
            itemTags.put(tag.getName().toString(), tag);
        }
        return this;
    }

    /**
     * A new tag to be added to the block's item. Accessible through the {@link ItemBuilder#ITEM_TAGS} field. For already defined tags use {@link #withExistingItemTags(Tag.Named[])}.
     * Note that if the string has a Backslash("/") it will create and add 2 tags, e.g. Passing this string will add the following tags:
     * <p>
     * "forge:ores/my_ore" -> "forge:ores", "forge:ores/my_ore"
     * @param tagName The full name of the tag, e.g. "forge:ores/my_ore"
     */
    public BlockBuilder withNewItemTag(String tagName) {
        itemTags.put(tagName, ItemTags.createOptional(new ResourceLocation(tagName)));
        ItemBuilder.ITEM_TAGS.put(tagName, ItemTags.createOptional(new ResourceLocation(tagName)));
        if (tagName.contains("/")) {
            String parent = tagName.substring(0, tagName.indexOf("/"));
            itemTagsGen.put(ItemTags.createOptional(new ResourceLocation(parent)), ItemTags.createOptional(new ResourceLocation(tagName)));
        }
        return this;
    }

    /**
     * Adds an existing tag to this block, for example "minecraft:logs"
     * @param tags An array of tags to be added to the block.
     */
    @SafeVarargs
    public final BlockBuilder withExistingBlockTags(Tag.Named<Block>... tags) {
        for (Tag.Named<Block> tag : tags) {
            blockTags.put(tag.getName().toString(), tag);
        }
        return this;
    }

    /**
     * A new tag to be added to the block. Accessible through the {@link #BLOCK_TAGS} field. For already defined tags use {@link #withExistingBlockTags(Tag.Named[])}.
     * Note that if the string has a Backslash("/") it will create and add 2 tags, e.g. Passing this string will add the following tags:
     * <p>
     * "forge:ores/my_ore" -> "forge:ores", "forge:ores/my_ore"
     * @param tagName The full name of the tag, e.g. "forge:ores/my_ore"
     */
    public BlockBuilder withNewBlockTag(String tagName) {
        blockTags.put(tagName, BlockTags.createOptional(new ResourceLocation(tagName)));
        BLOCK_TAGS.put(tagName, BlockTags.createOptional(new ResourceLocation(tagName)));
        if (tagName.contains("/")) {
            String parent = tagName.substring(0, tagName.indexOf("/"));
            blockTagsGen.put(BlockTags.createOptional(new ResourceLocation(parent)), BlockTags.createOptional(new ResourceLocation(tagName)));
        }
        return this;
    }

    /**
     * Allows you to generate a block state / model for this block. By default, a simple block and block item (only if {@link #generateBlockItem} is true) will be generated.
     * pass in null if you do not want to generate a block state at all.
     * @param ctx A {@link BiConsumer} of types {@link BlockModelDataGenerator} and {@link Block}.
     *            You can find many useful methods for generating block states inside the {@link BlockModelDataGenerator} class
     */
    public BlockBuilder withBlockModel(BiConsumer<BlockModelDataGenerator, Block> ctx) {
        this.blockModel = ctx;
        return this;
    }

    /**
     * Allows you to generate an item model for this block to be shown in the inventory. By default, unless this method is called, a simple block item model will be generated.
     * Passing null will create a block item model.
     * @param ctx A {@link BiConsumer} of types {@link ItemModelDataGenerator} and {@link Block}.
     *            You can find many useful methods for generating item models inside the {@link ItemModelDataGenerator} class
     */
    public BlockBuilder withItemModel(BiConsumer<ItemModelDataGenerator, Block> ctx) {
        this.itemModel = ctx;
        return this;
    }

    /**
     * Allows you to add a custom loot table to this block. By default, unless this method is called, a simple self-drop loot table is generated.
     * @param ctx A {@link BiConsumer} of types {@link LootTableDataGenerator} and {@link Block}.
     *            You can find many useful methods for generating item models inside the {@link LootTableDataGenerator} class
     */
    public BlockBuilder withLootTable(BiConsumer<LootTableDataGenerator, Block> ctx) {
        this.lootTable = ctx;
        return this;
    }

    /**
     * Adds a translation to this block in the en_us locale. If you wish to change locale use {@link #withTranslation(String, String)}
     * @param translation The name of the translated block, e.g. "My Ore"
     */
    public BlockBuilder withTranslation(String translation) {
        this.locale.put("en_us", translation);
        return this;
    }

    /**
     * Adds a translation to this block in the given locale.
     * @param translation The name of the translated block, e.g. "My Ore"
     * @param locale The localization this translation will be added to, e.g. "en_us"
     */
    public BlockBuilder withTranslation(String translation, String locale) {
        this.locale.put(locale, translation);
        return this;
    }

    /**
     * Whether the BlockBuilder should automatically generate a block item for this block. This by default is true.
     * If you set this to false you will not be able to use several methods such as {@link #withCreativeTab(CreativeModeTab)}, {@link #withBurnTime(int)}, e.t.c.
     * <p>
     * Methods that need this one will be mentioned in their documentation.
     * </p>
     */
    public BlockBuilder withGeneratedBlockItem(boolean shouldGenerate) {
        this.generateBlockItem = shouldGenerate;
        return this;
    }

    /**
     * Adds this block to the given creative tab. If you do not use this method the default will be {@link ModItemGroups.BlocksIG}.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method.
     * </p></strong>
     * @param tab The {@link CreativeModeTab} you want your block in
     */
    public BlockBuilder withCreativeTab(CreativeModeTab tab) {
        this.tab = tab;
        return this;
    }

    /**
     * Adds a burn time to this block's item so that it can be used as fuel in furnaces.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method.
     * </p></strong>
     * @param burnTime The amount in ticks that this item will burn for.
     */
    public BlockBuilder withBurnTime(int burnTime) {
        this.burnTime = burnTime;
        return this;
    }

    /**
     * Makes the block's item stay in the crafting grid when crafted with.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method.
     * </p></strong>
     */
    public BlockBuilder withContainerItem() {
        this.containerType = 1;
        return this;
    }

    /**
     * Adds the functionality of {@link #withContainerItem()} but the block's item will be damaged when crafted with by 1 durability point.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method.
     * </p></strong>
     */
    public BlockBuilder withDamageableContainerItem() {
        this.containerType = 2;
        return this;
    }

    //============================CLBlock Exclusive============================

    /**
     * The result of right-clicking this block with a tool. Similar to stripping wood.
     * <p><strong>
     * This method is exclusive for blocks that implement {@link ICLibBlock} interface.
     * </strong></p>
     * @param action The {@link ToolAction} that must be used, for all vanilla tool actions see {@link net.minecraftforge.common.ToolActions}
     * @param block The block that results from the tool action.
     */
    public BlockBuilder withModifiedState(ToolAction action, Supplier<? extends Block> block) {
        if (this.block instanceof ICLibBlock clBlock) {
            clBlock.setModifiedState(action, block);
        }
        return this;
    }
}
