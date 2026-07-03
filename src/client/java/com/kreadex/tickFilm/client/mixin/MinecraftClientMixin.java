package com.kreadex.tickFilm.client.mixin;

import com.kreadex.tickFilm.client.sound.TickSoundController;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void tickFilm$updatePitch(
            CallbackInfo ci
    ) {
        TickSoundController.tick();
    }
}
