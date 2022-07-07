package party.lemons.taniwha.level.event;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import party.lemons.taniwha.network.S2CLevelEvent;

import java.util.function.Consumer;

public class LevelEvent
{
	private final ResourceLocation id;
	private FriendlyByteBuf buf;
	private final BlockPos position;
	private Consumer<FriendlyByteBuf> dataWriter;

	//TODO:
	/*
			users probably shouldnt need to touch this, all cases could be covered though a static method in LevelEvents
			This could also autosend it, instead of requiring .send to be called
	 */

	public LevelEvent(ResourceLocation id, BlockPos position)
	{
		this(id, position, null);
	}

	public LevelEvent(ResourceLocation id, BlockPos position, Consumer<FriendlyByteBuf> dataWriter)
	{
		this.id = id;
		this.position = position;
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
