package party.lemons.taniwha.block;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.rtype.RType;

import java.util.List;
import java.util.Map;

public class TBlocks {

    public static final Multimap<Block, BlockModifier> MODIFIERS = ArrayListMultimap.create();
    public static final Map<Block, RType> RTYPES = Maps.newHashMap();

    public static void initModifiers()
    {
        MODIFIERS.forEach((b, a)->a.accept(b));
    }

    public static void initClient()
    {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () ->
                RTYPES.forEach((block, type) -> RenderTypeRegistry.register(type.getAsRenderType(), block)));
    }

    public static TagKey<Block> GOLEM_HEADS = TagKey.create(Registry.BLOCK_REGISTRY, Taniwha.id("golem_heads"));
}
