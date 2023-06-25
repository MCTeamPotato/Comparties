package com.teampotato.comparties;

import deathtags.api.PartyHelper;
import deathtags.api.relation.EnumRelation;
import deathtags.config.ConfigHolder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events {
    private static final EnumRelation PARTY = EnumRelation.PARTY;
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void OnPlayerHurt(LivingAttackEvent event) {
        if (ConfigHolder.COMMON.friendlyFireDisabled.get()) {
            Entity target = event.getEntity();
            Entity sourceEntity = event.getSource().getEntity();
            Entity directSourceEntity = event.getSource().getDirectEntity();
            if (target instanceof TamableAnimal tamableTarget && tamableTarget.getOwner() instanceof ServerPlayer ownerPlayer && sourceEntity instanceof ServerPlayer sourcePlayer && PartyHelper.Server.GetRelation(ownerPlayer, sourcePlayer) == PARTY) {
                event.setCanceled(true);
            } else if (target instanceof ServerPlayer targetPlayer) {
                if (sourceEntity instanceof ServerPlayer sourcePlayer && PartyHelper.Server.GetRelation(targetPlayer, sourcePlayer) == PARTY) {
                    event.setCanceled(true);
                    return;
                }
                if (directSourceEntity instanceof ServerPlayer directSourcePlayer && PartyHelper.Server.GetRelation(directSourcePlayer, targetPlayer) == PARTY) event.setCanceled(true);
            }
        }
    }
}
