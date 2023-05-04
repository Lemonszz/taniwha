package party.lemons.taniwha.forge;

import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import party.lemons.taniwha.hooks.TEvents;

public class TForgeEvents {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onPlaceEvent(BlockEvent.EntityPlaceEvent event) {
		if (event.getLevel() instanceof Level) {
			System.out.println("yo");

			TEvents.PLACE.invoker().placeBlock((Level) event.getLevel(), event.getPos(), event.getState(), event.getEntity());
		}
	}
}
