package party.lemons.taniwha.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import party.lemons.taniwha.block.types.TSlabBlock;
import party.lemons.taniwha.block.types.TStairBlock;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DecorationBlockFactory
{
    public static final List<DecorationBlockFactory> REGISTERED_FACTORIES = Lists.newArrayList();

    private final List<DecorationBlockFactory.Type> types = Lists.newArrayList();
    private final Map<DecorationBlockFactory.Type, Supplier<Block>> blocks = Maps.newHashMap();
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
        types.add(DecorationBlockFactory.Type.SLAB);
        return this;
    }

    public DecorationBlockFactory stair()
    {
        types.add(DecorationBlockFactory.Type.STAIR);
        return this;
    }

    public DecorationBlockFactory wall()
    {
        types.add(DecorationBlockFactory.Type.WALL);
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

    private void set(DecorationBlockFactory.Type type, Supplier<Block> block)
    {
        this.blocks.put(type, block);
    }

    public Supplier<Block> get(DecorationBlockFactory.Type type)
    {
        return blocks.get(type);
    }

    public boolean has(DecorationBlockFactory.Type type)
    {
        return blocks.containsKey(type);
    }

    public Supplier<Block> getBase()
    {
        return base;
    }

    public DecorationBlockFactory register(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister)
    {
        for(DecorationBlockFactory.Type type : types)
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

        for(DecorationBlockFactory.Type key : blocks.keySet())
        {
            Supplier<Block> bl = blocks.get(key);

            ResourceLocation id = key.make(this.modid, name);

            RegistrySupplier<Block> regBlock = blockRegister.register(id, bl);
            itemRegister.register(id, ()->new BlockItem(regBlock.get(), blockItemProperties.get()));

            if(callback != null)
            {
                callback.accept(bl);
            }
        }

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