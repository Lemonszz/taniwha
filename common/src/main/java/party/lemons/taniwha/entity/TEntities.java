package party.lemons.taniwha.entity;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.entity.boat.BoatTypes;
import party.lemons.taniwha.entity.boat.TBoat;
import party.lemons.taniwha.entity.boat.TBoatRender;
import party.lemons.taniwha.entity.boat.TChestBoat;

import java.util.function.Supplier;

public class TEntities {
    static boolean dfCache = SharedConstants.CHECK_DATA_FIXER_SCHEMA;
    static {SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;}   //STFU

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(TConstants.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final Supplier<EntityType<? extends TBoat>> T_BOAT = ENTITIES.register(Taniwha.id("t_boat"), ()->
        EntityType.Builder.of((EntityType.EntityFactory<TBoat>)TBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(128).updateInterval(3).build("t_boat")
    );
    public static final Supplier<EntityType<? extends TChestBoat>> T_CHEST_BOAT = ENTITIES.register(Taniwha.id("t_chest_boat"), ()->
            EntityType.Builder.of((EntityType.EntityFactory<TChestBoat>)TChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(128).updateInterval(3).build("t_chest_boat")
    );


    static {SharedConstants.CHECK_DATA_FIXER_SCHEMA = dfCache;}

    public static void init()
    {
        ENTITIES.register();
    }

    public static void registerModels()
    {
        BoatTypes.registerModelLayers();
        EntityRendererRegistry.register(T_BOAT, c->new TBoatRender(c, false));
        EntityRendererRegistry.register(T_CHEST_BOAT, c->new TBoatRender(c, true));
    }
}
