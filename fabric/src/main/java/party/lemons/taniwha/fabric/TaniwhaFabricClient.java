package party.lemons.taniwha.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.TaniwhaClient;

public class TaniwhaFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TaniwhaClient.init();
    }
}
