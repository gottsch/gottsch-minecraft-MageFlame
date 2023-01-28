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
package mod.gottsch.forge.mageflame.core.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import mod.gottsch.forge.mageflame.core.MageFlame;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

/**
 * 
 * @author Mark Gottschling Jan 23, 2023
 *
 */
public class SummonFlameRegistry {
	public static final Map<UUID, UUID> REGISTRY = Maps.newHashMap();
	
	/**
	 * 
	 */
	private SummonFlameRegistry() {}
	
	/**
	 * save/load shouldn't be  necessary as entities self-register when then join a level.
	 * @return
	 */
	public ListTag save() {
		ListTag listTag = new ListTag();
		REGISTRY.forEach((playerUuid, entityUuid) -> {
			CompoundTag tag = new CompoundTag();
			tag.putString("playerUuid", playerUuid.toString());
			tag.putString("entityUuid", entityUuid.toString());
			listTag.add(tag);
		});
		return listTag;		
	}
	
	/**
	 * 
	 * @param listTag
	 */
	public void load(ListTag listTag) {
		if (listTag != null) {
			MageFlame.LOGGER.debug("loading summon flame registry...");  	
			listTag.forEach(element -> {
				
				CompoundTag tag = (CompoundTag)element;
				if (tag.contains("playerUuid") && tag.contains("entityUuid")) {
					REGISTRY.put(tag.getUUID("playerUuid"), tag.getUUID("entityUuid"));
				}
			});
		}
	}
	
	/**
	 * 
	 * @param key the name key for the enum set. ex "rarity" for the Rarity enum.
	 * @param ienum
	 */
	public static void register(UUID uuid, UUID entityId) {
		if (!REGISTRY.containsKey(entityId)) {
			REGISTRY.put(uuid, entityId);
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param ienum
	 * @return
	 */
	public static boolean isRegistered(UUID uuid) {
		if (REGISTRY.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param uuid
	 */
	public static void unregister(UUID uuid) {
		REGISTRY.remove(uuid);
	}
	
	/**
	 * 
	 * @param key
	 * @param enumKey
	 * @return
	 */
	public static UUID get(UUID uuid) {
		if (REGISTRY.containsKey(uuid)) {
			return REGISTRY.get(uuid);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static List<UUID> getValues() {
		return new ArrayList<>(REGISTRY.values());
	}
}
