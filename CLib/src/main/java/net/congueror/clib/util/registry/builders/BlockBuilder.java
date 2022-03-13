package net.congueror.clib.util.registry.builders;

import net.congueror.clib.util.registry.data.BlockModelDataProvider;
import net.congueror.clib.util.registry.data.ItemModelDataProvider;
import net.congueror.clib.util.registry.data.LootTableDataProvider;
import net.congueror.clib.blocks.generic.ICLibBlock;
import net.congueror.clib.util.CreativeTabs;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A class that can be used for registering blocks and block items to the game. This builder class provides many methods that can be used to ease the burden of registering blocks.
 */
@SuppressWarnings("unused")
public class BlockBuilder {
    private final String name;
    public final Block block;
    public RegistryObject<Block> regObject;

    /**
     * All the block tags added via the {@link #withNewItemTag(String)} method. The string is simply the full name of the tag, e.g. "forge:storage_blocks/steel".
     */
    public static final Map<String, Tag.Named<Block>> BLOCK_TAGS = new HashMap<>();

    public static final Map<String, List<BlockBuilder>> OBJECTS = new HashMap<>();

    public final Map<Tag.Named<Block>, Tag.Named<Block>> blockTagsGen = new HashMap<>();
    public final Map<String, Tag.Named<Block>> blockTags = new HashMap<>();
    @Nullable
    public BiConsumer<BlockModelDataProvider, Block> blockModel = BlockStateProvider::simpleBlock;
    public BiConsumer<LootTableDataProvider, Block> lootTable = LootTableDataProvider::createStandardBlockDrop;
    public final Map<String, String> locale = new HashMap<>();

    public boolean generateBlockItem = true;
    private Function<Block, BlockItem> item = null;
    public Item.Properties itemProperties = new Item.Properties().tab(CreativeTabs.AssortmentsIG.instance);
    public int burnTime;
    public int containerType;

    public final List<BiConsumer<Consumer<FinishedRecipe>, Block>> recipes = new ArrayList<>();
    public final Map<Tag.Named<Item>, Tag.Named<Item>> itemTagsGen = new HashMap<>();
    public final Map<String, Tag.Named<Item>> itemTags = new HashMap<>();
    @Nullable
    public BiConsumer<ItemModelDataProvider, Block> itemModel;

    public RenderType renderType = null;

    /**
     * Convenience method for creating a fluid block
     */
    public static BlockBuilder createFluid(String name, LiquidBlock block) {
        return new BlockBuilder(name, block)
                .withLootTable(null)
                .withBlockModel(null)
                .withGeneratedBlockItem(false);
    }

    /**
     * This is the main constructor of the builder.
     *
     * @param name  The name of the block. e.g. "my_ore"
     * @param block A new instance of a class which extends {@link Block}.
     */
    public BlockBuilder(String name, Block block) {
        this.name = name;
        this.block = block;
    }

    private static Stream<BlockBuilder> stream() {
        List<BlockBuilder> list = new ArrayList<>();
        OBJECTS.values().forEach(list::addAll);
        return list.stream();
    }

