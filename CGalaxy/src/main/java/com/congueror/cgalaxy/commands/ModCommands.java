package com.congueror.cgalaxy.commands;

import com.congueror.cgalaxy.CGalaxy;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> cmdOpenGalaxyMap = dispatcher.register(
                Commands.literal(CGalaxy.MODID)
                    .then(OpenGalaxyMapCommand.register(dispatcher))
        );
        dispatcher.register(Commands.literal("cgalaxy").redirect(cmdOpenGalaxyMap));
    }
}
