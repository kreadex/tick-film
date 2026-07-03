package com.kreadex.tickFilm.client.mixin;

import com.kreadex.tickFilm.client.sound.TickSoundController;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(
            method = "tickEntities",
            at = @At("HEAD")
    )
    private void tickFilm$detectServerTick(
            CallbackInfo ci
    ) {
        TickSoundController.updateServerTick();
    }
}
