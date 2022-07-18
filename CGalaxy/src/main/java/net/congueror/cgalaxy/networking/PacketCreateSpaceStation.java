package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.registry.SpaceStationBlueprint;
import net.congueror.cgalaxy.init.CGItemInit;
import net.congueror.cgalaxy.util.saved_data.SpaceStationCoreObject;
import net.congueror.clib.networking.IPacket;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class PacketCreateSpaceStation implements IPacket {
    private final ResourceLocation level;
    private final String blueprint;
    private final List<String> optionals;

    public PacketCreateSpaceStation(ResourceLocation level, String blueprint, List<String> optionals) {
        this.level = level;
        this.blueprint = blueprint;
        this.optionals = optionals;
    }

    public PacketCreateSpaceStation(FriendlyByteBuf buf) {
        this.level = buf.readResourceLocation();
        this.blueprint = buf.readUtf();
        this.optionals = buf.readList(FriendlyByteBuf::readUtf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.level);
        buf.writeUtf(this.blueprint);
        buf.writeCollection(this.optionals, FriendlyByteBuf::writeUtf);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            ItemStack stack = RenderingHelper.getFirstItemInInventory(player.getInventory(), CGItemInit.SPACE_STATION.get());
            stack.shrink(1);

            ResourceKey<Level> dim = ResourceKey.create(Registry.DIMENSION_REGISTRY, level);
            ServerLevel level = player.getServer().getLevel(dim);
            SpaceStationBlueprint blueprint = CGalaxy.REGISTRY.get().getValue(new ResourceLocation(this.blueprint));
            SpaceStationCoreObject obj = SpaceStationCoreObject.generateObject(player.getUUID(), player.getOnPos(), level, blueprint, this.optionals);
            CGNetwork.sendToServer(new PacketTeleport(this.level, obj.getLaunchPadPos(), true));
        });
        return true;
    }
}
