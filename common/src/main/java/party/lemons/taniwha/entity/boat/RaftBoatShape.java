package party.lemons.taniwha.entity.boat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import party.lemons.taniwha.TConstants;

import java.util.function.Supplier;

public class RaftBoatShape extends BoatShape
{
	@Override
	public double getPassengersRidingOffset()
	{
		return 0.25F;
	}

	@Override
	public Supplier<LayerDefinition> getLayerDefinition()
	{
		return RaftModel::createBodyModel;
	}

	@Override
	public Supplier<LayerDefinition> getChestLayerDefinition()
	{
		return ChestRaftModel::createBodyModel;
	}

	@Environment(EnvType.CLIENT)
	public ListModel<Boat> createModel(EntityRendererProvider.Context context, BoatType type, boolean chest)
	{
		if(chest)
			return new ChestRaftModel(context.bakeLayer(new ModelLayerLocation(new ResourceLocation(TConstants.MOD_ID, type.getChestModelLocation()), "main")));
		else
			return new RaftModel(context.bakeLayer(new ModelLayerLocation(new ResourceLocation(TConstants.MOD_ID, type.getModelLocation()), "main")));
	}
}
