package net.congueror.clib.util.registry.builders;

import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.ListMap;
import net.congueror.clib.util.registry.data.BlockModelDataProvider;
import net.congueror.clib.util.registry.data.ItemModelDataProvider;
import net.congueror.clib.util.registry.data.LootTableDataProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A class that can be used for registering blocks and block items to the game. This builder class provides many methods that can be used to ease the burden of registering blocks.
 */
@SuppressWarnings("unused")
public class BlockBuilder<B extends Block, I extends BlockItem> implements Builder<B> {
    public RegistryObject<B> regObject;

    @Nullable
    private final DeferredRegister<Item> register;

    public static final ListMap<String, BlockBuilder<?, ?>> OBJECTS = new ListMap<>();

    /**
     * All the block tags added via the {@link #withItemTag(String)} method. The string is simply the full name of the tag, e.g. "forge:storage_blocks/steel".
     */
    public static final Map<String, TagKey<Block>> BLOCK_TAGS = new HashMap<>();
    public final Map<TagKey<Block>, TagKey<Block>> blockTagsGen = new HashMap<>();
    public final Map<String, TagKey<Block>> blockTags = new HashMap<>();

    @Nullable
    public BiConsumer<BlockModelDataProvider, B> blockModel = BlockStateProvider::simpleBlock;
    @Nullable
    public BiConsumer<ItemModelDataProvider, B> itemModel;

    public BiConsumer<LootTableDataProvider, B> lootTable = LootTableDataProvider::createStandardBlockDrop;
    public final List<BiConsumer<Consumer<FinishedRecipe>, Block>> recipes = new ArrayList<>();
    public final Map<String, String> locale = new HashMap<>();

    public RenderType renderType = null;


    public boolean generateBlockItem = true;
    private Function<B, I> item = null;
    public Item.Properties itemProperties = new Item.Properties().tab(CreativeTabs.AssortmentsIG.instance);
    public int burnTime;
    public int containerType;

    public final Map<TagKey<Item>, TagKey<Item>> itemTagsGen = new HashMap<>();
    public final Map<String, TagKey<Item>> itemTags = new HashMap<>();

    public record BlockDeferredRegister(DeferredRegister<Block> register, DeferredRegister<Item> itemRegister) {

        public <B extends Block, I extends BlockItem> BlockBuilder<B, I> create(String name, Supplier<B> block) {
            return new BlockBuilder<>(register.register(name, block), itemRegister);
        }

        public <B extends LiquidBlock, I extends BlockItem> BlockBuilder<B, I> createFluid(String name, Supplier<B> block) {
            return new BlockBuilder<B, I>(register.register(name, block), itemRegister)
                    .withLootTable(null)
                    .withBlockModel(null)
                    .withGeneratedBlockItem(false);
        }
    }

    public BlockBuilder(RegistryObject<B> regObject, @Nullable DeferredRegister<Item> items) {
        this.regObject = regObject;
        this.register = items;
    }

    /**
     * Returns a stream of all block builders.
     */
    private static Stream<BlockBuilder<?, ?>> stream() {
        List<BlockBuilder<?, ?>> list = new ArrayList<>();
        OBJECTS.values().forEach(list::addAll);
        return list.stream();
    }

