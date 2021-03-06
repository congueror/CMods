package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGSoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CGalaxy.MODID);

    public static final Lazy<SoundEvent> OXYGEN_MASK_EQUIP_LAZY = Lazy.of(() -> new SoundEvent(new ResourceLocation(CGalaxy.MODID, "oxygen_mask_equip")));
    public static final RegistryObject<SoundEvent> OXYGEN_MASK_EQUIP = SOUNDS.register("oxygen_mask_equip", OXYGEN_MASK_EQUIP_LAZY);
}
