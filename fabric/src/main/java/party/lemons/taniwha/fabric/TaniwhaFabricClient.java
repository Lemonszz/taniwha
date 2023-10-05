package party.lemons.taniwha.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.TaniwhaClient;
import party.lemons.taniwha.client.model.RenderLayerInjector;

public class TaniwhaFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        TaniwhaClient.init();

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            for(RenderLayerInjector.LayerInject inject : RenderLayerInjector.injects)
            {
                RenderLayerInjector.Context ctx = new RenderLayerInjector.Context(entityRenderer, context.getModelSet());

                if(inject.type().equals(entityType))
                {
                    registrationHelper.register(inject.layerFunction().apply(ctx));
                }
            }
        });
    }
}
