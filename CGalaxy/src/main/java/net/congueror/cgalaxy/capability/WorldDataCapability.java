package net.congueror.cgalaxy.capability;

import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.INBTSerializable;

public class WorldDataCapability implements INBTSerializable<CompoundTag> {//TODO Make AFFECTED_BLOCKS save.
    public static Capability<WorldDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        for (int i = 0; i < RoomPressurizerBlockEntity.AFFECTED_BLOCKS.values().size(); i++) {

        }

        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag list = nbt.getList("cgalaxy:blockPositions", ListTag.TAG_LIST);
    }
}
