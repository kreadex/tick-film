package com.kreadex.tickFilm.animate;

import com.kreadex.tickFilm.data.EasingMode;
import com.kreadex.tickFilm.data.EasingType;

public class TickAnimation {

    public double start;
    public double target;

    public long startTimeMs;
    public long durationMs;

    public EasingMode easingMode;
    public EasingType easingType;

    public TickAnimation(
            double start,
            double target,
            long durationMs,
            EasingMode easingMode,
            EasingType easingType
    ) {
        this.start = start;
        this.target = target;
        this.durationMs = durationMs;
        this.easingMode = easingMode;
        this.easingType = easingType;
    }
}