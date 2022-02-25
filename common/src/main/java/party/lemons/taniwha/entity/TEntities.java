package party.lemons.taniwha.entity;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.entity.boat.TBoat;
import party.lemons.taniwha.entity.boat.TBoatRender;
import party.lemons.taniwha.registry.RegistryHelper;

public class TEntities {
    static boolean dfCache = SharedConstants.CHECK_DATA_FIXER_SCHEMA;
    static {SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;}   //STFU

    public static final EntityType<TBoat> T_BOAT = EntityType.Builder.of((EntityType.EntityFactory<TBoat>)TBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(128).updateInterval(3).build("t_boat");

    static {SharedConstants.CHECK_DATA_FIXER_SCHEMA = dfCache;}

    public static void init()
    {
        RegistryHelper.register(TConstants.MOD_ID, Registry.ENTITY_TYPE, EntityType.class, TEntities.class);
    }

    public static void registerModels()
    {
        EnvExecutor.runInEnv(Env.CLIENT, ()->()-> {
            EntityRendererRegistry.register(() -> T_BOAT, TBoatRender::new);
        });
    }
}
