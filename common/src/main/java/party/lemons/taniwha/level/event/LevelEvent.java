package party.lemons.taniwha.level.event;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import party.lemons.taniwha.network.S2CLevelEvent;

public class LevelEvent
{
	private final ResourceLocation id;
	private final FriendlyByteBuf buf;
	private final BlockPos position;

	public LevelEvent(ResourceLocation id, BlockPos position)
	{
		this.id = id;
		this.position = position;
		buf = new FriendlyByteBuf(Unpooled.buffer());

		buf.writeResourceLocation(id);
		buf.writeBlockPos(position);
	}

	public LevelEvent(FriendlyByteBuf buf)
	{
		this.id = buf.readResourceLocation();
		this.position = buf.readBlockPos();
		this.buf = buf;
	}

	public LevelEvent data(ByteBuf buff)
	{
		buf.writeBytes(buff);

		return this;
	}

	public void send(ServerLevel level)
	{
		new S2CLevelEvent(this).sendToChunkListeners(level.getChunkAt(position));
	}

	public FriendlyByteBuf getData()
	{
		return buf;
	}
}
