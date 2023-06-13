package party.lemons.taniwha.mixin.elytra.display;

import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.hooks.TClientEvents;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{
	@Inject(at = @At("TAIL"), method = "<init>")
	public void onConstruct(EntityRendererProvider.Context context, boolean bl, CallbackInfo cbi)
	{
		TClientEvents.LAYERS.forEach(l->{
			addLayer((RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) l.addLayer((PlayerRenderer)(Object)this, context.getModelSet()));
		});
	}

	public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f)
	{
		super(context, entityModel, f);
	}
}
