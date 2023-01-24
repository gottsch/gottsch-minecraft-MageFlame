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
package mod.gottsch.forge.mageflame.core.config;

import mod.gottsch.forge.gottschcore.config.AbstractConfig;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * 
 * @author Mark Gottschling Jan 19, 2023
 *
 */
public class Config extends AbstractConfig {
	public static final String CATEGORY_DIV = "##############################";
	public static final String UNDERLINE_DIV = "------------------------------";

	// TODO change to the new Echelons style of config setup
	protected static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	protected static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
	
	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec SERVER_CONFIG;
	
	public static final Logging LOGGING;
	public static final ServerConfig SERVER;
	public static Config instance = new Config();
	
	static {
		LOGGING = new Logging(COMMON_BUILDER);
		COMMON_CONFIG = COMMON_BUILDER.build();
		
		SERVER = new ServerConfig(SERVER_BUILDER);
		SERVER_CONFIG = SERVER_BUILDER.build();
	}
	
	/*
	 * 
	 */
	public static class ServerConfig {
		public ForgeConfigSpec.IntValue mageFlameLifespan;
		public ForgeConfigSpec.IntValue lesserRevelationLifespan;
		public ForgeConfigSpec.IntValue greaterRevelationLifespan;
		
		public ServerConfig(ForgeConfigSpec.Builder builder) {

			builder.comment(CATEGORY_DIV, "Flame / Torch Entity Properties", CATEGORY_DIV)
			.push("entities");
			
			mageFlameLifespan = builder
					.comment(" The lifespan of a Mage Flame spell/entity in ticks.", 
							"Ex. 20 ticks * 60 seconds * 5 = 6000 = 5 minutes.")
					.defineInRange("mageFlameLifespan", 6000, 1200, 72000);

			lesserRevelationLifespan = builder
					.comment(" The lifespan of a Lesser Revelation spell/entity in ticks.")
					.defineInRange("lesserRevelationLifespan", 18000, 1200, 72000);
			
			greaterRevelationLifespan = builder
					.comment(" The lifespan of a Greater Revelation spell/entity in ticks.")
					.defineInRange("greaterRevelationLifespan", 36000, 1200, 72000);

			builder.pop();
		}
	}
	
	@Override
	public String getLogsFolder() {
		return Config.LOGGING.folder.get();
	}
	
	public void setLogsFolder(String folder) {
		Config.LOGGING.folder.set(folder);
	}
	
	@Override
	public String getLoggingLevel() {
		return Config.LOGGING.level.get();
	}
}
