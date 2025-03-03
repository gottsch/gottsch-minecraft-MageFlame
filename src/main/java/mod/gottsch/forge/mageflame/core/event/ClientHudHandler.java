/*
 * This file is part of Mage Flame.
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


import com.mojang.authlib.minecraft.client.MinecraftClient;
import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.client.LifespanBar;
import mod.gottsch.forge.mageflame.core.config.Config;
import mod.gottsch.forge.mageflame.core.entity.creature.ISummonedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by Mark Gottschling on 1/24/2025
 */
@Mod.EventBusSubscriber(modid = MageFlame.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientHudHandler {

    @SubscribeEvent
    public static void onHudRender(final RenderGuiOverlayEvent.Pre evt) {
        if (!Config.CLIENT.enableLifespanDisplay.get()) {
            return;
        }

        Entity entity = Minecraft.getInstance().crosshairPickEntity;

        // for config distance use
        /*
        double maxReach = 1000; //The farthest target the cameraEntity can detect
        float tickDelta = 1.0F; //Used for tracking animation progress; no tracking is 1.0F
        boolean includeFluids = true; //Whether to detect fluids as blocks

        HitResult hit = client.cameraEntity.raycast(maxReach, tickDelta, includeFluids);
         */

//        if (hit.getType() == HitResult.Type.ENTITY) {
//            EntityHitResult entityHit = (EntityHitResult) hit;
//            Entity entity = entityHit.getEntity();
            if (entity instanceof ISummonedEntity summonedEntity) {
                GuiGraphics matrixStack = evt.getGuiGraphics();
                LifespanBar.renderLevelBar(matrixStack, summonedEntity);
            }
//        }
    }
}

