package com.kreadex.tickFilm.argument;

import com.kreadex.tickFilm.data.EasingMode;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Arrays;

public class EasingModeArgument {

    public static StringArgumentType mode() {
        return StringArgumentType.word();
    }

    public static EasingMode get(CommandContext<ServerCommandSource> ctx, String name) {
        return EasingMode.valueOf(
                StringArgumentType.getString(ctx, name).toUpperCase()
        );
    }

    public static final SuggestionProvider<ServerCommandSource> SUGGESTIONS = (ctx, builder) -> {
        return net.minecraft.command.CommandSource.suggestMatching(
                Arrays.asList("in", "out", "in_out"),
                builder
        );
    };
}