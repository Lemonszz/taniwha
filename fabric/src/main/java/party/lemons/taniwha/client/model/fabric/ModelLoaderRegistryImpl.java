package party.lemons.taniwha.client.model.fabric;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ModelLoaderRegistryImpl
{
	public static void loadModel(ModelResourceLocation location)
	{
		ModelLoadingRegistry.INSTANCE.registerModelProvider(((manager, out) -> {
			out.accept(location);
		}));
	}
}
