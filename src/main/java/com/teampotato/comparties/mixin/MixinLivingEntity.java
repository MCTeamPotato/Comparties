package com.teampotato.comparties.mixin;

import deathtags.api.PartyHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.ThrownPotion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.teampotato.comparties.Comparties.PARTY;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity pet = getThis();
        if (pet instanceof TamableAnimal targetPet && targetPet.getOwner() instanceof ServerPlayer petOwner) {
            Entity sourceEntity = pSource.getEntity();
            Entity directSourceEntity = pSource.getDirectEntity();
            if (sourceEntity instanceof ServerPlayer sourcePlayer && PartyHelper.Server.GetRelation(petOwner, sourcePlayer) == PARTY) {
                cir.setReturnValue(false);
                cir.cancel();
                return;
            } else if (sourceEntity instanceof ThrownPotion sourcePotion &&
                    sourcePotion.getOwner() instanceof ServerPlayer ownerPlayer &&
                    PartyHelper.Server.GetRelation(petOwner, ownerPlayer) == PARTY) {
                cir.setReturnValue(false);
                cir.cancel();
                return;
            } else if (sourceEntity instanceof AreaEffectCloud sourceCloud &&
                    sourceCloud.getOwner() instanceof ServerPlayer ownerPlayer &&
                    PartyHelper.Server.GetRelation(petOwner, ownerPlayer) == PARTY) {
                cir.setReturnValue(false);
                cir.cancel();
                return;
            }

            if (directSourceEntity instanceof ServerPlayer sourcePlayer && PartyHelper.Server.GetRelation(petOwner, sourcePlayer) == PARTY) {
                cir.setReturnValue(false);
                cir.cancel();
            } else if (directSourceEntity instanceof ThrownPotion sourcePotion &&
                    sourcePotion.getOwner() instanceof ServerPlayer ownerPlayer &&
                    PartyHelper.Server.GetRelation(petOwner, ownerPlayer) == PARTY) {
                cir.setReturnValue(false);
                cir.cancel();
            } else if (directSourceEntity instanceof AreaEffectCloud sourceCloud &&
                    sourceCloud.getOwner() instanceof ServerPlayer ownerPlayer &&
                    PartyHelper.Server.GetRelation(petOwner, ownerPlayer) == PARTY) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

    private LivingEntity getThis() {
        return (LivingEntity) (Object) this;
    }
}
