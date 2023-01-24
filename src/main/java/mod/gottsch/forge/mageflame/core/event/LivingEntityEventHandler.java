package mod.gottsch.forge.mageflame.core.event;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.entity.creature.ISummonFlameEntity;
import mod.gottsch.forge.mageflame.core.registry.SummonFlameRegistry;
import net.minecraft.world.damagesource.DamageSource;
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
			if (entity.getOwnerUUID() != null) {
				if(!SummonFlameRegistry.isRegistered(entity.getOwnerUUID())) {
					SummonFlameRegistry.register(entity.getOwnerUUID(), event.getEntity().getUUID());
				}
			}
			else {
				entity.die();
			}
		}
	}
}
