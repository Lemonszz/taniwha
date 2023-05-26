package party.lemons.taniwha.entity.boat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;

import java.util.List;
import java.util.Map;

public class BoatTypes
{
    public static List<BoatType> TYPES = Lists.newArrayList();
    public static Map<ResourceLocation, BoatType> TYPES_MAP = Maps.newHashMap();

    public static final BoatShape REGULAR_SHAPE = new BoatShape();
    public static final BoatShape RAFT_SHAPE = new RaftBoatShape();

    public static final BoatType ACACIA = new VanillaBoatType(Taniwha.id("acacia"), REGULAR_SHAPE, Boat.Type.ACACIA, Items.ACACIA_BOAT, Items.ACACIA_CHEST_BOAT);
    public static final BoatType BIRCH = new VanillaBoatType(Taniwha.id("birch"), REGULAR_SHAPE, Boat.Type.BIRCH, Items.BIRCH_BOAT, Items.BIRCH_CHEST_BOAT);
    public static final BoatType DARK_OAK = new VanillaBoatType(Taniwha.id("dark_oak"), REGULAR_SHAPE, Boat.Type.DARK_OAK, Items.DARK_OAK_BOAT, Items.DARK_OAK_CHEST_BOAT);
    public static final BoatType JUNGLE = new VanillaBoatType(Taniwha.id("jungle"), REGULAR_SHAPE, Boat.Type.JUNGLE, Items.JUNGLE_BOAT, Items.JUNGLE_CHEST_BOAT);
    public static final BoatType OAK = new VanillaBoatType(Taniwha.id("oak"), REGULAR_SHAPE, Boat.Type.OAK, Items.OAK_BOAT, Items.OAK_CHEST_BOAT);
    public static final BoatType SPRUCE = new VanillaBoatType(Taniwha.id("spruce"), REGULAR_SHAPE, Boat.Type.SPRUCE, Items.SPRUCE_BOAT, Items.SPRUCE_CHEST_BOAT);
    public static final BoatType MANGROVE = new VanillaBoatType(Taniwha.id("mangrove"), REGULAR_SHAPE, Boat.Type.MANGROVE, Items.MANGROVE_BOAT, Items.MANGROVE_CHEST_BOAT);
    public static final BoatType CHERRY = new VanillaBoatType(Taniwha.id("cherry"), REGULAR_SHAPE,  Boat.Type.CHERRY, Items.CHERRY_BOAT, Items.CHERRY_CHEST_BOAT);
    public static final BoatType BAMBOO = new VanillaBoatType(Taniwha.id("bamboo"), RAFT_SHAPE,  Boat.Type.BAMBOO, Items.BAMBOO_RAFT, Items.BAMBOO_CHEST_RAFT);

    public static void init(){
        //nofu
    }

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
            EntityModelLayerRegistry.register(new ModelLayerLocation(new ResourceLocation(TConstants.MOD_ID, type.getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
        }
    }
}