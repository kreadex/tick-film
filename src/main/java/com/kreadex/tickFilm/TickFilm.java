package com.kreadex.tickFilm;

import com.kreadex.tickFilm.animate.TickEngine;
import net.fabricmc.api.ModInitializer;

public class TickFilm implements ModInitializer {

    @Override
    public void onInitialize() {
        TickEngine.init();
    }
}
