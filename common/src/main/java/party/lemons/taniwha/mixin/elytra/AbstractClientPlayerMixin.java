package party.lemons.taniwha.mixin.elytra;

import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import party.lemons.taniwha.util.WingTipPlayerExtension;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin implements WingTipPlayerExtension
{
	private float rotX, rotY, rotZ;

	@Override
	public float wingTipRotX()
	{
		return rotX;
	}

	@Override
	public float wingTipRotY()
	{
		return rotY;
	}

	@Override
	public float wingTipRotZ()
	{
		return rotZ;
	}

	@Override
	public void setWingTipRotX(float val)
	{
		this.rotX = val;
	}

	@Override
	public void setWingTipRotY(float val)
	{
		this.rotY = val;
	}

	@Override
	public void setWingTipRotZ(float val)
	{
		this.rotZ = val;
	}
}
