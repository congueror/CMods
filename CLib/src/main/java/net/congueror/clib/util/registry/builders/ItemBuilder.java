package net.congueror.clib.util.registry.builders;

import net.congueror.clib.util.ListMap;
import net.congueror.clib.util.registry.data.ItemModelDataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A class that can be used for registering items to the game. This builder class provides many methods that can be used to ease the burden of registering items.
 * <pre>
 *  public static final RegistryObject<Item> TEST = new ItemBuilder("test",
 *             new CLItem(new Item.Properties()
 *                  .tab(ModCreativeModeTabs.ResourcesIG.instance)))
 *             .withTranslation("My Test")
 *             .withItemTags(Tags.Items.INGOTS)
 *             .build(ITEMS);
 * </pre>
 */
@SuppressWarnings("unused")
public class ItemBuilder<I extends Item> implements Builder<I> {
    public final RegistryObject<I> regObject;

    public static final ListMap<String, ItemBuilder<?>> OBJECTS = new ListMap<>();

    public final Map<TagKey<Item>, TagKey<Item>> itemTagsGen = new HashMap<>();
    public final Map<String, TagKey<Item>> itemTags = new HashMap<>();
    public BiConsumer<ItemModelDataProvider, Item> itemModel = (itemModelDataProvider, item1) -> itemModelDataProvider.modTexture(item1, "item/" + item1);
    public final Map<String, String> locale = new HashMap<>();
    public final List<BiConsumer<Consumer<FinishedRecipe>, Item>> recipes = new ArrayList<>();

    public record ItemDeferredRegister(DeferredRegister<Item> register) {

        public <I extends Item> ItemBuilder<I> create(String name, Supplier<I> item) {
            return new ItemBuilder<>(register.register(name, item));
        }
    }

    public ItemBuilder(RegistryObject<I> regObject) {
        this.regObject = regObject;
    }

    private static Stream<ItemBuilder<?>> stream() {
        List<ItemBuilder<?>> list = new ArrayList<>();
        OBJECTS.values().forEach(list::addAll);
        return list.stream();
    }

    public RegistryObject<I> build() {
        String modid = this.regObject.getId().getNamespace();
        OBJECTS.addEntry(modid, this);
        return regObject;
    }

    /**
     * Adds an existing tag to this item, for example "minecraft:logs"
     *
     * @param tags An array of tags to be added to the item.
     */
    @SafeVarargs
    public final ItemBuilder<I> withItemTags(TagKey<Item>... tags) {
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
     * A new tag to be added to the item. For already defined tags use {@link #withItemTags(TagKey[])}.
     * Note that if the string has a Backslash("/") it will create and add 2 tags, e.g. Passing this string will add the following tags:
     * <p>
     * "forge:nuggets/my_nugget" -> "forge:nuggets", "forge:nuggets/my_nugget"
     *
     * @param tagName The full name of the tag, e.g. "forge:ingots/my_ingot"
     */
    public ItemBuilder<I> withItemTag(String tagName) {
        TagKey<Item> tag = ItemTags.create(new ResourceLocation(tagName));
        itemTags.put(tagName, tag);
        if (tagName.contains("/")) {
            String parent = tagName.substring(0, tagName.indexOf("/"));
            itemTagsGen.put(ItemTags.create(new ResourceLocation(parent)), tag);
        }
        return this;
    }

    /**
     * Generates a new item model for this item. By default, a single item modTexture model is created.
     * Passing null will result in no model at all.
     *
     * @param ctx A {@link BiConsumer} of types {@link ItemModelDataProvider} and {@link Item}.
     *            You can find many useful methods for generating item models inside the {@link ItemModelDataProvider} class
     */
    public ItemBuilder<I> withItemModel(BiConsumer<ItemModelDataProvider, Item> ctx) {
        this.itemModel = ctx;
        return this;
    }

    /**
     * Adds a translation to this item in the en_us locale. If you wish to use a different locale use {@link #withTranslation(String, String)}
     *
     * @param translation The name of the translated item, e.g. "My Ingot"
     */
    public ItemBuilder<I> withTranslation(String translation) {
        this.locale.put("en_us", translation);
        return this;
    }

    /**
     * Adds a translation to this item in the given locale.
     *
     * @param translation The name of the translated item, e.g. "My Ingot"
     * @param locale      The localization this translation will be added to, e.g. "en_us"
     */
    public ItemBuilder<I> withTranslation(String translation, String locale) {
        this.locale.put(locale, translation);
        return this;
    }

    public ItemBuilder<I> withRecipe(BiConsumer<Consumer<FinishedRecipe>, Item> consumer) {
        this.recipes.add(consumer);
        return this;
    }
}