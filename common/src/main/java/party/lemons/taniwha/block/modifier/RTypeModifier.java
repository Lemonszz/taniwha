package party.lemons.taniwha.block.modifier;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.block.TBlocks;
import party.lemons.taniwha.block.rtype.RType;

public record RTypeModifier(RType type) implements BlockModifier {
    public static final RTypeModifier CUTOUT = create(RType.CUTOUT);

    public static RTypeModifier create(RType type) {
        return new RTypeModifier(type);
    }

    @Override
    public void accept(Block block) {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () ->
        {
            if (type != null) {
                EnvExecutor.runInEnv(Env.CLIENT, () -> () ->
                        RenderTypeRegistry.register(type.getAsRenderType(), block));
               // TBlocks.RTYPES.put(block, type);
            }
        });
    }
}