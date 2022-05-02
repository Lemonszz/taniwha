package party.lemons.taniwha.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockHelper {
    public static final Consumer<Block> MODIFIER_CONSUMER = (b)->{
        if(b instanceof BlockWithModifiers modifierBlock)
        {
            modifierBlock.initModifiers();
        }
    };

    public static RegistrySupplier<Block> registerBlock(DeferredRegister<Block> register, ResourceLocation location, Supplier<Block> blockSupplier)
    {
        RegistrySupplier<Block> registered = register.register(location, blockSupplier);
        registered.listen(MODIFIER_CONSUMER);

        return registered;
    }
}
