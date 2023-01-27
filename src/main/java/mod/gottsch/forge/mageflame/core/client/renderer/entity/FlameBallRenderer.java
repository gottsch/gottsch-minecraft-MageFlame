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
package mod.gottsch.forge.mageflame.core.client.renderer.entity;

import com.mojang.blaze3d.vertex.MatrixStack;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.client.model.entity.FlameBallModel;
import mod.gottsch.forge.mageflame.core.entity.creature.MageFlameEntity;
import mod.gottsch.forge.mageflame.core.entity.creature.SummonFlameBaseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling Jan 19, 2023
 *
 * @param <T>
 */
public class FlameBallRenderer<T extends SummonFlameBaseEntity> extends MobRenderer<T, FlameBallModel<T>> {
	// TODO if Mage Flame uses this model, update the texture name accordingly.
	private static final ResourceLocation TEXTURE = new ResourceLocation(MageFlame.MOD_ID, "textures/entity/flame_ball.png");
	private final float scale;
	
	/**
	 * 
	 * @param context
	 */
	public FlameBallRenderer(EntityRendererProvider.Context context) {
		super(context, new FlameBallModel<>(context.bakeLayer(FlameBallModel.LAYER_LOCATION)), 0F);
		this.scale = 1.0F;
	}

	@Override
	protected void scale(SummonFlameBaseEntity  mageFlame, MatrixStack pose, float scale) {
		pose.scale(this.scale, this.scale, this.scale);
	}

	@Override
	public ResourceLocation getTextureLocation(SummonFlameBaseEntity entity) {
		return TEXTURE;
	}
}
