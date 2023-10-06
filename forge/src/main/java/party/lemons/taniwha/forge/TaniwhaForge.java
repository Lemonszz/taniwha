package party.lemons.taniwha.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;

@Mod(TConstants.MOD_ID)
@Mod.EventBusSubscriber(modid = TConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TaniwhaForge {

    public TaniwhaForge() {
        EventBuses.registerModEventBus(TConstants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Taniwha.init();
        MinecraftForge.EVENT_BUS.addListener(TForgeEvents::onPlaceEvent);

        EnvExecutor.runInEnv(Env.CLIENT, ()-> TForgeClientEvents::initClient);
    }
}
