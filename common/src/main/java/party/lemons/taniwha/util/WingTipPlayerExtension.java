package party.lemons.taniwha.util;

import net.minecraft.client.player.AbstractClientPlayer;

public interface WingTipPlayerExtension
{
	float wingTipRotX();
	float wingTipRotY();
	float wingTipRotZ();

	void setWingTipRotX(float val);
	void setWingTipRotY(float val);
	void setWingTipRotZ(float val);

	static float getWingTipX(AbstractClientPlayer player)
	{
		return ((WingTipPlayerExtension)player).wingTipRotX();
	}

	static float getWingTipY(AbstractClientPlayer player)
	{
		return ((WingTipPlayerExtension)player).wingTipRotY();
	}

	static float getWingTipZ(AbstractClientPlayer player)
	{
		return ((WingTipPlayerExtension)player).wingTipRotZ();
	}

	static void setWingTip(AbstractClientPlayer player, float x, float y, float z)
	{
		((WingTipPlayerExtension)player).setWingTipRotX(x);
		((WingTipPlayerExtension)player).setWingTipRotY(y);
		((WingTipPlayerExtension)player).setWingTipRotZ(z);
	}

	static void addWingTip(AbstractClientPlayer player, float x, float y, float z)
	{
		((WingTipPlayerExtension)player).setWingTipRotX(getWingTipX(player) + x);
		((WingTipPlayerExtension)player).setWingTipRotY(getWingTipY(player) + y);
		((WingTipPlayerExtension)player).setWingTipRotZ(getWingTipZ(player) + z);
	}

	static void setWingTipX(AbstractClientPlayer player, float x)
	{
		((WingTipPlayerExtension)player).setWingTipRotX(x);
	}

	static void setWingTipY(AbstractClientPlayer player, float y)
	{
		((WingTipPlayerExtension)player).setWingTipRotY(y);
	}

	static void setWingTipZ(AbstractClientPlayer player, float z)
	{
		((WingTipPlayerExtension)player).setWingTipRotZ(z);
	}
}
