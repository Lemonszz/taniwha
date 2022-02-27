package party.lemons.taniwha.mixin.sign;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.hooks.sign.SignTypeHooks;

import java.util.Set;

@Mixin(WoodType.class)
public class WoodTypeMixin implements SignTypeHooks {

    @Shadow @Final
    private static Set<WoodType> VALUES;

    @Override
    public Set<WoodType> bm_getTypes() {
        return VALUES;
    }
}
