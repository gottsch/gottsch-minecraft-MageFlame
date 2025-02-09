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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * 
 * @author Mark Gottschling on Jul 30, 2022
 *
 */
public class LifespanUpdateC2S {
	private final UUID entityUuId;
	private final int entityId;

	public LifespanUpdateC2S(UUID entityUuId, int entityId) {
		this.entityUuId = entityUuId;
		this.entityId = entityId;
	}

	public static void encode(LifespanUpdateC2S msg, FriendlyByteBuf buf) {
		buf.writeUUID(msg.entityUuId);
		buf.writeInt(msg.entityId);
	}

	public static LifespanUpdateC2S decode(FriendlyByteBuf buf) {
		UUID entityUuid = buf.readUUID();
		int entityId = buf.readInt();
		return new LifespanUpdateC2S(entityUuid, entityId);
	}

	public static void handle(LifespanUpdateC2S msg, Supplier<Context> context) {
//		EEchelons.LOGGER.debug("received request message -> {}", msg);
		Context ctx = context.get();
		LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

		if (sideReceived != LogicalSide.SERVER) {
			MageFlame.LOGGER.warn("LevelRequestToServer received on wrong side -> {}", ctx.getDirection().getReceptionSide());
			return;
		}

		ctx.enqueueWork(() -> {
			processMessage(ctx, msg);
		});

		context.get().setPacketHandled(true);

	}

	private static void processMessage(Context ctx, LifespanUpdateC2S msg) {
		ServerLevel world = (ServerLevel) ctx.getSender().level();

//		EEchelons.LOGGER.debug("processing request message -> {}", msg);
		if (world != null) {
			Entity entityByUuid = world.getEntity(msg.entityUuId);
			if (entityByUuid != null) {
				Entity entity = world.getEntity(msg.entityId);

				// send the lifespan back to the client
				LifespanUpdateS2C message = new LifespanUpdateS2C(entity.getId(), ((ISummonedEntity)entity).getLifespan());
				ModNetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY
						.with(() -> entityByUuid == null ? entity : entityByUuid), message);

			}
		}
	}

	@Override
	public String toString() {
		return "LifespanUpdateC2S{" +
				"entityUuId=" + entityUuId +
				", entityId=" + entityId +
				'}';
	}
}
