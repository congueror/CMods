package net.congueror.clib.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket {
    void toBytes(FriendlyByteBuf buf);
    boolean handle(Supplier<NetworkEvent.Context> ctx);
}
