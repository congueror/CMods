package com.congueror.clib.compat.tconstruct;

import com.congueror.clib.CLib;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import slimeknights.tconstruct.library.modifiers.Modifier;

public class ModifierInit {

    public static final DeferredRegister<Modifier> MODIFIERS = DeferredRegister.create(Modifier.class, CLib.MODID);

    public static final RegistryObject<Modifier> MAGNETIC = MODIFIERS.register("magnetic", MagneticModifier::new);
}
