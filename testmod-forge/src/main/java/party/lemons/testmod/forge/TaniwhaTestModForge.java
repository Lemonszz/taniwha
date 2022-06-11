package party.lemons.testmod.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import party.lemons.testmod.TaniwhaTestMod;

@Mod(TaniwhaTestMod.MODID)
public class TaniwhaTestModForge
{
	public TaniwhaTestModForge()
	{
		EventBuses.registerModEventBus(TaniwhaTestMod.MODID, FMLJavaModLoadingContext.get().getModEventBus());

		TaniwhaTestMod.init();
	}
}
