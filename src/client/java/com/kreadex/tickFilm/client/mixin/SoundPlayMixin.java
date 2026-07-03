package com.kreadex.tickFilm.client.mixin;

import com.kreadex.tickFilm.client.sound.TickSoundController;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundSystem.class)
public class SoundPlayMixin {

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)Lnet/minecraft/client/sound/SoundSystem$PlayResult;", at = @At("HEAD"))
    private void tickFilm$captureSound(SoundInstance sound, CallbackInfoReturnable<SoundSystem.PlayResult> cir) {
        if (sound != null && sound.getSound() != null) {
            TickSoundController.capture(sound);
        }
    }

    @Inject(method = "getAdjustedPitch", at = @At("RETURN"), cancellable = true)
    private void tickFilm$modifyInitialPitch(SoundInstance sound, CallbackInfoReturnable<Float> cir) {
        SoundCategory category = sound.getCategory();

        if (category == SoundCategory.MUSIC
                || category == SoundCategory.RECORDS
                || category == SoundCategory.VOICE
                || category == SoundCategory.MASTER) {
            return;
        }

        float originalPitch = cir.getReturnValue();
        float multiplier = TickSoundController.getPitchMultiplier();

        float finalPitch = originalPitch * multiplier;
        cir.setReturnValue(MathHelper.clamp(finalPitch, 0.05f, 2.0f));
    }
}