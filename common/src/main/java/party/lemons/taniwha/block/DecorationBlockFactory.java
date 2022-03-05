package party.lemons.taniwha.block;

import com.google.common.collect.Maps;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.apache.commons.compress.utils.Lists;
import party.lemons.taniwha.block.types.TSlabBlock;
import party.lemons.taniwha.block.types.TStairBlock;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DecorationBlockFactory
{
    public static final List<DecorationBlockFactory> REGISTERED_FACTORIES = Lists.newArrayList();

    private final List<Type> types = Lists.newArrayList();
    private final Map<Type, Supplier<Block>> blocks = Maps.newHashMap();
    private final String name;
    private final BlockBehaviour.Properties settings;
    private final Supplier<Block> base;
    private final Consumer<Supplier<Block>> callback;
    private final String modid;
    private final CreativeModeTab tab;
    protected Supplier<Item.Properties> blockItemProperties;

    public DecorationBlockFactory(String modid, CreativeModeTab tab, String name, Supplier<Block> baseBlock, Block.Properties settings)
    {
        this(modid, tab, name, baseBlock, settings, null);
    }

    public DecorationBlockFactory(String modid, CreativeModeTab tab, String name, Supplier<Block> baseBlock, Block.Properties settings, Consumer<Supplier<Block>> callback)
    {
        this.modid = modid;
        this.name = name;
        this.settings = settings;
        this.base = baseBlock;
        this.tab = tab;
        this.callback = callback;

        this.blockItemProperties = ()->new Item.Properties().tab(this.tab);
    }

    public DecorationBlockFactory slab()
    {
        types.add(Type.SLAB);
        return this;
    }

    public DecorationBlockFactory stair()
    {
        types.add(Type.STAIR);
        return this;
    }

    public DecorationBlockFactory wall()
    {
        types.add(Type.WALL);
        return this;
    }

    public DecorationBlockFactory all()
    {
        return slab().stair().wall();
    }

    public DecorationBlockFactory blocKItemProperties(Supplier<Item.Properties> properties)
    {
        this.blockItemProperties = properties;
        return this;
    }

    private void set(Type type, Supplier<Block> block)
    {
        this.blocks.put(type, block);
    }

    public Supplier<Block> get(Type type)
    {
        return blocks.get(type);
    }

    public boolean has(Type type)
    {
        return blocks.containsKey(type);
    }

    public Supplier<Block> getBase()
    {
        return base;
    }

    public DecorationBlockFactory register()
    {
        DeferredRegister<Block> bR = DeferredRegister.create(this.modid, Registry.BLOCK_REGISTRY);
        DeferredRegister<Item> iR = DeferredRegister.create(this.modid, Registry.ITEM_REGISTRY);

        for(Type type : types)
        {
            switch (type)
            {
                case SLAB -> {
                    set(type, ()->new TSlabBlock(this.settings));
                }
                case STAIR -> {
                    set(type, ()->new TStairBlock(base.get().defaultBlockState(), this.settings));
                }
                case WALL -> {
                    set(type, ()->new WallBlock(this.settings));
                }
            }
        }

        for(Type key : blocks.keySet())
        {
            Supplier<Block> bl = blocks.get(key);
            bR.register(key.make(this.modid, name), bl);
            iR.register(key.make(this.modid, name), ()->new BlockItem(bl.get(), blockItemProperties.get()));

            if(callback != null)
            {
                callback.accept(bl);
            }
        }
        bR.register();
        iR.register();

        REGISTERED_FACTORIES.add(this);
        return this;
    }
    public enum Type
    {
        SLAB("slab"), STAIR("stairs"), WALL("wall");

        private final String postfix;

        Type(String postfix)
        {
            this.postfix = postfix;
        }

        public ResourceLocation make(String modid, String name)
        {
            return new ResourceLocation(modid, name + "_" + postfix);
        }
    }
}
