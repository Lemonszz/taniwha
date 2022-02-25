package party.lemons.taniwha.block;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.CompostModifier;
import party.lemons.taniwha.block.rtype.RType;
import party.lemons.taniwha.util.collections.WeightedList;

import java.util.List;
import java.util.Map;

public class TBlocks {

    public static final Multimap<Block, BlockModifier> MODIFIERS = ArrayListMultimap.create();
    public static final List<CompostModifier.CompostValue> COMPOSTABLES = Lists.newArrayList();
    public static final Map<Block, RType> RTYPES = Maps.newHashMap();

    public static void initModifiers()
    {
        MODIFIERS.forEach((b, a)->a.accept(b));
    }

    public static void initCompostables()
    {
        COMPOSTABLES.forEach((p)-> ComposterBlock.COMPOSTABLES.put(p.block(), p.chance()));
    }

    public static void initClient()
    {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () ->
                RTYPES.forEach((block, type) -> RenderTypeRegistry.register(type.getAsRenderType(), block)));
    }
}
