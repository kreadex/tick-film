package com.kreadex.tickFilm.client.sound;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.math.MathHelper;

import java.util.Map;
import java.util.WeakHashMap;

public class TickSoundController {

    private static long lastUpdateMs = 0;

    private static double currentPitch = 1.0;
    private static double targetPitch = 1.0;

    private static final Map<SoundInstance, Float> BASE_PITCH = new WeakHashMap<>();

    public static float getBasePitch(SoundInstance sound) {
        return BASE_PITCH.getOrDefault(sound, sound.getPitch());
    }

    public static void capture(SoundInstance sound) {
        if (sound == null || sound.getSound() == null) return;
        BASE_PITCH.put(sound, sound.getPitch());
    }

    public static void updateServerTick() {
        long now = System.currentTimeMillis();

        if (lastUpdateMs != 0) {
            long delta = now - lastUpdateMs;

            if (delta > 0) {
                if (delta >= 40 && delta <= 60) {
                    delta = 50;
                }

                if (delta > 2000) {
                    delta = 2000;
                }

                double estimatedTps = Math.min(20.0, 1000.0 / delta);

                targetPitch = Math.max(0.05, estimatedTps / 20.0);
            }
        }

        lastUpdateMs = now;
    }

    public static void tick() {
        currentPitch = MathHelper.lerp(0.1, currentPitch, targetPitch);

        if (Math.abs(currentPitch - 1.0) < 0.005) {
            currentPitch = 1.0;
        }
    }

    public static float getPitchMultiplier() {
        return (float) currentPitch;
    }
}