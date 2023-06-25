package com.teampotato.comparties.mixin;

import deathtags.api.PartyHelper;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.spells.SchoolType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.teampotato.comparties.Comparties.PARTY;

@Mixin(value = DamageSources.class, remap = false)
public abstract class MixinDamageSources {
    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    private static void onHurt(Entity target, float baseAmount, DamageSource damageSource, SchoolType damageSchool, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof ServerPlayer targetPlayer) {
            Entity sourceEntity = damageSource.getEntity();
            Entity directSourceEntity = damageSource.getDirectEntity();
            if (sourceEntity instanceof ServerPlayer sourcePlayer && PartyHelper.Server.GetRelation(targetPlayer, sourcePlayer) == PARTY) {
                cir.setReturnValue(false);
                cir.cancel();
            }
            if (directSourceEntity instanceof ServerPlayer directSourcePlayer && PartyHelper.Server.GetRelation(targetPlayer, directSourcePlayer) == PARTY) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
