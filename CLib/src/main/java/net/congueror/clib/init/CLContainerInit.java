package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.blocks.solar_generator.SolarGeneratorBlockEntity;
import net.congueror.clib.blocks.solar_generator.SolarGeneratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CLContainerInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, CLib.MODID);

    public static final RegistryObject<MenuType<SolarGeneratorContainer>> SOLAR_GENERATOR = MENU_TYPES.register("solar_generator", () ->
            IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tile = inv.player.getCommandSenderWorld().getBlockEntity(pos);
                SolarGeneratorBlockEntity te = (SolarGeneratorBlockEntity) tile;
                return new SolarGeneratorContainer(windowId, inv.player, inv, te, new SimpleContainerData(SolarGeneratorBlockEntity.FIELDS_COUNT));
            }));
}
