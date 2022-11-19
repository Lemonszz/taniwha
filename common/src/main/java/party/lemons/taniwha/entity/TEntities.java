package party.lemons.taniwha.entity;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.entity.boat.TBoat;
import party.lemons.taniwha.entity.boat.TChestBoat;

import java.util.function.Supplier;

public class TEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(TConstants.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final Supplier<EntityType<? extends TBoat>> T_BOAT = ENTITIES.register(Taniwha.id("t_boat"), ()->
        TEntityTypeBuilder.of((EntityType.EntityFactory<TBoat>)TBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(128).updateInterval(3).build()
    );
    public static final Supplier<EntityType<? extends TChestBoat>> T_CHEST_BOAT = ENTITIES.register(Taniwha.id("t_chest_boat"), ()->
            TEntityTypeBuilder.of((EntityType.EntityFactory<TChestBoat>)TChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(128).updateInterval(3).build()
    );

    public static void init()
    {
        ENTITIES.register();
    }
}
