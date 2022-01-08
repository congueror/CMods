package net.congueror.cgalaxy.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public class OpenGalaxyMapCommand implements Command<CommandSourceStack> {

    private static final OpenGalaxyMapCommand CMD = new OpenGalaxyMapCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("open_galaxy_map")
                .requires(commandSource -> commandSource.hasPermission(4))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        NetworkHooks.openGui(player, new MenuProvider() {
            @Nonnull
            @Override
            public Component getDisplayName() {
                return new TranslatableComponent("gui.cgalaxy.galaxy_map");
            }

            @Nonnull
            @Override
            public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
                return new GalaxyMapContainer(pContainerId, player, pInventory, true, null);
            }
        });
        return 1;
    }
}
