package party.lemons.taniwha;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
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
        EnvExecutor.runInEnv(Env.CLIENT, ()->()-> {
            EntityRendererRegistry.register(TEntities.T_BOAT, c -> new TBoatRender(c, false));
            EntityRendererRegistry.register(TEntities.T_CHEST_BOAT, c -> new TBoatRender(c, true));
        });
    }
}
