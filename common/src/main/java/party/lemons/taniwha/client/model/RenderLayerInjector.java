package party.lemons.taniwha.client.model;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RenderLayerInjector
{
    public static final List<LayerInject> injects = new ArrayList<>();

    public static void inject(EntityType<? extends LivingEntity> type, Function<Context, RenderLayer<? extends LivingEntity, ?>> layerFunction)
    {
        injects.add(new LayerInject(type, layerFunction));
    }

    public record LayerInject(EntityType<? extends LivingEntity> type, Function<Context, RenderLayer<? extends LivingEntity, ?>> layerFunction)
    {

    }

    public record Context(LivingEntityRenderer<? extends LivingEntity, ?> entityRenderer, EntityModelSet modelSet)
    {

    }
}
