package party.lemons.taniwha.hooks;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface TEvents
{
	Event<Place> PLACE = EventFactory.createLoop();

	interface Place {
		void placeBlock(Level level, BlockPos pos, BlockState state, @Nullable Entity placer);
	}
}
