/*
 * This file is part of  Enemy Echelons.
 * Copyright (c) 2022, Mark Gottschling (gottsch)
 * 
 * All rights reserved.
 *
 * Enemy Echelons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Enemy Echelons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Enemy Echelons.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.mageflame.core.network;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.entity.creature.ISummonedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

/**
 * 
 * @author Mark Gottschling on Jul 28, 2022
 *
 */
public class LifespanUpdateS2C {
	private final int entityId;
	private final int lifespan;

	public LifespanUpdateS2C(int entityId, int lifespan) {
		this.entityId = entityId;
		this.lifespan = lifespan;
	}

	public static void encode(LifespanUpdateS2C msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(msg.lifespan);
	}

	public static LifespanUpdateS2C decode(FriendlyByteBuf buf) {
		int entityId = buf.readInt();
		int level = buf.readInt();
		return new LifespanUpdateS2C(entityId, level);
	}

	public static void handle(LifespanUpdateS2C msg, Supplier<Context> context) {
//		EEchelons.LOGGER.debug("received message on client -> {}", msg);
		Context ctx = context.get();
		LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

		if (sideReceived != LogicalSide.CLIENT) {
			MageFlame.LOGGER.warn("Lifespan Update To Client received on wrong side -> {}", ctx.getDirection().getReceptionSide());
			return;
		}

		context.get().enqueueWork(() ->
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> processMessage(ctx, msg))
				);
		context.get().setPacketHandled(true);

	}

	private static void processMessage(Context ctx, LifespanUpdateS2C msg) {
		ClientLevel world = Minecraft.getInstance().level;
		if (world != null) {
			Entity entity = world.getEntity(msg.entityId);
			if (entity != null) {
//			EEchelons.LOGGER.debug("handling client message to entity -> {} for level -> {}", entity.getName().getString(), msg.level);
//				EEchelons.LOGGER.debug("setting the level on the client entity");
				((ISummonedEntity)entity).setLifespan(msg.lifespan);
			}

		}
	}

	@Override
	public String toString() {
		return "LevelMessageToClient [entityId=" + entityId + ", level=" + lifespan + "]";
	}


}
