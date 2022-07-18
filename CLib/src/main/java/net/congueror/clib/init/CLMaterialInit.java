package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.HarvestLevels;
import net.congueror.clib.util.registry.builders.ResourceBuilder;
import net.congueror.clib.util.registry.builders.material_builders.*;
import net.congueror.clib.util.registry.data.RecipeDataProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import static net.congueror.clib.util.registry.data.RecipeDataProvider.getTag;

public class CLMaterialInit {

    private static final ResourceBuilder.ResourceDeferredRegister REGISTER = new ResourceBuilder.ResourceDeferredRegister(CLib.MODID, CLBlockInit.BLOCKS, CLItemInit.ITEMS);

    public static final BasicMetalObject TIN = REGISTER.createBasicMetal("tin",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.STONE, 0)
            .withIngotTranslation("\u9521\u952d", "zh_cn")
            .withNuggetTranslation("\u9521\u7c92", "zh_cn")
            .withOreTranslation("\u9521\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u9521\u5757", "zh_cn")
            .build();

    public static final AlloyMetalObject STEEL = REGISTER.createAlloyMetal("steel",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.IRON)
            .withBlendRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.
                    shapelessRecipe(finishedRecipeConsumer, item, 2, Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON, ItemTags.COALS))
            .withIngotTranslation("\u94a2\u952d", "zh_cn")
            .withNuggetTranslation("\u94a2\u7c92", "zh_cn")
            .withBlendTranslation("\u94a2\u7c89", "zh_cn")
            .withBlockTranslation("\u94a2\u5757", "zh_cn")
            .build();

    public static final BasicMetalObject ALUMINUM = REGISTER.createBasicMetal("aluminum",
            CreativeTabs.ResourcesIG.instance, 5.0f, HarvestLevels.IRON, 0)
            .withIngotTranslation("\u94dd\u952d", "zh_cn")
            .withNuggetTranslation("\u94dd\u7c92", "zh_cn")
            .withOreTranslation("\u94dd\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u94dd\u5757", "zh_cn")
            .build();

    public static final BasicMetalObject LEAD = REGISTER.createBasicMetal("lead",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0)
            .withIngotTranslation("\u94c5\u952d", "zh_cn")
            .withNuggetTranslation("\u94c5\u7c92", "zh_cn")
            .withOreTranslation("\u94c5\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u94c5\u5757", "zh_cn")
            .build();

    public static final SingleGemObject RUBY = REGISTER.createSingleGem("ruby",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 7)
            .withOreAffix("Nether")
            .withGemTranslation("\u7ea2\u5b9d\u77f3", "zh_cn")
            .withOreTranslation("\u4e0b\u754c\u7ea2\u5b9d\u77f3\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u7ea2\u5b9d\u77f3\u5757", "zh_cn")
            .build();

    public static final BasicMetalObject SILVER = REGISTER.createBasicMetal("silver",
            CreativeTabs.ResourcesIG.instance, 5.0f, HarvestLevels.DIAMOND, 0)
            .withIngotTranslation("\u94f6\u952d", "zh_cn")
            .withNuggetTranslation("\u94f6\u7c92", "zh_cn")
            .withOreTranslation("\u94f6\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u94f6\u5757", "zh_cn")
            .build();

    public static final AlloyMetalObject LUMIUM = REGISTER.createAlloyMetal("lumium",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON)
            .withBlendRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.
                    shapelessRecipe(finishedRecipeConsumer, item, 2, getTag("forge:ingots/silver"), getTag("forge:ingots/silver"), getTag("forge:ingots/tin"), Tags.Items.DUSTS_GLOWSTONE))
            .withIngotTranslation("\u6d41\u660e\u952d", "zh_cn")
            .withNuggetTranslation("\u6d41\u660e\u7c92", "zh_cn")
            .withBlendTranslation("\u6d41\u660e\u7c89", "zh_cn")
            .withBlockTranslation("\u6d41\u660e\u5757", "zh_cn")
            .build();

    public static final BasicMetalObject NICKEL = REGISTER.createBasicMetal("nickel",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0)
            .withIngotTranslation("\u954d\u952d", "zh_cn")
            .withNuggetTranslation("\u954d\u7c92", "zh_cn")
            .withOreTranslation("\u954d\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u954d\u5757", "zh_cn")
            .build();

    public static final AlloyMetalObject INVAR = REGISTER.createAlloyMetal("invar",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON)
            .withBlendRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.
                    shapelessRecipe(finishedRecipeConsumer, item, 3, getTag("forge:ingots/nickel"), getTag("forge:ingots/nickel"), Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON))
            .withIngotTranslation("\u6bb7\u94a2\u952d", "zh_cn")
            .withNuggetTranslation("\u6bb7\u94a2\u7c92", "zh_cn")
            .withBlendTranslation("\u6bb7\u94a2\u7c89", "zh_cn")
            .withBlockTranslation("\u6bb7\u94a2\u5757", "zh_cn")
            .build();

    public static final AlloyMetalObject ELECTRUM = REGISTER.createAlloyMetal("electrum",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON)
            .withBlendRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.
                    shapelessRecipe(finishedRecipeConsumer, item, 2, getTag("forge:ingots/silver"), getTag("forge:ingots/silver"), Tags.Items.INGOTS_GOLD))
            .withIngotTranslation("\u7425\u73c0\u91d1\u952d", "zh_cn")
            .withNuggetTranslation("\u7425\u73c0\u91d1\u7c92", "zh_cn")
            .withBlendTranslation("\u7425\u73c0\u91d1\u7c89", "zh_cn")
            .withBlockTranslation("\u7425\u73c0\u91d1\u5757", "zh_cn")
            .build();

    public static final BasicMetalObject PLATINUM = REGISTER.createBasicMetal("platinum",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0)
            .withIngotTranslation("\u94c2\u952d", "zh_cn")
            .withNuggetTranslation("\u94c2\u7c92", "zh_cn")
            .withOreTranslation("\u94c2\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u94c2\u5757", "zh_cn")
            .build();

    public static final AlloyMetalObject SIGNALUM = REGISTER.createAlloyMetal("signalum",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON)
            .withBlendRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.
                    shapelessRecipe(finishedRecipeConsumer, item, 4, Tags.Items.INGOTS_COPPER, getTag("forge:ingots/tin"), getTag("forge:ingots/tin"), getTag("forge:ingots/tin"), Tags.Items.INGOTS_COPPER, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE))
            .withIngotTranslation("\u4fe1\u7d20\u952d", "zh_cn")
            .withNuggetTranslation("\u4fe1\u7d20\u7c92", "zh_cn")
            .withBlendTranslation("\u4fe1\u7d20\u7c89", "zh_cn")
            .withBlockTranslation("\u4fe1\u7d20\u5757", "zh_cn")
            .build();

    public static final BasicMetalObject TUNGSTEN = REGISTER.createBasicMetal("tungsten",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0)
            .withIngotTranslation("\u94a8\u952d", "zh_cn")
            .withNuggetTranslation("\u94a8\u7c92", "zh_cn")
            .withOreTranslation("\u94a8\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u94a8\u5757", "zh_cn")
            .build();

    public static final AlloyMetalObject BRONZE = REGISTER.createAlloyMetal("bronze",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON)
            .withBlendRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.
                    shapelessRecipe(finishedRecipeConsumer, item, 3, Tags.Items.INGOTS_COPPER, getTag("forge:ingots/tin"), getTag("forge:ingots/tin"), Tags.Items.INGOTS_COPPER))
            .withIngotTranslation("\u9752\u94dc\u952d", "zh_cn")
            .withNuggetTranslation("\u9752\u94dc\u7c92", "zh_cn")
            .withBlendTranslation("\u9752\u94dc\u7c89", "zh_cn")
            .withBlockTranslation("\u9752\u94dc\u5757", "zh_cn")
            .build();

    public static final GeodeGemObject SAPPHIRE = REGISTER.createGeodeGem("sapphire",
            CreativeTabs.ResourcesIG.instance)
            .withCrystalBlockTranslation("\u7eff\u5b9d\u77f3\u5757", "zh_cn")
            .build();

    public static final SingleGemObject OPAL = REGISTER.createSingleGem("opal",
            CreativeTabs.ResourcesIG.instance, 7.0f, HarvestLevels.DIAMOND, 17)
            .withOreAffix("End")
            .withGemTranslation("\u732b\u773c\u77f3", "zh_cn")
            .withOreTranslation("\u732b\u773c\u77f3\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u732b\u773c\u77f3\u5757", "zh_cn")
            .build();

    public static final DebrisMetalObject TITANIUM = REGISTER.createDebrisMetal("titanium", "Recens Debris",
            CreativeTabs.ResourcesIG.instance, 9.0f, HarvestLevels.DIAMOND, 0)
            .withIngotTranslation("\u949b\u952d", "zh_cn")
            .withNuggetTranslation("\u949b\u7c92", "zh_cn")
            .withScrapTranslation("\u949b\u788e\u7247", "zh_cn")
            .withDebrisTranslation("\u949b\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u949b\u5757", "zh_cn")
            .build();

    public static final RadioactiveMetalObject URANIUM = REGISTER.createRadioactiveMetal("uranium",
            CreativeTabs.ResourcesIG.instance, HarvestLevels.DIAMOND, 0)
            .withIngotTranslation("\u94c0\u952d", "zh_cn")
            .withNuggetTranslation("\u94c0\u7c92", "zh_cn")
            .withOreTranslation("\u94c0\u77ff\u77f3", "zh_cn")
            .build();

    public static final SingleMetalObject COBALT = REGISTER.createSingleMetal("cobalt",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0)
            .withOreAffix("Nether")
            .withIngotTranslation("\u94b4\u952d", "zh_cn")
            .withNuggetTranslation("\u94b4\u7c92", "zh_cn")
            .withOreTranslation("\u94b4\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u94b4\u5757", "zh_cn")
            .build();

    public static final BasicMetalObject ZINC = REGISTER.createBasicMetal("zinc",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0)
            .withIngotTranslation("\u950c\u952d", "zh_cn")
            .withNuggetTranslation("\u950c\u7c92", "zh_cn")
            .withOreTranslation("\u950c\u77ff\u77f3", "zh_cn")
            .withBlockTranslation("\u950c\u5757", "zh_cn")
            .build();

    public static final AlloyMetalObject BRASS = REGISTER.createAlloyMetal("brass",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON)
            .withBlendRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.
                    shapelessRecipe(finishedRecipeConsumer, item, 4, getTag("forge:ingots/zinc"), Tags.Items.INGOTS_COPPER, Tags.Items.INGOTS_COPPER, Tags.Items.INGOTS_COPPER, getTag("forge:ingots/zinc")))
            .build();

    public static final BasicMetalObject CHROMIUM = REGISTER.createBasicMetal("chromium",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build();

    public static final RadioactiveMetalObject THORIUM = REGISTER.createRadioactiveMetal("thorium",
            CreativeTabs.ResourcesIG.instance, HarvestLevels.DIAMOND, 0).build();



    public static void init() {}
}
