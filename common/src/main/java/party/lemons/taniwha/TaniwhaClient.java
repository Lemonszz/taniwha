package party.lemons.taniwha;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import party.lemons.taniwha.entity.TEntities;
import party.lemons.taniwha.entity.boat.BoatTypes;
import party.lemons.taniwha.entity.boat.TBoatRender;

public class TaniwhaClient
{
    public static void init()
    {
        registerModels();
    }

    public static void registerModels()
    {
        BoatTypes.registerModelLayers();
        EntityRendererRegistry.register(TEntities.T_BOAT, c->new TBoatRender(c, false));
        EntityRendererRegistry.register(TEntities.T_CHEST_BOAT, c->new TBoatRender(c, true));
    }
}
