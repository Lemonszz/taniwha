package party.lemons.taniwha;

import dev.architectury.event.events.common.LifecycleEvent;
import party.lemons.taniwha.block.TBlocks;
import party.lemons.taniwha.entity.TEntities;
import party.lemons.taniwha.entity.boat.BoatTypes;
import party.lemons.taniwha.item.TItems;

public class TaniwhaClient
{
    public static void init()
    {
        LifecycleEvent.SETUP.register(()->{
            TBlocks.initClient();
            TEntities.registerModels();
        });
    }

    public static void registerModels()
    {
    //    BoatTypes.registerModelLayers();
    //    TEntities.registerModels();
    }
}
