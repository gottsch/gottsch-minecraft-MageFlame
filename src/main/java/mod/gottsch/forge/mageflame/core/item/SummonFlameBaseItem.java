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
package mod.gottsch.forge.mageflame.core.item;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.config.Config;
import mod.gottsch.forge.mageflame.core.entity.creature.ISummonFlameEntity;
import mod.gottsch.forge.mageflame.core.setup.Registration;
import mod.gottsch.forge.mageflame.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * 
 * @author Mark Gottschling Jan 22, 2023
 *
 */
public abstract class SummonFlameBaseItem extends Item implements ISummonFlameItem {

	/**
	 * 
	 * @param properties
	 */
	public SummonFlameBaseItem(Properties properties) {
		super(properties);
	}

	@Override
	public Component getName(ItemStack stack) {
		return ((TranslatableComponent)super.getName(stack)).withStyle(ChatFormatting.AQUA);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		appendBaseText(stack, level, tooltip, flag);
		LangUtil.appendAdvancedHoverText(tooltip, tt -> {
			appendAdvancedText(stack, level, tooltip, flag);
		});
	}

	public void appendBaseText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {

	}

	public void appendAdvancedText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {

	}
	
	public void appendLore(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag, String key) {
		TranslatableComponent lore = new TranslatableComponent(LangUtil.tooltip(key));
		tooltip.add(new TextComponent(" "));
		for (String s : lore.getString().split("~")) {	
			tooltip.add(new TranslatableComponent(LangUtil.INDENT2)
					.append(new TextComponent(s).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC)));
		}
	}

	public String ticksToTime(int ticks) {
		int secs = ticks / 20;   
		int hours = secs / 3600;
		int remainder = secs % 3600;
		int minutes = remainder / 60;
		int seconds = remainder % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack heldStack = player.getItemInHand(hand);
		if (level.isClientSide) {
			return InteractionResultHolder.pass(heldStack);
		}
		Vec3 spawnPos = getByPlayerPos(player);

		// spawn entity
		MageFlame.LOGGER.debug("using summon flame item...");
		Optional<Mob> mob = spawn((ServerLevel)level, new Random(), player, getSummonFlameEntity(), spawnPos);
		if (mob.isPresent()) {
			MageFlame.LOGGER.debug("summon flame is present...");
			// reduce scroll stack size ie consume
			heldStack.shrink(1);
			return InteractionResultHolder.consume(heldStack);
		}
		return super.use(level, player, hand);
	}
}
