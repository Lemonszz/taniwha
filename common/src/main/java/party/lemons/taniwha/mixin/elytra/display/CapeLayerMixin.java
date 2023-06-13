package party.lemons.taniwha.mixin.elytra.display;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.util.TaniwhaTags;

@Mixin(CapeLayer.class)
public class CapeLayerMixin
{
	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, AbstractClientPlayer player, float f, float g, float h, float j, float k, float l, CallbackInfo cbi)
	{
		if(player.getItemBySlot(EquipmentSlot.CHEST).is(TaniwhaTags.T_ELYTRA))
			cbi.cancel();
	}
}
