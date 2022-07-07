package party.lemons.taniwha.level.event;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class LevelEvents
{
	private static final Map<ResourceLocation, EventHandler> handlers = Maps.newHashMap();

	public static ResourceLocation register(ResourceLocation eventID, EventHandler handler)
	{
		handlers.put(eventID, handler);
		return eventID;
	}

	public static void execute(LevelEvent event)
	{
		ResourceLocation location = event.getID();
		BlockPos pos =  event.getPosition();
		handlers.get(location).handle(pos, event.getData());
	}

	public interface EventHandler
	{
		void handle(BlockPos pos, FriendlyByteBuf buf);
	}
}
