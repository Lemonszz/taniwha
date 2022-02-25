package party.lemons.taniwha.fabric;

import net.fabricmc.api.ModInitializer;
import party.lemons.taniwha.Taniwha;

public class TaniwhaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Taniwha.init();
    }
}
