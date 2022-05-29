package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.HarvestLevels;
import net.congueror.clib.util.registry.*;
import net.congueror.clib.util.registry.material_builders.*;

public class CLMaterialInit {//TODO: Enum system?

    private static final ResourceBuilder.ResourceDeferredRegister REGISTER = new ResourceBuilder.ResourceDeferredRegister(CLib.MODID, CLBlockInit.BLOCKS, CLItemInit.ITEMS);

    public static final BasicMetalObject TIN = REGISTER.createBasicMetal("tin",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.STONE, 0).build();

    public static final AlloyMetalObject STEEL = REGISTER.createAlloyMetal("steel",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.IRON).build();

    public static final BasicMetalObject ALUMINUM = REGISTER.createBasicMetal("aluminum",
            CreativeTabs.ResourcesIG.instance, 5.0f, HarvestLevels.IRON, 0).build();

    public static final BasicMetalObject LEAD = REGISTER.createBasicMetal("lead",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build();

    public static final SingleGemObject RUBY = REGISTER.createSingleGem("ruby",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 7)
            .withOreAffix("Nether")
            .build();

    public static final BasicMetalObject SILVER = REGISTER.createBasicMetal("silver",
            CreativeTabs.ResourcesIG.instance, 5.0f, HarvestLevels.DIAMOND, 0).build();

    public static final AlloyMetalObject LUMIUM = REGISTER.createAlloyMetal("lumium",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON).build();

    public static final BasicMetalObject NICKEL = REGISTER.createBasicMetal("nickel",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build();

    public static final AlloyMetalObject INVAR = REGISTER.createAlloyMetal("invar",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON).build();

    public static final AlloyMetalObject ELECTRUM = REGISTER.createAlloyMetal("electrum",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON).build();

    public static final BasicMetalObject PLATINUM = REGISTER.createBasicMetal("platinum",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0).build();

    public static final AlloyMetalObject SIGNALUM = REGISTER.createAlloyMetal("signalum",
            CreativeTabs.ResourcesIG.instance, 3.0f, HarvestLevels.IRON).build();

    public static final BasicMetalObject TUNGSTEN = REGISTER.createBasicMetal("tungsten",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0).build();

    public static final AlloyMetalObject BRONZE = REGISTER.createAlloyMetal("bronze",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON).build();

    public static final GeodeGemObject SAPPHIRE = REGISTER.createGeodeGem("sapphire",
            CreativeTabs.ResourcesIG.instance).build();

    public static final SingleGemObject OPAL = REGISTER.createSingleGem("opal",
            CreativeTabs.ResourcesIG.instance, 7.0f, HarvestLevels.DIAMOND, 17)
            .withOreAffix("End").build();

    public static final DebrisMetalObject TITANIUM = REGISTER.createDebrisMetal("titanium", "Recens Debris",
            CreativeTabs.ResourcesIG.instance, 9.0f, HarvestLevels.DIAMOND, 0).build();

    public static final RadioactiveMetalObject URANIUM = REGISTER.createRadioactiveMetal("uranium",
            CreativeTabs.ResourcesIG.instance, HarvestLevels.DIAMOND, 0).build();

    public static final SingleMetalObject COBALT = REGISTER.createSingleMetal("cobalt",
            CreativeTabs.ResourcesIG.instance, 6.0f, HarvestLevels.DIAMOND, 0)
            .withOreAffix("Nether").build();

    public static final BasicMetalObject ZINC = REGISTER.createBasicMetal("zinc",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build();

    public static final AlloyMetalObject BRASS = REGISTER.createAlloyMetal("brass",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON).build();

    public static final BasicMetalObject CHROMIUM = REGISTER.createBasicMetal("chromium",
            CreativeTabs.ResourcesIG.instance, 4.0f, HarvestLevels.IRON, 0).build();

    public static final RadioactiveMetalObject THORIUM = REGISTER.createRadioactiveMetal("thorium",
            CreativeTabs.ResourcesIG.instance, HarvestLevels.DIAMOND, 0).build();



    public static void init() {}
}
