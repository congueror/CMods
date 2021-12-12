package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderBlockEntity;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderContainer;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryBlockEntity;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryContainer;
import net.congueror.cgalaxy.blocks.oxygen_compressor.OxygenCompressorBlockEntity;
import net.congueror.cgalaxy.blocks.oxygen_compressor.OxygenCompressorContainer;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlockEntity;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerContainer;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.congueror.cgalaxy.gui.space_suit.SpaceSuitContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CGContainerInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, CGalaxy.MODID);

    public static final RegistryObject<MenuType<FuelLoaderContainer>> FUEL_LOADER = MENU_TYPES.register("fuel_loader", () ->
            IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tile = inv.player.getCommandSenderWorld().getBlockEntity(pos);
                FuelLoaderBlockEntity te = (FuelLoaderBlockEntity) tile;
                return new FuelLoaderContainer(windowId, inv.player, inv, te, new SimpleContainerData(FuelLoaderBlockEntity.FIELDS_COUNT));
            }));
    public static final RegistryObject<MenuType<FuelRefineryContainer>> FUEL_REFINERY = MENU_TYPES.register("fuel_refinery", () ->
            IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tile = inv.player.getCommandSenderWorld().getBlockEntity(pos);
                FuelRefineryBlockEntity te = (FuelRefineryBlockEntity) tile;
                return new FuelRefineryContainer(windowId, inv.player, inv, te, new SimpleContainerData(FuelRefineryBlockEntity.FIELDS_COUNT));
            }));
    public static final RegistryObject<MenuType<OxygenCompressorContainer>> OXYGEN_COMPRESSOR = MENU_TYPES.register("oxygen_compressor", () ->
            IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tile = inv.player.getCommandSenderWorld().getBlockEntity(pos);
                OxygenCompressorBlockEntity te = (OxygenCompressorBlockEntity) tile;
                return new OxygenCompressorContainer(windowId, inv.player, inv, te, new SimpleContainerData(OxygenCompressorBlockEntity.FIELDS_COUNT));
            }));
    public static final RegistryObject<MenuType<RoomPressurizerContainer>> ROOM_PRESSURIZER = MENU_TYPES.register("room_pressurizer", () ->
            IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tile = inv.player.getCommandSenderWorld().getBlockEntity(pos);
                RoomPressurizerBlockEntity te = (RoomPressurizerBlockEntity) tile;
                return new RoomPressurizerContainer(windowId, inv.player, inv, te, new SimpleContainerData(RoomPressurizerBlockEntity.FIELDS_COUNT));
            }));

    public static final RegistryObject<MenuType<GalaxyMapContainer>> GALAXY_MAP = MENU_TYPES.register("galaxy_map", () ->
            IForgeMenuType.create((windowId, inv, data) -> new GalaxyMapContainer(windowId, inv.player, false, null)));
    public static final RegistryObject<MenuType<SpaceSuitContainer>> SPACE_SUIT = MENU_TYPES.register("space_suit", () ->
            IForgeMenuType.create((windowId, inv, data) -> new SpaceSuitContainer(windowId, inv)));
}
