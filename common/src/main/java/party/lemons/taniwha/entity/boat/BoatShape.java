package party.lemons.taniwha.entity.boat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import party.lemons.taniwha.TConstants;

import java.util.function.Supplier;

public class BoatShape
{
	public double getPassengersRidingOffset(){
		return -0.1;
	}

	@Environment(EnvType.CLIENT)
	public Supplier<LayerDefinition> getLayerDefinition()
	{
		return BoatModel::createBodyModel;
	}

	@Environment(EnvType.CLIENT)
	public Supplier<LayerDefinition> getChestLayerDefinition()
	{
		return ChestBoatModel::createBodyModel;
	}

	@Environment(EnvType.CLIENT)
	public ListModel<Boat> createModel(EntityRendererProvider.Context context, BoatType type, boolean chest)
	{
		if(chest)
			return new ChestBoatModel(context.bakeLayer(new ModelLayerLocation(new ResourceLocation(TConstants.MOD_ID, type.getChestModelLocation()), "main")));
		else
			return new BoatModel(context.bakeLayer(new ModelLayerLocation(new ResourceLocation(TConstants.MOD_ID, type.getModelLocation()), "main")));
	}
}
