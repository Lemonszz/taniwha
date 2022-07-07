package party.lemons.taniwha.network;

import dev.architectury.networking.simple.MessageType;
import dev.architectury.networking.simple.SimpleNetworkManager;
import party.lemons.taniwha.TConstants;

public class TNetwork
{
	public static final SimpleNetworkManager NET = SimpleNetworkManager.create(TConstants.MOD_ID);

	public static final MessageType S2C_LEVEL_EVENT = NET.registerS2C("level_event", S2CLevelEvent::new);

	public static void init()
	{
	}
}
