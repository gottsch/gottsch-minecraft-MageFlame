package mod.gottsch.forge.mageflame;

import mod.gottsch.forge.mageflame.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Mark Gottschling on Nov 6, 2022
 *
 */
@Mod(value = MageFlame.MOD_ID)
public class MageFlame {

	public static final String MOD_ID = "mageflame";
	
	// TEMP mageflame tracking
	public static BlockPos currentPos;
	public static BlockPos lastPos;
	
	public MageFlame() {
		// register the deferred registries
        Registration.init();
	}
}