    /**
     * Builds/Registers this BlockBuilder object to the DeferredRegister passed in.
     *
     * @param register The block {@link DeferredRegister} of your mod.
     * @return The registered {@link RegistryObject}.
     */
    public RegistryObject<Block> build(DeferredRegister<Block> register) {
        String modid = ObfuscationReflectionHelper.getPrivateValue(DeferredRegister.class, register, "modid");
        RegistryObject<Block> obj = register.register(name, () -> block);

        if (generateBlockItem) {
            BlockItem item;
            if (this.item != null) {
                item = this.item.apply(block);
            } else {
                item = new BlockItem(block, itemProperties) {
                    @Override
                    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                        return burnTime;
                    }

                    @Override
                    public boolean hasContainerItem(ItemStack stack) {
                        return containerType > 0;
                    }

                    @Override
                    public ItemStack getContainerItem(ItemStack itemStack) {
                        if (containerType == 2) {
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
            }

            new ItemBuilder(name, item)
                    .withItemModel(null)
                    .build(ItemBuilder.REGISTERS.get(modid));
        }

        List<BlockBuilder> newList;
        if (OBJECTS.get(modid) != null) {
            newList = new ArrayList<>(OBJECTS.get(modid));
        } else {
            newList = new ArrayList<>();
        }
        newList.add(this);
        OBJECTS.put(modid, newList);
        this.regObject = obj;
        return obj;
    }

    /**
     * Adds an existing tag to this block's item, for example "minecraft:logs"
     *
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
     *
     * @param tagName The full name of the tag, e.g. "forge:ores/my_ore"
     */
    public BlockBuilder withNewItemTag(String tagName) {
        itemTags.putIfAbsent(tagName, ItemTags.createOptional(new ResourceLocation(tagName)));
        ItemBuilder.ITEM_TAGS.putIfAbsent(tagName, ItemTags.createOptional(new ResourceLocation(tagName)));
        if (tagName.contains("/")) {
            String parent = tagName.substring(0, tagName.indexOf("/"));
            itemTagsGen.putIfAbsent(ItemTags.createOptional(new ResourceLocation(parent)), ItemTags.createOptional(new ResourceLocation(tagName)));
        }
        return this;
    }

    /**
     * Adds an existing tag to this block, for example "minecraft:logs"
     *
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
     *
     * @param tagName The full name of the tag, e.g. "forge:ores/my_ore"
     */
    public BlockBuilder withNewBlockTag(String tagName) {
        Tags.IOptionalNamedTag<Block> tag = BlockTags.createOptional(new ResourceLocation(tagName));
        blockTags.putIfAbsent(tagName, tag);
        BLOCK_TAGS.putIfAbsent(tagName, tag);
        if (tagName.contains("/")) {
            String parent = tagName.substring(0, tagName.indexOf("/"));
            blockTagsGen.putIfAbsent(BlockTags.createOptional(new ResourceLocation(parent)), tag);
        }
        return this;
    }

    /**
     * Allows you to generate a block state / model for this block. By default, a simple block and block item (only if {@link #generateBlockItem} is true) will be generated.
     * pass in null if you do not want to generate a block state at all.
     *
     * @param ctx A {@link BiConsumer} of types {@link BlockModelDataProvider} and {@link Block}.
     *            You can find many useful methods for generating block states inside the {@link BlockModelDataProvider} class
     */
    public BlockBuilder withBlockModel(BiConsumer<BlockModelDataProvider, Block> ctx) {
        this.blockModel = ctx;
        return this;
    }

    /**
     * Allows you to generate an item model for this block to be shown in the inventory. By default, unless this method is called, a simple block item model will be generated.
     * Passing null will create a block item model.
     *
     * @param ctx A {@link BiConsumer} of types {@link ItemModelDataProvider} and {@link Block}.
     *            You can find many useful methods for generating item models inside the {@link ItemModelDataProvider} class
     */
    public BlockBuilder withItemModel(BiConsumer<ItemModelDataProvider, Block> ctx) {
        this.itemModel = ctx;
        return this;
    }

    /**
     * Allows you to add a custom loot table to this block. By default, unless this method is called, a simple self-drop loot table is generated.
     * Passing in null will not generate one.
     *
     * @param ctx A {@link BiConsumer} of types {@link LootTableDataProvider} and {@link Block}.
     *            You can find many useful methods for generating item models inside the {@link LootTableDataProvider} class
     */
    public BlockBuilder withLootTable(BiConsumer<LootTableDataProvider, Block> ctx) {
        this.lootTable = ctx;
        return this;
    }

    /**
     * Adds a translation to this block in the en_us locale. If you wish to change locale use {@link #withTranslation(String, String)}
     *
     * @param translation The name of the translated block, e.g. "My Ore"
     */
    public BlockBuilder withTranslation(String translation) {
        this.locale.put("en_us", translation);
        return this;
    }

    /**
     * Adds a translation to this block in the given locale.
     *
     * @param translation The name of the translated block, e.g. "My Ore"
     * @param locale      The localization this translation will be added to, e.g. "en_us"
     */
    public BlockBuilder withTranslation(String translation, String locale) {
        this.locale.put(locale, translation);
        return this;
    }

    public BlockBuilder withRecipe(BiConsumer<Consumer<FinishedRecipe>, Block> recipe) {
        this.recipes.add(recipe);
        return this;
    }

    public BlockBuilder withRenderType(RenderType type) {
        this.renderType = type;
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
     * The item instance that the generated block item will use. By default, this is a normal BlockItem. <br>
     * Note: this overrides the {@link #withItemProperties(Item.Properties)} method.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method.
     * </p></strong>
     *
     * @param item A function that takes in the final block instance and returns the item instance.
     */
    public BlockBuilder withItem(Function<Block, BlockItem> item) {
        this.item = item;
        return this;
    }

    /**
     * Adds this block to the given creative tab. If you do not use this method the default will be {@link CreativeTabs.AssortmentsIG}.
     * If {@link #withItemProperties(Item.Properties)} was used, the tab that was set last will be the final tab.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method.
     * </p></strong>
     *
     * @param tab The {@link CreativeModeTab} you want your block in
     */
    public BlockBuilder withCreativeTab(CreativeModeTab tab) {
        this.itemProperties.tab(tab);
        return this;
    }

    /**
     * Adds a burn time to this block's item so that it can be used as fuel in furnaces.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method.
     * </p></strong>
     *
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

    /**
     * The properties will be passed on to the block item create.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method.
     * </p></strong>
     */
    public BlockBuilder withItemProperties(Item.Properties properties) {
        this.itemProperties = properties;
        return this;
    }

    //============================CLBlock Exclusive============================

    /**
     * The result of right-clicking this block with a tool. Similar to stripping wood.
     * <p><strong>
     * This method is exclusive for blocks that implement {@link ICLibBlock} interface.
     * </strong></p>
     *
     * @param action The {@link ToolAction} that must be used, for all vanilla tool actions see {@link net.minecraftforge.common.ToolActions}
     * @param block  The block that results from the tool action.
     */
    public BlockBuilder withModifiedState(ToolAction action, Supplier<? extends Block> block) {
        if (this.block instanceof ICLibBlock clBlock) {
            clBlock.setModifiedState(action, block);
        }
        return this;
    }
}
