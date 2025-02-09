
package mod.gottsch.forge.mageflame.core.util;

import mod.gottsch.forge.treasure2.Treasure;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Mark Gottschling on Jul 22, 2021
 *
 */
public class ModUtil {

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static ResourceLocation asLocation(String name) {
		return hasDomain(name) ? new ResourceLocation(name) : new ResourceLocation(Treasure.MODID, name);
	}
	
	public static ResourceLocation getName(Block block) {
		// don't bother checking optional - if it is empty, then the block isn't registered and this shouldn't run anyway.
		ResourceLocation name = ForgeRegistries.BLOCKS.getResourceKey(block).get().location();
		return name;
	}

	public static ResourceLocation getName(Item item) {
		// don't bother checking optional - if it is empty, then the block isn't registered and this shouldn't run anyway.
		ResourceLocation name = ForgeRegistries.ITEMS.getResourceKey(item).get().location();
		return name;
	}
	

	public static ResourceLocation getName(Holder<Biome> biome) {
		return biome.unwrapKey().get().location();	
	}
	
	public static boolean hasDomain(String name) {
		return name.indexOf(":") >= 0;
	}
}
