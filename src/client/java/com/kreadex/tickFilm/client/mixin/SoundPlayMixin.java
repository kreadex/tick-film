package com.kreadex.tickFilm.client.mixin;

import com.kreadex.tickFilm.client.sound.TickSoundController;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class SoundPlayMixin {

    @Inject(method = "play", at = @At("HEAD"))
    private void tickFilm$instantPitch(SoundInstance sound, CallbackInfo ci) {

        if (sound == null) return;
        if (sound.getSound() == null) return;

        float multiplier = TickSoundController.getPitchMultiplier();

        TickSoundController.capture(sound);

        float base = sound.getPitch();
        float pitch = base * multiplier;
    }
}