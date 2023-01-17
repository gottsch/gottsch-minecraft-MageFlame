package mod.gottsch.forge.mageflame.setup;

import mod.gottsch.forge.mageflame.MageFlame;
import mod.gottsch.forge.mageflame.block.MageFlameBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 
 * @author Mark Gottschling on Nov 6, 2022
 *
 */
public class Registration {
	/*
	 * deferred registries
	 */
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MageFlame.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MageFlame.MOD_ID);
	
	public static final RegistryObject<Block> FLAME_BLOCK = Registration.BLOCKS.register("flame_block",	() -> new MageFlameBlock(Block.Properties.of(Material.WOOD)));

	public static void init() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(eventBus);	
	}
}
