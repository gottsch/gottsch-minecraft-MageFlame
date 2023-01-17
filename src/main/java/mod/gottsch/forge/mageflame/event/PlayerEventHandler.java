package mod.gottsch.forge.mageflame.event;

import mod.gottsch.forge.mageflame.MageFlame;
import mod.gottsch.forge.mageflame.setup.Registration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
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
public class PlayerEventHandler {
	@SubscribeEvent
	public static void checkCharmsInteraction(LivingUpdateEvent event) {
		if (event.getEntity().level.isClientSide) {
			return;
		}

		// do something to player every update tick:
		if (event.getEntity() instanceof Player) {

			// get the player
			ServerPlayer player = (ServerPlayer) event.getEntity();
			
			// 
			if (MageFlame.currentPos == null) {
				MageFlame.currentPos = player.blockPosition();
				MageFlame.lastPos = MageFlame.currentPos;
				player.level.setBlockAndUpdate(MageFlame.currentPos.above(), Registration.FLAME_BLOCK.get().defaultBlockState());
			} else {
				if (!player.blockPosition().equals(MageFlame.currentPos)) {
					// TODO check for replaceables and attempt to move above, to the sides, behind, or below
					// TODO if can't then extinguish
					
					// update positions
					MageFlame.lastPos = MageFlame.currentPos;
					MageFlame.currentPos = player.blockPosition();
					
					// update block with flame
					player.level.setBlockAndUpdate(MageFlame.currentPos.above(), Registration.FLAME_BLOCK.get().defaultBlockState());

					// delete old
					player.level.setBlockAndUpdate(MageFlame.lastPos.above(), Blocks.AIR.defaultBlockState());
				}
			}
		}
	}
}
