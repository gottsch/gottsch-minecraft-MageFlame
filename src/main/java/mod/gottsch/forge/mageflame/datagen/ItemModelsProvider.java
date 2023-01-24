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
import mod.gottsch.forge.mageflame.core.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Mark Gottschling Jan 20, 2023
 *
 */
public class ItemModelsProvider extends ItemModelProvider {

	public ItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, MageFlame.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		// tabs
		singleTexture(Registration.MAGE_FLAME_SCROLL.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/mage_flame_scroll"));
		singleTexture(Registration.LESSER_REVELATION_SCROLL.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lesser_revelation_scroll"));
		singleTexture(Registration.GREATER_REVELATION_SCROLL.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/greater_revelation_scroll"));
		singleTexture(Registration.WINGED_TORCH_SCROLL.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/winged_torch_scroll"));

	}
}
