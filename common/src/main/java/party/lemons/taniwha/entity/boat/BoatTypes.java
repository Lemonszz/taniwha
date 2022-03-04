package party.lemons.taniwha.entity.boat;

import com.google.common.collect.Lists;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;

import java.util.List;

public class BoatTypes
{
    public static List<BoatType> TYPES = Lists.newArrayList();

    public static final BoatType ACACIA = new VanillaBoatType(Taniwha.id("acacia"), Boat.Type.ACACIA, Items.ACACIA_BOAT);
    public static final BoatType BIRCH = new VanillaBoatType(Taniwha.id("birch"), Boat.Type.BIRCH, Items.BIRCH_BOAT);
    public static final BoatType DARK_OAK = new VanillaBoatType(Taniwha.id("dark_oak"), Boat.Type.DARK_OAK, Items.DARK_OAK_BOAT);
    public static final BoatType JUNGLE = new VanillaBoatType(Taniwha.id("jungle"), Boat.Type.JUNGLE, Items.JUNGLE_BOAT);
    public static final BoatType OAK = new VanillaBoatType(Taniwha.id("oak"), Boat.Type.OAK, Items.OAK_BOAT);
    public static final BoatType SPRUCE = new VanillaBoatType(Taniwha.id("spruce"), Boat.Type.SPRUCE, Items.SPRUCE_BOAT);

    public static BoatType getVanillaType(Boat.Type boatType)
    {
        for(BoatType t : TYPES)
        {
            if(t instanceof VanillaBoatType)
            {
                if(((VanillaBoatType) t).getVanillaType() == boatType) return t;
            }
        }
        return null;
    }

    public static void registerModelLayers()
    {
        for(BoatType type : TYPES)
        {
            EntityModelLayerRegistry.register(new ModelLayerLocation(new ResourceLocation(TConstants.MOD_ID, type.getModelLocation()), "main"), BoatModel::createBodyModel);
        }
    }
}