package com.teampotato.comparties.mixin;

import deathtags.core.events.EventCommon;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EventCommon.class, remap = false)
public abstract class MixinEventCommon {
    @Inject(method = "OnPlayerHurt", at = @At("HEAD"), cancellable = true)
    private void onPlayerHurt(LivingHurtEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}
