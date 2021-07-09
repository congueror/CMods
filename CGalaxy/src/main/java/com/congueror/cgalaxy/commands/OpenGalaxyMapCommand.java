package com.congueror.cgalaxy.commands;

import com.congueror.cgalaxy.network.Networking;
import com.congueror.cgalaxy.network.PacketOpenGalaxyMap;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class OpenGalaxyMapCommand implements Command<CommandSource> {

    private static final OpenGalaxyMapCommand CMD = new OpenGalaxyMapCommand();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("open_galaxy_map")
                .requires(commandSource -> commandSource.hasPermissionLevel(4))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        Networking.sendToClient(new PacketOpenGalaxyMap(), player);
        return 1;
    }
}
