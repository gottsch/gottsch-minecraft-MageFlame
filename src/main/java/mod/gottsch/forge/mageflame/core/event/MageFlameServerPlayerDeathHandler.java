/*
 * This file is part of  Mage Flame.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
 *
 * Mage Flame is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mage Flame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURCoordsE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Mage Flame.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.mageflame.core.event;

import com.google.common.eventbus.Subscribe;
import mod.gottsch.forge.mageflame.core.MageFlame;

import mod.gottsch.forge.mageflame.core.util.SpawnUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Mark Gottschling on 1/16/2025
 */
@Mod.EventBusSubscriber(modid = MageFlame.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MageFlameServerPlayerDeathHandler {

    @SubscribeEvent
    public static void afterDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // remove all summoned entities
            SpawnUtil.killAllSummonedEntities((ServerLevel) event.getEntity().level(), player);
        }
    }
}
