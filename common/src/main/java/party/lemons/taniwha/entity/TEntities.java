package party.lemons.taniwha.entity;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.TaniwhaClient;
import party.lemons.taniwha.entity.boat.BoatTypes;
import party.lemons.taniwha.entity.boat.TBoat;
import party.lemons.taniwha.entity.boat.TBoatRender;
import party.lemons.taniwha.registry.RegistryHelper;

import java.util.function.Supplier;

public class TEntities {
    static boolean dfCache = SharedConstants.CHECK_DATA_FIXER_SCHEMA;
    static {SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;}   //STFU

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(TConstants.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final Supplier<EntityType<? extends TBoat>> T_BOAT = ENTITIES.register(Taniwha.id("t_boat"), ()->
        EntityType.Builder.of((EntityType.EntityFactory<TBoat>)TBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(128).updateInterval(3).build("t_boat")
    );


    static {SharedConstants.CHECK_DATA_FIXER_SCHEMA = dfCache;}

    public static void init()
    {
        ENTITIES.register();

        if (Platform.getEnvironment() == Env.CLIENT) {
            registerModels();
        }
    }

    public static void registerModels()
    {
        BoatTypes.registerModelLayers();
        EntityRendererRegistry.register(T_BOAT, TBoatRender::new);
    }
}
