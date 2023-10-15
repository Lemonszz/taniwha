package party.lemons.taniwha.item.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public final class CustomUseAnimationRegistry
{
    private static final Map<Item, CustomUseAnimation> ANIMATIONS = new HashMap<>();

    @Nullable
    public static CustomUseAnimationRegistry.CustomUseAnimation getAnimationRenderer(Item item)
    {
        return ANIMATIONS.get(item);
    }

    public static void register(Item item, CustomUseAnimation renderer)
    {
        ANIMATIONS.put(item, renderer);
    }

    public static void register(RegistrySupplier<Item> itemSupplier, CustomUseAnimation renderer)
    {
        itemSupplier.listen((i)-> register(i, renderer));
    }

    public static class CustomUseAnimation
    {
        public void transform(Minecraft minecraft, PoseStack poseStack, float f, float swing, HumanoidArm humanoidArm, ItemStack itemStack)
        {

        }

        protected void applyItemArmTransform(PoseStack pose, HumanoidArm arm, float swing) {
            int xDirection = arm == HumanoidArm.RIGHT ? 1 : -1;
            pose.translate((float)xDirection * 0.56F, -0.52F + swing * -0.6F, -0.72F);
        }
    }


}
