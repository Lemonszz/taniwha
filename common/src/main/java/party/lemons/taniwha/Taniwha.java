package party.lemons.taniwha;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import party.lemons.taniwha.block.TBlockTags;
import party.lemons.taniwha.config.TaniwhaConfig;
import party.lemons.taniwha.data.BrewingFuelReloadListener;
import party.lemons.taniwha.data.CompostReloadListener;
import party.lemons.taniwha.entity.TEntities;
import party.lemons.taniwha.entity.golem.GolemHandler;
import party.lemons.taniwha.hooks.entity.TSpawnPlacement;
import party.lemons.taniwha.network.TNetwork;

public class Taniwha
{
    public static final TaniwhaConfig CONFIG = new TaniwhaConfig();

    public static void init()
    {
        TEntities.init();
        TBlockTags.init();
        TNetwork.init();
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new BrewingFuelReloadListener());
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new CompostReloadListener());

        LifecycleEvent.SETUP.register(()->{
            GolemHandler.init();
            TSpawnPlacement.internal_Register();
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(TConstants.MOD_ID, path);
    }
    public static ResourceLocation commonId(String path)
    {
        return new ResourceLocation(TConstants.COMMON_MOD_ID, path);
    }
}
