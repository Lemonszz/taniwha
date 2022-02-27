package party.lemons.taniwha.mixin.client.disableexperimentalwarning;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.Taniwha;

@Mixin(ConfirmScreen.class)
public class ConfirmScreenMixin {

    @Inject(at = @At("RETURN"), method = "<init>(Lit/unimi/dsi/fastutil/booleans/BooleanConsumer;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V")
    public void onConstruct(BooleanConsumer booleanConsumer, Component arg, Component arg2, Component arg3, Component arg4, CallbackInfo cbi)
    {
        if(Taniwha.CONFIG.disableExperimentalWorldWarning && arg instanceof TranslatableComponent trans && trans.getKey().equals("selectWorld.backupQuestion.experimental"))
        {
            booleanConsumer.accept(true);
        }
    }
}
