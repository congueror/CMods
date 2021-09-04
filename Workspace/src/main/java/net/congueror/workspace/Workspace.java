package net.congueror.workspace;

import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("workspace")
public class Workspace {

    public Workspace() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
