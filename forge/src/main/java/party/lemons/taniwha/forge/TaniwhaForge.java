package party.lemons.taniwha.forge;

import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.Env;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.TaniwhaClient;

@Mod(TConstants.MOD_ID)
@Mod.EventBusSubscriber(modid = TConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TaniwhaForge {

    public TaniwhaForge() {
        EventBuses.registerModEventBus(TConstants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Taniwha.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(TaniwhaForge::clientSetup);
    }

    private static void modelLoad(ModelRegistryEvent event) {
    }

    public static void clientSetup(FMLClientSetupEvent event)
    {
        if (Platform.getEnvironment() == Env.CLIENT) {
            TaniwhaClient.init();
        }
    }
}
