package com.kreadex.tickFilm.animate;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class TickEngine {

    public static void init() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            TickAnimation anim = TickAnimator.current;
            if (anim == null) {
                return;
            }

            long now = System.currentTimeMillis();

            long elapsedMs =
                    now - anim.startTimeMs;

            double t =
                    elapsedMs /
                            (double) anim.durationMs;

            t = Math.clamp(t,
                    0.0, 1.0);

            double eased =
                    Easing.apply(
                            t,
                            anim.easingMode,
                            anim.easingType
                    );

            float tickRate = (float)(
                    anim.start +
                            (anim.target - anim.start) * eased
            );

            server.getTickManager()
                    .setTickRate(tickRate);

            if (t >= 1.0) {

                server.getTickManager()
                        .setTickRate((float) anim.target);

                if (anim.target <= 0.0) {
                    server.getTickManager()
                            .setFrozen(true);
                }

                TickAnimator.stop();
            }
        });
    }
}