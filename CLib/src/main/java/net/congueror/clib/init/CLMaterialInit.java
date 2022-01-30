package net.congueror.clib.init;

import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.HarvestLevels;
import net.congueror.clib.util.registry.*;

public class CLMaterialInit {

    public static final BasicMetalRegistryObject TIN = new BasicMetalRegistryObject.BasicMetalBuilder("tin",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.STONE, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final AlloyMetalRegistryObject STEEL = new AlloyMetalRegistryObject.AlloyMetalBuilder("steel",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.IRON).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final BasicMetalRegistryObject ALUMINUM = new BasicMetalRegistryObject.BasicMetalBuilder("aluminum",
            CreativeTabs.ResourcesIG.instance, 5.0f, HarvestLevels.IRON, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final BasicMetalRegistryObject LEAD = new BasicMetalRegistryObject.BasicMetalBuilder("lead",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final SingleGemRegistryObject RUBY = new SingleGemRegistryObject.SingleGemBuilder("ruby",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 7)
            .withOreAffix("Nether")
            .build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final BasicMetalRegistryObject SILVER = new BasicMetalRegistryObject.BasicMetalBuilder("silver",
            CreativeTabs.ResourcesIG.instance, 5.0f, HarvestLevels.DIAMOND, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final AlloyMetalRegistryObject LUMIUM = new AlloyMetalRegistryObject.AlloyMetalBuilder("lumium",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final BasicMetalRegistryObject NICKEL = new BasicMetalRegistryObject.BasicMetalBuilder("nickel",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final AlloyMetalRegistryObject INVAR = new AlloyMetalRegistryObject.AlloyMetalBuilder("invar",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final AlloyMetalRegistryObject ELECTRUM = new AlloyMetalRegistryObject.AlloyMetalBuilder("electrum",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final BasicMetalRegistryObject PLATINUM = new BasicMetalRegistryObject.BasicMetalBuilder("platinum",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final AlloyMetalRegistryObject ENDERIUM = new AlloyMetalRegistryObject.AlloyMetalBuilder("enderium",
            CreativeTabs.ResourcesIG.instance, 7.0f, HarvestLevels.DIAMOND).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final AlloyMetalRegistryObject SIGNALUM = new AlloyMetalRegistryObject.AlloyMetalBuilder("signalum",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final BasicMetalRegistryObject TUNGSTEN = new BasicMetalRegistryObject.BasicMetalBuilder("tungsten",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final AlloyMetalRegistryObject BRONZE = new AlloyMetalRegistryObject.AlloyMetalBuilder("bronze",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final GeodeGemRegistryObject SAPPHIRE = new GeodeGemRegistryObject.GeodeGemBuilder("sapphire", CreativeTabs.ResourcesIG.instance)
            .build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final SingleGemRegistryObject OPAL = new SingleGemRegistryObject.SingleGemBuilder("opal",
            CreativeTabs.ResourcesIG.instance, 7.0f, HarvestLevels.DIAMOND, 17)
            .withOreAffix("End").build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final DebrisMetalRegistryObject TITANIUM = new DebrisMetalRegistryObject.DebrisMetalBuilder("titanium", "Recens Debris",
            CreativeTabs.ResourcesIG.instance, 9.0f, HarvestLevels.DIAMOND, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final RadioactiveMetalRegistryObject URANIUM = new RadioactiveMetalRegistryObject.RadioactiveMetalBuilder("uranium",
            CreativeTabs.ResourcesIG.instance, HarvestLevels.DIAMOND, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final SingleMetalRegistryObject COBALT = new SingleMetalRegistryObject.SingleMetalBuilder("cobalt",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0)
            .withOreAffix("Nether").build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final BasicMetalRegistryObject ZINC = new BasicMetalRegistryObject.BasicMetalBuilder("zinc",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final AlloyMetalRegistryObject BRASS = new AlloyMetalRegistryObject.AlloyMetalBuilder("brass",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final BasicMetalRegistryObject CHROMIUM = new BasicMetalRegistryObject.BasicMetalBuilder("chromium",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);
    public static final RadioactiveMetalRegistryObject THORIUM = new RadioactiveMetalRegistryObject.RadioactiveMetalBuilder("thorium",
            CreativeTabs.ResourcesIG.instance, HarvestLevels.DIAMOND, 0).build(CLItemInit.ITEMS, CLBlockInit.BLOCKS);



    public static void init() {}
}
