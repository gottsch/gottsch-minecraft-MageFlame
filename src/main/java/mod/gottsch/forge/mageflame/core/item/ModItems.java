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
package mod.gottsch.forge.mageflame.core.item;

import mod.gottsch.forge.mageflame.core.MageFlame;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author Mark Gottschling on 1/23/2025
 */
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MageFlame.MOD_ID);

    public static final RegistryObject<Item> MAGE_FLAME_SCROLL = ITEMS.register("mage_flame_scroll", () -> new MageFlameScroll(new Item.Properties()));
    public static final RegistryObject<Item> LESSER_REVELATION_SCROLL = ITEMS.register("lesser_revelation_scroll", () -> new LesserRevelationScroll(new Item.Properties()));
    public static final RegistryObject<Item> GREATER_REVELATION_SCROLL = ITEMS.register("greater_revelation_scroll", () -> new GreaterRevelationScroll(new Item.Properties()));
    public static final RegistryObject<Item> WINGED_TORCH_SCROLL = ITEMS.register("winged_torch_scroll", () -> new WingedTorchScroll(new Item.Properties()));
    public static final RegistryObject<Item> EMBER_HOUND_SCROLL = ITEMS.register("ember_hound_scroll", () ->new EmberHoundScroll(new Item.Properties()));
    public static final RegistryObject<Item> BUBBLE_FLAME_SCROLL = ITEMS.register("bubble_flame_scroll", () ->new BubbleFlameScroll(new Item.Properties()));
    public static final RegistryObject<Item> GLOWGLOB_BALL = ITEMS.register("glowglob_ball", () ->new GlowglobBall(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
