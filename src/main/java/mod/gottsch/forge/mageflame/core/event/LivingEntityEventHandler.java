package mod.gottsch.forge.mageflame.core.event;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.entity.creature.ISummonFlameEntity;
import mod.gottsch.forge.mageflame.core.registry.SummonFlameRegistry;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
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
	public static void onEntityUpdate(LivingTickEvent event) {
		if (event.getEntity().level.isClientSide) {
			return;
		}	
	}
	
	// TODO RESEARCH this could be better implemented in Entity.Load()
	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		if (event.getEntity().level.isClientSide) {
			return;
		}
		if (event.getEntity() instanceof ISummonFlameEntity) {
			ISummonFlameEntity entity = (ISummonFlameEntity)event.getEntity();
			
			// register the entity
			if (entity.getOwnerUUID() != null) {
				if(!SummonFlameRegistry.isRegistered(entity.getOwnerUUID())) {
					SummonFlameRegistry.register(entity.getOwnerUUID(), event.getEntity().getUUID());
				}
			}
			else {
				entity.die();
			}
			
			// update position and light blocks
			entity.updateLightBlocks();
		}
	}
}
