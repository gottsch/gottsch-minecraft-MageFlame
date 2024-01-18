/*
 * This file is part of  Mage Flame.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
package mod.gottsch.forge.mageflame.core.item;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.setup.Registration;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Mark Gottschling Jan 18, 2024
 *
 */
@Mod.EventBusSubscriber(modid = MageFlame.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MageFlameCreativeModeTabs {

	@SubscribeEvent
	public static void registerItemToTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			Registration.ITEMS.getEntries().forEach(item -> {
				event.accept(item, TabVisibility.PARENT_AND_SEARCH_TABS);
			});
		}
	}
}
