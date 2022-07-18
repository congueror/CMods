package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.blocks.space_station_creator.SpaceStationCreatorContainer;
import net.congueror.clib.networking.IPacket;
import net.congueror.clib.util.PairList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class PacketUpdateSSCreator implements IPacket {
    private final int windowId;
    private final String blueprint;
    private final PairList.Unique<Item, Integer> stored;
    private final PairList.Default<Item, Integer> optional;

    public PacketUpdateSSCreator(int windowId, String blueprint, PairList.Unique<Item, Integer> stored, PairList.Default<Item, Integer> optional) {
        this.windowId = windowId;
        this.blueprint = blueprint;
        this.stored = stored;
        this.optional = optional;
    }

    public PacketUpdateSSCreator(FriendlyByteBuf buf) {
        this.windowId = buf.readInt();
        this.blueprint = buf.readUtf();
        this.stored = PairList.Unique.read(buf, b -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(b.readUtf())), FriendlyByteBuf::readInt);
        this.optional = PairList.Default.read(buf, b -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(b.readUtf())), FriendlyByteBuf::readInt);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(windowId);
        buf.writeUtf(blueprint);
        stored.write(buf, (b, itemLike) -> b.writeUtf(itemLike.asItem().getRegistryName().toString()), FriendlyByteBuf::writeInt);
        optional.write(buf, (b, itemLike) -> b.writeUtf(itemLike.asItem().getRegistryName().toString()), FriendlyByteBuf::writeInt);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            ctx.get().enqueueWork(() -> {
                Player player = mc.player;
                if (windowId != -1 && player != null) {
                    if (windowId == player.containerMenu.containerId) {
                        if (player.containerMenu instanceof SpaceStationCreatorContainer) {
                            ((SpaceStationCreatorContainer) player.containerMenu).updateData(blueprint, stored, optional);
                        }
                    }
                }
            });
        }
        return true;
    }
}
