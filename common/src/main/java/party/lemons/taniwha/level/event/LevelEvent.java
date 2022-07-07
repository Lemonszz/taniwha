package party.lemons.taniwha.level.event;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import party.lemons.taniwha.network.S2CLevelEvent;

import java.util.function.Consumer;

public class LevelEvent
{
	private final ResourceLocation id;
	private final FriendlyByteBuf buf;
	private final BlockPos position;
	private Consumer<FriendlyByteBuf> dataWriter;

	public LevelEvent(ResourceLocation id, BlockPos position)
	{
		this(id, position, null);
	}

	public LevelEvent(ResourceLocation id, BlockPos position, Consumer<FriendlyByteBuf> dataWriter)
	{
		this.id = id;
		this.position = position;
		buf = new FriendlyByteBuf(Unpooled.buffer());

		buf.writeResourceLocation(id);
		buf.writeBlockPos(position);

		this.dataWriter = dataWriter;
	}

	public LevelEvent(FriendlyByteBuf buf)
	{
		this.id = buf.readResourceLocation();
		this.position = buf.readBlockPos();
		this.buf = buf;
	}

	public void send(ServerLevel level)
	{
		new S2CLevelEvent(this).sendToChunkListeners(level.getChunkAt(position));
	}

	public FriendlyByteBuf getData()
	{
		return buf;
	}

	public ResourceLocation getID()
	{
		return id;
	}

	public BlockPos getPosition()
	{
		return position;
	}

	public void writeAdditional(FriendlyByteBuf buf)
	{
		if(dataWriter != null)
			dataWriter.accept(buf);
	}
}
