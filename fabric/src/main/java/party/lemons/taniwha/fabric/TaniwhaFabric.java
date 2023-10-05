package party.lemons.taniwha.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import party.lemons.taniwha.Taniwha;

public class TaniwhaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Taniwha.init();
    }
}
