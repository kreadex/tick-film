package com.kreadex.tickFilm.animate;

import com.kreadex.tickFilm.data.EasingMode;
import com.kreadex.tickFilm.data.EasingType;

public class Easing {

    public static double apply(double t, EasingMode mode, EasingType type) {

        t = Math.clamp(t, 0.0, 1.0);

        return switch (mode) {

            case IN ->
                    easeIn(t, type);

            case OUT ->
                    easeOut(t, type);

            case IN_OUT -> {

                if (t < 0.5) {
                    yield easeIn(t * 2.0, type) * 0.5;
                } else {
                    yield 0.5 + easeOut((t - 0.5) * 2.0, type) * 0.5;
                }
            }
        };
    }

    private static double easeIn(double t, EasingType type) {

        return switch (type) {

            case SINE ->
                    1.0 - Math.cos(t * Math.PI / 2.0);

            case QUAD ->
                    t * t;

            case CUBIC ->
                    t * t * t;

            case QUARTIC ->
                    Math.pow(t, 4);

            case QUINTIC ->
                    Math.pow(t, 5);

            case EXPO ->
                    (t == 0.0)
                            ? 0.0
                            : Math.pow(2.0, 10.0 * (t - 1.0));

            case CIRCULAR ->
                    1.0 - Math.sqrt(1.0 - t * t);
        };
    }

    private static double easeOut(double t, EasingType type) {

        return switch (type) {

            case SINE ->
                    Math.sin(t * Math.PI / 2.0);

            case QUAD ->
                    1.0 - Math.pow(1.0 - t, 2);

            case CUBIC ->
                    1.0 - Math.pow(1.0 - t, 3);

            case QUARTIC ->
                    1.0 - Math.pow(1.0 - t, 4);

            case QUINTIC ->
                    1.0 - Math.pow(1.0 - t, 5);

            case EXPO ->
                    (t == 1.0)
                            ? 1.0
                            : 1.0 - Math.pow(2.0, -10.0 * t);

            case CIRCULAR ->
                    Math.sqrt(1.0 - Math.pow(t - 1.0, 2));
        };
    }
}