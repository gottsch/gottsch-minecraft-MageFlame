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
package mod.gottsch.forge.mageflame.core;

import com.electronwill.nightconfig.core.CommentedConfig;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import mod.gottsch.forge.mageflame.core.config.Config;
import mod.gottsch.forge.mageflame.core.integration.Integrations;
import mod.gottsch.forge.mageflame.core.setup.CommonSetup;
import mod.gottsch.forge.mageflame.core.setup.DynamicLights;
import mod.gottsch.forge.mageflame.core.setup.Registration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static dev.lambdaurora.lambdynlights.api.DynamicLightHandlers.registerDynamicLightHandler;

/**
 * 
 * @author Mark Gottschling on Nov 6, 2022
 *
 */
@Mod(value = MageFlame.MOD_ID)
public class MageFlame {
	// logger
	public static Logger LOGGER = LogManager.getLogger(MageFlame.MOD_ID);

	public static final String MOD_ID = "mageflame";

	/**
	 * 
	 */
	public MageFlame() {
		// TODO change to the new Echelons style of config setup
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
		
		// register the deferred registries
        Registration.init();
        
		// Register the setup method for modloading
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.addListener(CommonSetup::common);
		eventBus.addListener(this::config);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(this::clientSetup));
	}

	public void clientSetup(FMLClientSetupEvent event) {
		// Dynamic Lights
		registerDynamicLightHandler(Registration.MAGE_FLAME_ENTITY.get(),
				entity -> {
					int luminance = DynamicLights.MAGE_FLAME_LUMINANCE;
					if(entity.getLifespan() <= 1200F) {
						luminance = (int) ((entity.getLifespan() / 1200) * DynamicLights.MAGE_FLAME_LUMINANCE);
						if (luminance < 1) luminance = 1;
					}
					return luminance;
				}
		);
		registerDynamicLightHandler(Registration.LESSER_REVELATION_ENTITY.get(),
				entity -> {
					int luminance = DynamicLights.LESSER_REVELATION_LUMINANCE;
					if(entity.getLifespan() <= 1200F) {
						luminance = (int) ((entity.getLifespan() / 1200) * DynamicLights.LESSER_REVELATION_LUMINANCE);
						if (luminance < 1) luminance = 1;
					}
					return luminance;
				}
		);

		registerDynamicLightHandler(Registration.GREATER_REVELATION_ENTITY.get(),
				entity -> {
					int luminance = DynamicLights.GREATER_REVELATION_LUMINANCE;
					if(entity.getLifespan() <= 1200F) {
						luminance = (int) ((entity.getLifespan() / 1200) * DynamicLights.GREATER_REVELATION_LUMINANCE);
						if (luminance < 1) luminance = 1;
					}
					return luminance;
				}
		);

		registerDynamicLightHandler(Registration.WINGED_TORCH_ENTITY.get(),
				DynamicLightHandler.makeHandler(entity -> DynamicLights.WINGED_TORCH_LUMINANCE, entity -> true)
		);

		registerDynamicLightHandler(Registration.EMBER_HOUND_ENTITY.get(),
				entity -> {
					int luminance = DynamicLights.EMBER_HOUND_LUMINANCE;
					luminance = (int) Math.ceil((entity.getHealth() / entity.getMaxHealth()) * DynamicLights.EMBER_HOUND_LUMINANCE);
					return Math.max(DynamicLights.MIN_EMBER_HOUND_LUMINANCE, luminance);
				}
		);

		registerDynamicLightHandler(Registration.BUBBLE_FLAME_ENTITY.get(),
				entity -> {
					int luminance = DynamicLights.BUBBLE_FLAME_LUMINANCE;
					if(entity.getLifespan() <= 1200F) {
						luminance = (entity.getLifespan() / 1200) * DynamicLights.BUBBLE_FLAME_LUMINANCE;
						if (luminance < 1) luminance = 1;
					}
					return luminance;
				}
		);

		registerDynamicLightHandler(Registration.GLOWGLOB_ENTITY.get(),
				entity -> {
					int luminance = DynamicLights.GLOWGLOB_LUMINANCE;
					if(entity.getLifespan() <= 1200F) {
						luminance = (entity.getLifespan() / 1200) * DynamicLights.GLOWGLOB_LUMINANCE;
						if (luminance < 1) luminance = 1;
					}
					return luminance;
				}
		);
	}

	private void config(final ModConfigEvent event) {
		if (event.getConfig().getModId().equals(MOD_ID)) {
			if (event.getConfig().getType() == ModConfig.Type.SERVER) {
				IConfigSpec<?> spec = event.getConfig().getSpec();
				// get the toml config data
				CommentedConfig commentedConfig = event.getConfig().getConfigData();

				 if (spec == Config.SERVER_CONFIG) {
					 Integrations.registerTreasure2Integration();
				}
			}
		}
	}
}
