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

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.client.model.entity.LargeFlameBallModel;
import mod.gottsch.forge.mageflame.core.entity.creature.GreaterRevelationEntity;
import mod.gottsch.forge.mageflame.core.entity.creature.SummonFlameBaseEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;


/**
 * 
 * @author Mark Gottschling Jan 23, 2023
 *
 * @param <T>
 */
public class GreaterRevelationRenderer extends MobRenderer<GreaterRevelationEntity, EntityModel<GreaterRevelationEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(MageFlame.MOD_ID, "textures/entity/greater_revelation.png");
	private final float scale;
	
	/**
	 * 
	 * @param context
	 */
	public GreaterRevelationRenderer(EntityRendererManager renderManager, BipedModel<BoundSoulEntity> modelBiped, float shadowSize) {
		super(context, new LargeFlameBallModel<>(context.bakeLayer(LargeFlameBallModel.LAYER_LOCATION)), 0F);
		this.scale = 1.0F;
	}


	@Override
	public ResourceLocation getTextureLocation(GreaterRevelationEntity entity) {
		return TEXTURE;
	}

}
