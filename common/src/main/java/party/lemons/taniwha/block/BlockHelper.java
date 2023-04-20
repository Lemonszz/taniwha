package party.lemons.taniwha.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.block.types.TBlock;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockHelper {
    public static final Consumer<Block> POST_REGISTER = (b)->
    {
        if(b instanceof BlockWithModifiers modifierBlock)
        {
            modifierBlock.initModifiers();
        }

        if(b instanceof TBlock tBlock)
        {
            tBlock.onRegister();
        }
    };

    public static RegistrySupplier<Block> registerBlock(DeferredRegister<Block> register, ResourceLocation location, Supplier<Block> blockSupplier)
    {
        RegistrySupplier<Block> registered = register.register(location, blockSupplier);
        registered.listen(POST_REGISTER);

        return registered;
    }
}
