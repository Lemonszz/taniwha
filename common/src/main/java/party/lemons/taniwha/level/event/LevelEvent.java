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

	public LevelEvent write(int val)
	{
		buf.writeInt(val);
		return this;
	}

	public LevelEvent write(boolean val)
	{
		buf.writeBoolean(val);

		return this;
	}

	public LevelEvent write(float val)
	{
		buf.writeFloat(val);

		return this;
	}

	public LevelEvent write(ItemStack val)
	{
		buf.writeItem(val);

		return this;
	}

	public LevelEvent write(BlockPos val)
	{
		buf.writeBlockPos(val);

		return this;
	}

	public LevelEvent write(ResourceLocation val)
	{
		buf.writeResourceLocation(val);

		return this;
	}

	public LevelEvent write(String val)
	{
		buf.writeUtf(val);

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

	public ResourceLocation getID()
	{
		return id;
	}

	public BlockPos getPosition()
	{
		return position;
	}
}
