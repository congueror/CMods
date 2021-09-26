package net.congueror.cgalaxy.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CGCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> cmdOpenGalaxyMap = dispatcher.register(
                Commands.literal(CGalaxy.MODID)
                        .then(OpenGalaxyMapCommand.register(dispatcher))
        );
        dispatcher.register(Commands.literal("cgalaxy").redirect(cmdOpenGalaxyMap));
    }
}
