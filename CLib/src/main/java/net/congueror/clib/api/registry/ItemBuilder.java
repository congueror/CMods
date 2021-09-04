package net.congueror.clib.api.registry;

import net.congueror.clib.api.data.ItemModelDataGenerator;
import net.congueror.clib.api.objects.items.ICLibItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A class that can be used for registering items to the game. This builder class provides many methods that can be used to ease the burden of registering items.
 */
@SuppressWarnings("unused")
public class ItemBuilder {
    private final ResourceLocation name;
    private final Item item;

    /**
     * All the item tags added via the {@link #withNewItemTag(String)} and {@link BlockBuilder#withNewItemTag(String)} methods. The string is simply the full name of the tag, e.g. "forge:ingots/steel".
     */
    public static final Map<String, Tag.Named<Item>> ITEM_TAGS = new HashMap<>();

    /**
     * A list of all the built ItemBuilder objects. This is used to iterate through all the objects.
     */
    public static final List<ItemBuilder> OBJECTS = new ArrayList<>();

    public final Map<Tag.Named<Item>, Tag.Named<Item>> itemTagsGen = new HashMap<>();
    public final Map<String, Tag.Named<Item>> itemTags = new HashMap<>();
    public BiConsumer<ItemModelDataGenerator, Item> itemModel;
    public String tranlsation;
    public final Map<String, String> locale = new HashMap<>();

    /**
     * Convenience constructor for those who don't want to make a new resource location object everytime.
     * @param name The name of your item, this string MUST include a modid and a path like so: "modid:my_item"
     * @param item See {@link #ItemBuilder(ResourceLocation, Item)}
     */
    public ItemBuilder(String name, Item item) {
        this(new ResourceLocation(name), item);
    }

    /**
     * This is the main constructor of the builder.
     * @param name The {@link ResourceLocation} of the item, which includes your mod's id and the path.
     * @param item A new instance of a class which extends {@link Item}.
     */
    public ItemBuilder(ResourceLocation name, Item item) {
        this.name = name;
        this.item = item;
    }

    /**
     * Getter for the item.
     * @return the item.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Builds/Registers this ItemBuilder object to the DeferredRegister passed in.
     * @param register The item {@link DeferredRegister} of your mod.
     * @return The registered {@link RegistryObject}.
     */
    public RegistryObject<Item> build(DeferredRegister<Item> register) {
        OBJECTS.add(this);
        return register.register(name.getPath(), () -> item);
    }

    /**
     * Adds an existing tag to this item, for example "minecraft:logs"
     * @param tags An array of tags to be added to the item.
     */
    @SafeVarargs
    public final ItemBuilder withExistingItemTags(Tag.Named<Item>... tags) {
        for (Tag.Named<Item> tag : tags) {
            itemTags.put(tag.getName().toString(), tag);
        }
        return this;
    }

    /**
     * A new tag to be added to the item. Accessible through the {@link #ITEM_TAGS} field. For already defined tags use {@link #withExistingItemTags(Tag.Named[])}.
     * Note that if the string has a Backslash("/") it will create and add 2 tags, e.g. Passing this string will add the following tags:
     * <p>
     * "forge:nuggets/my_nugget" -> "forge:nuggets", "forge:nuggets/my_nugget"
     * @param tagName The full name of the tag, e.g. "forge:ingots/my_ingot"
     */
    public ItemBuilder withNewItemTag(String tagName) {
        Tags.IOptionalNamedTag<Item> tag = ItemTags.createOptional(new ResourceLocation(tagName));
        itemTags.put(tagName, tag);
        ITEM_TAGS.put(tagName, tag);
        if (tagName.contains("/")) {
            String parent = tagName.substring(0, tagName.indexOf("/"));
            itemTagsGen.put(ItemTags.createOptional(new ResourceLocation(parent)), tag);
        }
        return this;
    }

    /**
     * Generates a new item model for this item. By default, a single item texture model is created.
     * Passing null will be result in no model at all.
     * @param ctx A {@link BiConsumer} of types {@link ItemModelDataGenerator} and {@link Item}.
     *            You can find many useful methods for generating item models inside the {@link ItemModelDataGenerator} class
     */
    public ItemBuilder withItemModel(BiConsumer<ItemModelDataGenerator, Item> ctx) {
        this.itemModel = ctx;
        return this;
    }

    /**
     * Adds a translation to this item in the en_us locale. If you wish to use a different locale use {@link #withTranslation(String, String)}
     * @param translation The name of the translated item, e.g. "My Ingot"
     */
    public ItemBuilder withTranslation(String translation) {
        this.locale.put("en_us", translation);
        return this;
    }

    /**
     * Adds a translation to this item in the given locale.
     * @param translation The name of the translated item, e.g. "My Ingot"
     * @param locale The localization this translation will be added to, e.g. "en_us"
     */
    public ItemBuilder withTranslation(String translation, String locale) {
        this.locale.put(locale, translation);
        return this;
    }

    //============================CLItem Exclusive============================

    /**
     * Adds a burn time to the item so it can be used in a furnace as fuel.
     * <p>
     * This method is exclusive for items that implement {@link ICLibItem} interface.
     * @param burnTime The amount in ticks that the fuel will burn for.
     */
    public ItemBuilder withBurnTime(int burnTime) {
        if (item instanceof ICLibItem clitem) {
            clitem.setBurnTime(burnTime);
        }
        return this;
    }

    /**
     * Makes the item stay in the crafting grid when crafted with.
     * <p><strong>
     * This method is exclusive for items that implement {@link ICLibItem} interface.</strong>
     */
    public ItemBuilder withContainerItem() {
        if (item instanceof ICLibItem clItem) {
            clItem.setContainerType(1);
        }
        return this;
    }

    /**
     * Adds the functionality of {@link #withContainerItem()} but the item will be damaged when crafted with by 1 durability point.
     * <p><strong>
     * This method is exclusive for items that implement {@link ICLibItem} interface.</strong>
     */
    public ItemBuilder withDamageableContainerItem() {
        if (item instanceof ICLibItem clItem) {
            clItem.setContainerType(2);
        }
        return this;
    }
}