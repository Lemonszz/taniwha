package party.lemons.taniwha.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.TaniwhaClient;
import party.lemons.taniwha.client.model.RenderLayerInjector;

public class TaniwhaFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TaniwhaClient.init();

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(new LivingEntityFeatureRendererRegistrationCallback() {
            @Override
            public void registerRenderers(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer, RegistrationHelper registrationHelper, EntityRendererProvider.Context context) {
                for(RenderLayerInjector.LayerInject inject : RenderLayerInjector.injects)
                {
                    RenderLayerInjector.Context ctx = new RenderLayerInjector.Context(entityRenderer, context.getModelSet());

                    if(inject.type().equals(entityRenderer))
                    {
                        registrationHelper.register(inject.layerFunction().apply(ctx));
                    }
                }
            }
        });
    }
}
