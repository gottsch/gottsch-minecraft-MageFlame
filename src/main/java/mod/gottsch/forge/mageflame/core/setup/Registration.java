package mod.gottsch.forge.mageflame.core.setup;

import mod.gottsch.forge.mageflame.core.MageFlame;
import mod.gottsch.forge.mageflame.core.block.SummonFlameBlock;
import mod.gottsch.forge.mageflame.core.config.Config;
import mod.gottsch.forge.mageflame.core.entity.creature.GreaterRevelationEntity;
import mod.gottsch.forge.mageflame.core.entity.creature.LesserRevelationEntity;
import mod.gottsch.forge.mageflame.core.entity.creature.MageFlameEntity;
import mod.gottsch.forge.mageflame.core.entity.creature.WingedTorchEntity;
import mod.gottsch.forge.mageflame.core.item.GreaterRevelationScroll;
import mod.gottsch.forge.mageflame.core.item.LesserRevelationScroll;
import mod.gottsch.forge.mageflame.core.item.MageFlameScroll;
import mod.gottsch.forge.mageflame.core.item.WingedTorchScroll;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
	public static final String MAGE_FLAME = "mage_flame";
	public static final String LESSER_REVELATION = "lesser_revelation";
	public static final String GREATER_REVELATION = "greater_revelation"; 
	public static final String WINGED_TORCH = "winged_torch"; 
	
	/*
	 * deferred registries
	 */
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MageFlame.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MageFlame.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MageFlame.MOD_ID);
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MageFlame.MOD_ID);

	// Blocks
	public static final RegistryObject<Block> MAGE_FLAME_BLOCK = Registration.BLOCKS.register(MAGE_FLAME,	
			() -> new SummonFlameBlock(Block.Properties.of(Material.FIRE)
					.noCollission().lightLevel((state) -> {
						return 11;
					}).noDrops()));
	public static final RegistryObject<Block> LESSER_REVELATION_BLOCK = Registration.BLOCKS.register(LESSER_REVELATION,	
			() -> new SummonFlameBlock(Block.Properties.of(Material.FIRE)
					.noCollission().lightLevel((state) -> {
						return 13;
					}).noDrops()));
	public static final RegistryObject<Block> GREATER_REVELATION_BLOCK = Registration.BLOCKS.register(GREATER_REVELATION,	
			() -> new SummonFlameBlock(Block.Properties.of(Material.FIRE)
					.noCollission().lightLevel((state) -> {
						return 15;
					}).noDrops()));

	// items
	public static final RegistryObject<Item> MAGE_FLAME_SCROLL = Registration.ITEMS.register("mage_flame_scroll", () -> new MageFlameScroll(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> LESSER_REVELATION_SCROLL = Registration.ITEMS.register("lesser_revelation_scroll", () -> new LesserRevelationScroll(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> GREATER_REVELATION_SCROLL = Registration.ITEMS.register("greater_revelation_scroll", () -> new GreaterRevelationScroll(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> WINGED_TORCH_SCROLL = Registration.ITEMS.register("winged_torch_scroll", () -> new WingedTorchScroll(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	// entities
	public static final RegistryObject<EntityType<MageFlameEntity>> MAGE_FLAME_ENTITY  = Registration.ENTITIES.register(MAGE_FLAME, () -> EntityType.Builder.of(MageFlameEntity::new, MobCategory.CREATURE)
			.sized(0.125F, 0.125F)
			.clientTrackingRange(8)
			.setTrackingRange(20)
			.setShouldReceiveVelocityUpdates(false)
			.build(MAGE_FLAME));
	
	public static final RegistryObject<EntityType<LesserRevelationEntity>> LESSER_REVELATION_ENTITY  = Registration.ENTITIES.register(LESSER_REVELATION, () -> EntityType.Builder.of(LesserRevelationEntity::new, MobCategory.CREATURE)
			.sized(0.125F, 0.125F)
			.clientTrackingRange(8)
			.setTrackingRange(20)
			.setShouldReceiveVelocityUpdates(false)
			.build(LESSER_REVELATION));
	
	public static final RegistryObject<EntityType<GreaterRevelationEntity>> GREATER_REVELATION_ENTITY  = Registration.ENTITIES.register(GREATER_REVELATION, () -> EntityType.Builder.of(GreaterRevelationEntity::new, MobCategory.CREATURE)
			.sized(0.1875F, 0.1875F)
			.clientTrackingRange(8)
			.setTrackingRange(20)
			.setShouldReceiveVelocityUpdates(false)
			.build(GREATER_REVELATION));
	
	public static final RegistryObject<EntityType<WingedTorchEntity>> WINGED_TORCH_ENTITY  = Registration.ENTITIES.register(WINGED_TORCH, () -> EntityType.Builder.of(WingedTorchEntity::new, MobCategory.CREATURE)
			.sized(0.375F, 0.25F)
			.clientTrackingRange(8)
			.setTrackingRange(20)
			.setShouldReceiveVelocityUpdates(false)
			.build(GREATER_REVELATION));

	// particles
	public static final RegistryObject<SimpleParticleType> REVELATION_PARTICLE = Registration.PARTICLES.register("revelation_particle", () -> new SimpleParticleType(true));
	
	
	/**
	 * 
	 */
	public static void init() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(eventBus);
		ITEMS.register(eventBus);
		ENTITIES.register(eventBus);
		PARTICLES.register(eventBus);
	}
}
