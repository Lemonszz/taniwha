package party.lemons.taniwha.mixin.elytra.display;

import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.hooks.TClientEvents;

@Mixin(ArmorStandRenderer.class)
public abstract class ArmorStandRendererMixin extends LivingEntityRenderer<ArmorStand, ArmorStandArmorModel>
{
	@Inject(at = @At("TAIL"), method = "<init>")
	public void onConstruct(EntityRendererProvider.Context context, CallbackInfo cbi)
	{
		TClientEvents.LAYERS.forEach(l->{
			addLayer((RenderLayer<ArmorStand, ArmorStandArmorModel>) l.addLayer((ArmorStandRenderer)(Object)this, context.getModelSet()));
		});
	}

	public ArmorStandRendererMixin(EntityRendererProvider.Context context, ArmorStandArmorModel entityModel, float f)
	{
		super(context, entityModel, f);
	}
}
