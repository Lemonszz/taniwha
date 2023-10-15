package party.lemons.taniwha.mixin.item.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.item.animation.CustomUseAnimationItem;
import party.lemons.taniwha.item.animation.CustomUseAnimationRegistry;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin
{
    @Shadow public abstract void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i);

    @Shadow @Final private Minecraft minecraft;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"), method = "renderArmWithItem", cancellable = true)
    private void renderArmWithItem(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int delta, CallbackInfo cbi)
    {
        if(!(itemStack.getItem() instanceof CustomUseAnimationItem))
            return;

        CustomUseAnimationRegistry.CustomUseAnimation animation = CustomUseAnimationRegistry.getAnimationRenderer(itemStack.getItem());
        if(animation == null)
            return;

        boolean isMainHand = interactionHand == InteractionHand.MAIN_HAND;

        HumanoidArm humanoidArm = isMainHand ? abstractClientPlayer.getMainArm() : abstractClientPlayer.getMainArm().getOpposite();
        boolean isRight = humanoidArm == HumanoidArm.RIGHT;

        animation.transform(minecraft, poseStack, f, humanoidArm, itemStack);
        this.renderItem(abstractClientPlayer, itemStack, isRight ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !isRight, poseStack, multiBufferSource, delta);

        poseStack.popPose();
        cbi.cancel();
    }
}
