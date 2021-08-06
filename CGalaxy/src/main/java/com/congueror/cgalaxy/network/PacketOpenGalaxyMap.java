package com.congueror.cgalaxy.network;

import com.congueror.cgalaxy.gui.galaxy_map.GalaxyMapGuiOLD;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOpenGalaxyMap {

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(GalaxyMapGuiOLD::open);
        return true;
    }
}
