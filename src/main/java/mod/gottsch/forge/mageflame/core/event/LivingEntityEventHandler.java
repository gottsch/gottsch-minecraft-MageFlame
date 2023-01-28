package mod.gottsch.forge.mageflame.core.event;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.entity.creature.ISummonFlameEntity;
import mod.gottsch.forge.mageflame.core.registry.SummonFlameRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Mark Gottschling on Nov 6, 2022
 *
 */
@Mod.EventBusSubscriber(modid = MageFlame.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class LivingEntityEventHandler {
//	@SubscribeEvent
	public static void onEntityUpdate(LivingUpdateEvent event) {
		if (event.getEntity().level.isClientSide) {
			return;
		}	
	}
	
	// TODO RESEARCH this could be better implemented in Entity.Load()
	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity().level.isClientSide) {
			return;
		}
		if (event.getEntity() instanceof ISummonFlameEntity) {
			ISummonFlameEntity entity = (ISummonFlameEntity)event.getEntity();
			ServerLevel serverLevel = (ServerLevel)event.getEntity().level;

			// register the entity
			if (entity.getOwnerUUID() != null) {
				if(!SummonFlameRegistry.isRegistered(entity.getOwnerUUID())) {
					SummonFlameRegistry.register(entity.getOwnerUUID(), event.getEntity().getUUID());
				}
				/*
				 * NOTE this is an edge-case scenario where the entity was not unregistered and killed
				 *  and it attempts to reunite with owner.
				 *  TODO need to add additional info like gameTime to determine which is the younger (to keep).
				 */
				else if (!SummonFlameRegistry.get(entity.getOwnerUUID()).equals(event.getEntity().getUUID())) {
					// kill the existing registered entity
					Entity existingMob = serverLevel.getEntity(SummonFlameRegistry.get(entity.getOwnerUUID()));
					if (existingMob != null) {
						((LivingEntity)existingMob).die(DamageSource.GENERIC);
					}
					SummonFlameRegistry.register(entity.getOwnerUUID(), event.getEntity().getUUID());
				}
			}
			else {
				// kill the entity if it doesn't have an owner
				entity.die();
			}
			
			// update position and light blocks
			entity.updateLightBlocks();
		}
	}
}
