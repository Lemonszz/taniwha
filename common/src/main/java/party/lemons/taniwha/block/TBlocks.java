package party.lemons.taniwha.block;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.item.TItems;
import party.lemons.taniwha.item.modifier.ItemModifier;
import party.lemons.taniwha.item.modifier.ItemWithModifiers;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TBlocks {

    public static final Multimap<Block, BlockModifier> MODIFIERS = ArrayListMultimap.create();

    public static final Consumer<Block> MODIFIER_CONSUMER = (b)->{
        if(b instanceof BlockWithModifiers<?>)
        {
            Collection<BlockModifier> mods = MODIFIERS.get(b);
            mods.forEach(m->m.accept(b));
        }
    };


    public static void initClient()
    {
    }

    public static RegistrySupplier<Block> registerBlock(DeferredRegister<Block> register, ResourceLocation location, Supplier<Block> itemSupplier)
    {
        RegistrySupplier<Block> registered = register.register(location, itemSupplier);
        registered.listen(MODIFIER_CONSUMER);

        return registered;
    }

    public static TagKey<Block> GOLEM_HEADS = TagKey.create(Registry.BLOCK_REGISTRY, Taniwha.id("golem_heads"));
}
