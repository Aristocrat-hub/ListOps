package com.sharish.listops;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.stream.Collectors;

@EventBusSubscriber(modid = ListOpsMod.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ListOpsCommands {
    public static void register(IEventBus bus) {
        bus.register(Commands.class);
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent evt) {
        CommandDispatcher<CommandSourceStack> d = evt.getDispatcher();
        d.register(
            Commands.literal("listops")
                .requires(src -> src.hasPermission(0))
                .executes(ctx -> {
                    var src = ctx.getSource();
                    String rawNames = src.getServer().getPlayerList()
                        .getPlayers().stream()
                        .filter(p -> src.getServer().getPlayerList().isOp(p.getGameProfile()))
                        .map(ServerPlayer::getName)
                        .map(Component::getString)
                        .collect(Collectors.joining(", "));
                    String names = rawNames.isEmpty() ? "none" : rawNames;
                    src.sendSuccess(() -> Component.literal("Ops players: " + names), false);
                    return 1;
                })
        );
    }
}