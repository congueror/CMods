package net.congueror.cgalaxy;

import net.congueror.cgalaxy.init.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("cgalaxy")
public class CGalaxy {

    public static final String MODID = "cgalaxy";
    public static final Logger LOGGER = LogManager.getLogger();
    public static CGalaxy instance;

    public CGalaxy() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CGItemInit.ITEMS.register(modEventBus);
        CGBlockInit.BLOCKS.register(modEventBus);
        CGBlockEntityInit.BLOCK_ENTITY_TYPES.register(modEventBus);
        CGContainerInit.MENU_TYPES.register(modEventBus);
        CGRecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
        CGFluidInit.FLUIDS.register(modEventBus);
        CGEntityTypeInit.ENTITY_TYPES.register(modEventBus);
        CGalaxy.instance = this;

        MinecraftForge.EVENT_BUS.register(this);
    }
}
