package party.lemons.taniwha.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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

        if(b instanceof TBlockExtension tBlock)
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

    public static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }
}
