package com.kreadex.tickFilm.mixin;

import com.kreadex.tickFilm.animate.TickAnimation;
import com.kreadex.tickFilm.animate.TickAnimator;
import com.kreadex.tickFilm.argument.EasingModeArgument;
import com.kreadex.tickFilm.argument.EasingTypeArgument;
import com.kreadex.tickFilm.data.EasingMode;
import com.kreadex.tickFilm.data.EasingType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TickCommand;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TickCommand.class)
public class TickCommandMixin {

    @Inject(
            method = "register",
            at = @At("TAIL")
    )
    private static void tickfilm$addCommands(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CallbackInfo ci
    ) {
        dispatcher.register(
                CommandManager.literal("tick")

                        .then(CommandManager.literal("help")
                                .executes(ctx -> {
                                    ctx.getSource().sendFeedback(
                                            () -> Text.literal("""
                                                    TickFilm:
                                                    /tick animate <target> <time> <interp> <easingMode> <easingType>
                                                    """),
                                            false
                                    );
                                    return 1;
                                })
                        )

                        .then(CommandManager.literal("animate")
                                .requires(source -> source.hasPermissionLevel(2))

                                .then(CommandManager.argument("target",
                                                com.mojang.brigadier.arguments.IntegerArgumentType.integer())

                                        .then(CommandManager.argument("time",
                                                        com.mojang.brigadier.arguments.IntegerArgumentType.integer())

                                                        .then(CommandManager.argument("easingMode",
                                                                        StringArgumentType.word())
                                                                .suggests(EasingModeArgument.SUGGESTIONS)

                                                                .then(CommandManager.argument("easingType",
                                                                                StringArgumentType.word())
                                                                        .suggests(EasingTypeArgument.SUGGESTIONS)

                                                                        .executes(ctx -> {

                                                                            int target = com.mojang.brigadier.arguments.IntegerArgumentType.getInteger(ctx, "target");
                                                                            int time = com.mojang.brigadier.arguments.IntegerArgumentType.getInteger(ctx, "time");

                                                                            var server = ctx.getSource().getServer();

                                                                            TickAnimation anim = new TickAnimation(
                                                                                    server.getTickManager().getTickRate(),
                                                                                    target,
                                                                                    time * 1000L,

                                                                                    EasingMode.valueOf(
                                                                                            com.mojang.brigadier.arguments.StringArgumentType.getString(ctx, "easingMode").toUpperCase()
                                                                                    ),

                                                                                    EasingType.valueOf(
                                                                                            com.mojang.brigadier.arguments.StringArgumentType.getString(ctx, "easingType").toUpperCase()
                                                                                    )
                                                                            );

                                                                            TickAnimator.start(anim, server);

                                                                            ctx.getSource().sendFeedback(
                                                                                    () -> Text.literal("Animating tick → " + target),
                                                                                    false
                                                                            );

                                                                            return 1;
                                                                        })
                                                                )
                                                        )
                                                )
                                        )
                                )

        );
    }
}