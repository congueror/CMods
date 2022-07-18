package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CLContainerInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, CLib.MODID);


}
