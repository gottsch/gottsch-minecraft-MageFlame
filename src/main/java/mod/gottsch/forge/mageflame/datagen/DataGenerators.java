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
package mod.gottsch.forge.mageflame.datagen;

import mod.gottsch.forge.mageflame.core.MageFlame;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Mark Gottschling on Nov 6, 2022
 *
 */
@Mod.EventBusSubscriber(modid = MageFlame.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new Recipes(output));
			//            generator.addProvider(new TutLootTables(generator));
			//            TutBlockTags blockTags = new TutBlockTags(generator, event.getExistingFileHelper());
			//            generator.addProvider(blockTags);
			//            generator.addProvider(new DDItemTags(generator, blockTags, event.getExistingFileHelper()));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new BlockStates(output, event.getExistingFileHelper()));
			generator.addProvider(event.includeClient(), new ItemModelsProvider(output, event.getExistingFileHelper()));
			generator.addProvider(event.includeClient(), new LanguageGen(output, "en_us"));
		}
	}
}