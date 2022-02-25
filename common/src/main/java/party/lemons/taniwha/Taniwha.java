package party.lemons.taniwha;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import party.lemons.taniwha.block.TBlocks;
import party.lemons.taniwha.data.BrewingFuelReloadListener;
import party.lemons.taniwha.entity.TEntities;
import party.lemons.taniwha.item.TItems;

public class Taniwha
{
    public static void init()
    {
        TEntities.init();
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new BrewingFuelReloadListener());

        LifecycleEvent.SETUP.register(()->{
            TItems.runModifiers();
            TBlocks.initModifiers();
            TBlocks.initCompostables();
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(TConstants.MOD_ID, path);
    }
}
