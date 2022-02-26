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
import party.lemons.taniwha.block.types.TStairBlock;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DecorationBlockFactory
{
    private final Map<Type, Block> blocks = Maps.newHashMap();
    private final String name;
    private final BlockBehaviour.Properties settings;
    private final Block base;
    private final Consumer<Block> callback;
    private final String modid;
    private final CreativeModeTab tab;
    protected Supplier<Item.Properties> blockItemProperties;


    public DecorationBlockFactory(String modid, CreativeModeTab tab, String name, Block baseBlock, Block.Properties settings)
    {
        this(modid, tab, name, baseBlock, settings, null);
    }

    public DecorationBlockFactory(String modid, CreativeModeTab tab, String name, Block baseBlock, Block.Properties settings, Consumer<Block> callback)
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
        set(Type.SLAB, new SlabBlock(settings));
        return this;
    }

    public DecorationBlockFactory stair()
    {
        set(Type.STAIR, new TStairBlock(base.defaultBlockState(), settings));
        return this;
    }

    public DecorationBlockFactory wall()
    {
        set(Type.WALL, new WallBlock(settings));
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

    private void set(Type type, Block block)
    {
        this.blocks.put(type, block);
    }

    public Block get(Type type)
    {
        return blocks.get(type);
    }

    public DecorationBlockFactory register()
    {
        DeferredRegister<Block> bR = DeferredRegister.create(this.modid, Registry.BLOCK_REGISTRY);
        DeferredRegister<Item> iR = DeferredRegister.create(this.modid, Registry.ITEM_REGISTRY);

        for(Type key : blocks.keySet())
        {
            Block bl = blocks.get(key);
            bR.register(key.make(this.modid, name), ()->bl);
            iR.register(key.make(this.modid, name), ()->new BlockItem(bl, blockItemProperties.get()));

            if(callback != null)
            {
                callback.accept(bl);
            }
        }
        bR.register();
        iR.register();
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
