package party.lemons.taniwha;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import party.lemons.taniwha.block.TBlocks;
import party.lemons.taniwha.block.instrument.NoteInstrument;
import party.lemons.taniwha.block.instrument.TagNoteInstrument;
import party.lemons.taniwha.config.TaniwhaConfig;
import party.lemons.taniwha.data.BrewingFuelReloadListener;
import party.lemons.taniwha.entity.TEntities;
import party.lemons.taniwha.entity.golem.GolemHandler;
import party.lemons.taniwha.item.TItems;

public class Taniwha
{
    public static final TaniwhaConfig CONFIG = new TaniwhaConfig();


    public static void init()
    {
        TEntities.init();
        TItems.init();
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new BrewingFuelReloadListener());


        LifecycleEvent.SETUP.register(()->{
            TItems.runModifiers();
            TBlocks.initModifiers();
            TBlocks.initCompostables();

            GolemHandler.init();
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
