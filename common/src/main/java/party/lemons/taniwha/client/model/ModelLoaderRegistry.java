package party.lemons.taniwha.client.model;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ModelLoaderRegistry
{
	@ExpectPlatform
	public static void loadModel(ModelResourceLocation location)
	{
		throw new AssertionError();
	}
}
