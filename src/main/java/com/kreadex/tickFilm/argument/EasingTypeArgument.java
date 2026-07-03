package com.kreadex.tickFilm.argument;

import com.kreadex.tickFilm.data.EasingType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Arrays;

public class EasingTypeArgument {

    public static StringArgumentType type() {
        return StringArgumentType.word();
    }

    public static EasingType get(CommandContext<ServerCommandSource> ctx, String name) {
        return EasingType.valueOf(
                StringArgumentType.getString(ctx, name).toUpperCase()
        );
    }

    public static final SuggestionProvider<ServerCommandSource> SUGGESTIONS = (ctx, builder) -> {
        return net.minecraft.command.CommandSource.suggestMatching(
                Arrays.asList(
                        "sine",
                        "quad",
                        "cubic",
                        "quartic",
                        "quintic",
                        "expo",
                        "circular"
                ),
                builder
        );
    };
}