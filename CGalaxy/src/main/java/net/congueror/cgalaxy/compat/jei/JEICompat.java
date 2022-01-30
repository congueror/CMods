package net.congueror.cgalaxy.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorScreen;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerScreen;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static net.congueror.clib.compat.jei.JEICompat.getRecipes;

@JeiPlugin
public class JEICompat implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(CGalaxy.MODID, "plugin/main");
    public static final ResourceLocation FUEL_REFINING = new ResourceLocation(CGalaxy.MODID, "fuel_refining");
    public static final ResourceLocation OXYGEN_COMPRESSING = new ResourceLocation(CGalaxy.MODID, "gas_extracting");
    public static final ResourceLocation FUEL_LOADING = new ResourceLocation(CGalaxy.MODID, "fuel_loading");
    public static final ResourceLocation ROOM_PRESSURIZING = new ResourceLocation(CGalaxy.MODID, "room_pressurizing");

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FuelRefiningCategory(guiHelper));
        registration.addRecipeCategories(new GasExtractingCategory(guiHelper));
        registration.addRecipeCategories(new FuelLoadingCategory(guiHelper));
        registration.addRecipeCategories(new RoomPressurizingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(getRecipes(CGRecipeSerializerInit.Types.FUEL_REFINING), FUEL_REFINING);
        registration.addRecipes(getRecipes(CGRecipeSerializerInit.Types.GAS_EXTRACTING), OXYGEN_COMPRESSING);
        registration.addRecipes(getRecipes(CGRecipeSerializerInit.Types.FUEL_LOADING), FUEL_LOADING);
        registration.addRecipes(getRecipes(CGRecipeSerializerInit.Types.ROOM_PRESSURIZING), ROOM_PRESSURIZING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FuelRefineryScreen.class, 85, 35, 24, 23, FUEL_REFINING);
        registration.addRecipeClickArea(GasExtractorScreen.class, 77, 38, 24, 23, OXYGEN_COMPRESSING);
        registration.addRecipeClickArea(FuelLoaderScreen.class, 82, 30, 15, 29, FUEL_LOADING);
        registration.addRecipeClickArea(RoomPressurizerScreen.class, 92, 30, 15, 29, ROOM_PRESSURIZING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.FUEL_REFINERY.get()), FUEL_REFINING);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.GAS_EXTRACTOR.get()), OXYGEN_COMPRESSING);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.FUEL_LOADER.get()), FUEL_LOADING);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.ROOM_PRESSURIZER.get()), ROOM_PRESSURIZING);
    }
}