    public RegistryObject<B> build() {
        String modid = this.regObject.getId().getNamespace();

        if (generateBlockItem) {
            Supplier<I> item;
            if (this.item != null) {
                item = () -> this.item.apply(regObject.get());
            } else {
                //noinspection unchecked
                item = () -> (I) new BlockItem(regObject.get(), itemProperties) {
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

            new ItemBuilder<>(register.register(this.regObject.getId().getPath(), item))
                    .withItemModel(null)
                    .build();
        }

        OBJECTS.addEntry(modid, this);
        return regObject;
    }

    /**
     * Adds an existing tag to this block's item, for example "minecraft:logs"
     *
     * @param tags An array of tags to be added to the block's item.
     */
    @SafeVarargs
    public final BlockBuilder<B, I> withItemTags(TagKey<Item>... tags) {
        for (TagKey<Item> tag : tags) {
            String name = tag.location().toString();
            itemTags.put(name, tag);
            if (name.contains("/")) {
                String parent = name.substring(0, name.indexOf("/"));
                itemTagsGen.put(ItemTags.create(new ResourceLocation(parent)), tag);
            }
        }
        return this;
    }

    /**
     * A new tag to be added to the block's item. For already defined tags use {@link #withItemTags(TagKey[])}.
     * Note that if the string has a Backslash("/") it will create and add 2 tags, e.g. Passing this string will add the following tags:
     * <p>
     * "forge:ores/my_ore" -> "forge:ores", "forge:ores/my_ore"
     *
     * @param tagName The full name of the tag, e.g. "forge:ores/my_ore"
     */
    public BlockBuilder<B, I> withItemTag(String tagName) {
        itemTags.putIfAbsent(tagName, ItemTags.create(new ResourceLocation(tagName)));
        if (tagName.contains("/")) {
            String parent = tagName.substring(0, tagName.indexOf("/"));
            itemTagsGen.putIfAbsent(ItemTags.create(new ResourceLocation(parent)), ItemTags.create(new ResourceLocation(tagName)));
        }
        return this;
    }

    /**
     * Adds an existing tag to this block, for example "minecraft:logs"
     *
     * @param tags An array of tags to be added to the block.
     */
    @SafeVarargs
    public final BlockBuilder<B, I> withExistingBlockTags(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) {
            blockTags.put(tag.location().toString(), tag);
        }
        return this;
    }

    /**
     * A new tag to be added to the block. Accessible through the {@link #BLOCK_TAGS} field. For already defined tags use {@link #withExistingBlockTags(TagKey[])}.
     * Note that if the string has a Backslash("/") it will create and add 2 tags, e.g. Passing this string will add the following tags:
     * <p>
     * "forge:ores/my_ore" -> "forge:ores", "forge:ores/my_ore"
     *
     * @param tagName The full name of the tag, e.g. "forge:ores/my_ore"
     */
    public BlockBuilder<B, I> withNewBlockTag(String tagName) {
        TagKey<Block> tag = BlockTags.create(new ResourceLocation(tagName));
        blockTags.putIfAbsent(tagName, tag);
        BLOCK_TAGS.putIfAbsent(tagName, tag);
        if (tagName.contains("/")) {
            String parent = tagName.substring(0, tagName.indexOf("/"));
            blockTagsGen.putIfAbsent(BlockTags.create(new ResourceLocation(parent)), tag);
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
    public BlockBuilder<B, I> withBlockModel(BiConsumer<BlockModelDataProvider, B> ctx) {
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
    public BlockBuilder<B, I> withItemModel(BiConsumer<ItemModelDataProvider, B> ctx) {
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
    public BlockBuilder<B, I> withLootTable(BiConsumer<LootTableDataProvider, B> ctx) {
        this.lootTable = ctx;
        return this;
    }

    /**
     * Adds a translation to this block in the en_us locale. If you wish to change locale use {@link #withTranslation(String, String)}
     *
     * @param translation The name of the translated block, e.g. "My Ore"
     */
    public BlockBuilder<B, I> withTranslation(String translation) {
        this.locale.put("en_us", translation);
        return this;
    }

    /**
     * Adds a translation to this block in the given locale.
     *
     * @param translation The name of the translated block, e.g. "My Ore"
     * @param locale      The localization this translation will be added to, e.g. "en_us"
     */
    public BlockBuilder<B, I> withTranslation(String translation, String locale) {
        this.locale.put(locale, translation);
        return this;
    }

    public BlockBuilder<B, I> withRecipe(BiConsumer<Consumer<FinishedRecipe>, Block> recipe) {
        this.recipes.add(recipe);
        return this;
    }

    public BlockBuilder<B, I> withRenderType(RenderType type) {
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
    public BlockBuilder<B, I> withGeneratedBlockItem(boolean shouldGenerate) {
        this.generateBlockItem = shouldGenerate;
        return this;
    }

    /**
     * The item instance that the generated block item will use. By default, this is a normal BlockItem. <br>
     * Note: this overrides the {@link #withItemProperties(Item.Properties)} method.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method to be true.
     * </p></strong>
     *
     * @param item A function that takes in the final block instance and returns the item instance.
     */
    public BlockBuilder<B, I> withItem(Function<B, I> item) {
        this.item = item;
        return this;
    }

    /**
     * Adds this block to the given creative tab. If you do not use this method the default will be {@link CreativeTabs.AssortmentsIG}.
     * If {@link #withItemProperties(Item.Properties)} was used, the tab that was set last will be the final tab.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method to be true.
     * </p></strong>
     *
     * @param tab The {@link CreativeModeTab} you want your block in
     */
    public BlockBuilder<B, I> withCreativeTab(@Nullable CreativeModeTab tab) {
        this.itemProperties.tab(tab);
        return this;
    }

    /**
     * Adds a burn time to this block's item so that it can be used as fuel in furnaces.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method to be true.
     * </p></strong>
     *
     * @param burnTime The amount in ticks that this item will burn for.
     */
    public BlockBuilder<B, I> withBurnTime(int burnTime) {
        this.burnTime = burnTime;
        return this;
    }

    /**
     * Makes the block's item stay in the crafting grid when crafted with.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method to be true.
     * </p></strong>
     */
    public BlockBuilder<B, I> withContainerItem() {
        this.containerType = 1;
        return this;
    }

    /**
     * Adds the functionality of {@link #withContainerItem()} but the block's item will be damaged when crafted with by 1 durability point.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method to be true.
     * </p></strong>
     */
    public BlockBuilder<B, I> withDamageableContainerItem() {
        this.containerType = 2;
        return this;
    }

    /**
     * The properties will be passed on to the block item create.
     * <p><strong>
     * Requires {@link #withGeneratedBlockItem(boolean)} method to be true.
     * </p></strong>
     */
    public BlockBuilder<B, I> withItemProperties(Item.Properties properties) {
        this.itemProperties = properties;
        return this;
    }
}
