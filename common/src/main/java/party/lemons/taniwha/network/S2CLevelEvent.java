package party.lemons.taniwha.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.network.FriendlyByteBuf;
import party.lemons.taniwha.level.event.LevelEvent;
import party.lemons.taniwha.level.event.LevelEvents;

public class S2CLevelEvent extends BaseS2CMessage
{
	private final LevelEvent event;

	public S2CLevelEvent(FriendlyByteBuf buf)
	{
		event = new LevelEvent(buf);
	}

	public S2CLevelEvent(LevelEvent event)
	{
		this.event = event;
	}

	@Override
	public MessageType getType()
	{
		return TNetwork.S2C_LEVEL_EVENT;
	}

	@Override
	public void write(FriendlyByteBuf buf)
	{
		buf.writeBytes(event.getData());
	}

	@Override
	public void handle(NetworkManager.PacketContext context)
	{
		context.queue(new Runnable()
		{
			@Override
			public void run()
			{
				LevelEvents.execute(event);
			}
		});
	}
}
