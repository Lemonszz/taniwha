package party.lemons.taniwha.forge;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.TaniwhaClient;
import party.lemons.taniwha.client.model.RenderLayerInjector;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = TConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TForgeClientEvents
{
    public static void initClient()
    {
        TaniwhaClient.init();
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event)
    {
        for(RenderLayerInjector.LayerInject inject : RenderLayerInjector.injects)
        {
            LivingEntityRenderer renderer = event.getRenderer(inject.type());

            renderer.addLayer(inject.layerFunction().apply(new RenderLayerInjector.Context(renderer, event.getEntityModels())));
        }
    }
}
