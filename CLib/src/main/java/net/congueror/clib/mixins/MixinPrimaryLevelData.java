package net.congueror.clib.mixins;

import com.mojang.serialization.Lifecycle;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimaryLevelData.class)
public class MixinPrimaryLevelData {

    /**
     * Disables experimental settings screen.
     */
    @Inject(method = "worldGenSettingsLifecycle", at = @At("HEAD"), cancellable = true)
    private void worldGenSettingsLifecycleHead(CallbackInfoReturnable<Lifecycle> cir) {
        if (!FMLEnvironment.production) {
            cir.setReturnValue(Lifecycle.stable());
        }
    }
}
