package party.lemons.taniwha.hooks;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface TClientEvents
{
	List<AddHumanoidLayer> LAYERS = Lists.newArrayList();

	interface AddHumanoidLayer{
		RenderLayer<?, ?> addLayer(RenderLayerParent<?, ?> parent, EntityModelSet modelset);
	}
}
