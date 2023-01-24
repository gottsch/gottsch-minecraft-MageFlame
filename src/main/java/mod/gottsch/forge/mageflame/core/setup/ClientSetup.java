/*
 * This file is part of  Mage Flame.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
 *
 * Mage Flame is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mage Flame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Mage Flame.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.mageflame.core.setup;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.client.model.entity.FlameBallModel;
import mod.gottsch.forge.mageflame.core.client.model.entity.LargeFlameBallModel;
import mod.gottsch.forge.mageflame.core.client.model.entity.WingedTorchModel;
import mod.gottsch.forge.mageflame.core.client.renderer.entity.GreaterRevelationRenderer;
import mod.gottsch.forge.mageflame.core.client.renderer.entity.LesserRevelationRenderer;
import mod.gottsch.forge.mageflame.core.client.renderer.entity.MageFlameRenderer;
import mod.gottsch.forge.mageflame.core.client.renderer.entity.WingedTorchRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client only event bus subscriber.
 * @author Mark Gottschling on Apr 2, 2022
 *
 */
@Mod.EventBusSubscriber(modid = MageFlame.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
    	
    }
    
	/**
	 * register layers
	 * @param event
	 */
	@SubscribeEvent()
	public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(FlameBallModel.LAYER_LOCATION, FlameBallModel::createBodyLayer);
		event.registerLayerDefinition(LargeFlameBallModel.LAYER_LOCATION, LargeFlameBallModel::createBodyLayer);
		event.registerLayerDefinition(WingedTorchModel.LAYER_LOCATION, WingedTorchModel::createBodyLayer);
	}

	/**
	 * register renderers
	 * @param event
	 */
	@SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.MAGE_FLAME_ENTITY.get(), MageFlameRenderer::new);
        event.registerEntityRenderer(Registration.LESSER_REVELATION_ENTITY.get(), LesserRevelationRenderer::new);
        event.registerEntityRenderer(Registration.GREATER_REVELATION_ENTITY.get(), GreaterRevelationRenderer::new);
        event.registerEntityRenderer(Registration.WINGED_TORCH_ENTITY.get(), WingedTorchRenderer::new);

	}
	
	@SubscribeEvent
	public static void registerParticleProviders(ParticleFactoryRegisterEvent event) {
		ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
		
		particleEngine.register(Registration.REVELATION_PARTICLE.get(), 
				FlameParticle.Provider::new);
		
	}
}
