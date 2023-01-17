package mod.gottsch.forge.mageflame.block;

import net.minecraft.world.level.block.AirBlock;

/**
 * 
 * @author Mark Gottschling on Nov 6, 2022
 *
 */
public class MageFlameBlock extends AirBlock {

	public MageFlameBlock(Properties properties) {
		super(properties.noCollission().lightLevel((state) -> {
		      return 14;
		   }).noDrops().air());
		// NOTE do not add to a tab group
	}

}
