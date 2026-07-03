package com.kreadex.tickFilm.animate;

import net.minecraft.server.MinecraftServer;

public class TickAnimator {

    public static TickAnimation current;

    public static void start(TickAnimation anim, MinecraftServer server) {

        server.getTickManager().setFrozen(false);

        anim.startTimeMs = System.currentTimeMillis();

        current = anim;
    }

    public static void stop() {
        current = null;
    }

    public static boolean isActive() {
        return current != null;
    }
}