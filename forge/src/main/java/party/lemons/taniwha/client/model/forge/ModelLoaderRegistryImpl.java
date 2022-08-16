package party.lemons.taniwha.client.model.forge;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelLoaderRegistryImpl
{
	private static final List<ResourceLocation> additionalModels = Lists.newArrayList();

	public static void loadModel(ModelResourceLocation location)
	{
		additionalModels.add(location);
	}

	@SubscribeEvent
	public static void onModelEvent(ModelEvent.RegisterAdditional event)
	{
		additionalModels.forEach(event::register);
	}
}
