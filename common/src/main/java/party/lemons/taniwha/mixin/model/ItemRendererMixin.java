package party.lemons.taniwha.mixin.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.client.model.AlternateInventoryModelRegistry;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin
{
	@Shadow
	@Final
	private ItemModelShaper itemModelShaper;

	@Unique
	private boolean doRender = true;

	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	public void render(ItemStack itemStack, ItemDisplayContext transformType, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, BakedModel bakedModel, CallbackInfo cbi)
	{
		if(doRender && AlternateInventoryModelRegistry.hasAlternateModel(itemStack.getItem()))
		{
			if(transformType == ItemDisplayContext.GUI || transformType == ItemDisplayContext.GROUND || transformType == ItemDisplayContext.FIXED)
			{
				doRender = false;
				((ItemRenderer)(Object)this).render(itemStack, transformType, bl, poseStack, multiBufferSource, i, j, this.itemModelShaper.getModelManager().getModel(AlternateInventoryModelRegistry.getAlternateModel(itemStack.getItem())));
				doRender = true;
				cbi.cancel();
			}
		}

	}
}
