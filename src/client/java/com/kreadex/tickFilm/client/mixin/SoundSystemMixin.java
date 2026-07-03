package com.kreadex.tickFilm.client.mixin;

import com.kreadex.tickFilm.client.sound.TickSoundController;
import net.minecraft.client.sound.Channel;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin {

    @Shadow
    private Map<SoundInstance, Channel.SourceManager> sources;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickFilm$updatePitch(CallbackInfo ci) {

        float multiplier = TickSoundController.getPitchMultiplier();

        for (var entry : sources.entrySet()) {

            SoundInstance sound = entry.getKey();
            if (sound == null) continue;

            if (sound.getSound() == null) continue;

            SoundCategory category = sound.getCategory();

            if (category == SoundCategory.MUSIC
                    || category == SoundCategory.RECORDS
                    || category == SoundCategory.VOICE
                    || category == SoundCategory.MASTER) {
                return;
            }

            entry.getValue().run(source -> {

                float basePitch = TickSoundController.getBasePitch(sound);

                float finalPitch = basePitch * multiplier;

                source.setPitch(MathHelper.clamp(finalPitch, 0.05f, 2.0f));
            });
        }
    }
}

