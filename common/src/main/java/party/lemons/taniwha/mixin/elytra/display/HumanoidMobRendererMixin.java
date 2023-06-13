package party.lemons.taniwha.mixin.elytra.display;

import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.hooks.TClientEvents;

@Mixin(HumanoidMobRenderer.class)
public abstract class HumanoidMobRendererMixin<T extends Mob, M extends HumanoidModel<T>> extends MobRenderer<T, M>
{
	public HumanoidMobRendererMixin(EntityRendererProvider.Context context, M entityModel, float f)
	{
		super(context, entityModel, f);
	}

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/client/model/HumanoidModel;FFFF)V")
	public void onConstruct(EntityRendererProvider.Context context, M humanoidModel, float f, float g, float h, float i, CallbackInfo cbi)
	{
		TClientEvents.LAYERS.forEach(l->{
			addLayer((RenderLayer<T, M>) l.addLayer((HumanoidMobRenderer)(Object)this, context.getModelSet()));
		});
	}
}