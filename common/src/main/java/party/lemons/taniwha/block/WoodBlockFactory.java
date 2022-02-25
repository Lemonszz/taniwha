package party.lemons.taniwha.block;

import com.google.common.collect.Maps;
import dev.architectury.registry.block.BlockProperties;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import party.lemons.taniwha.block.modifier.FlammableModifier;
import party.lemons.taniwha.block.modifier.RTypeModifier;
import party.lemons.taniwha.block.modifier.StrippableModifier;
import party.lemons.taniwha.block.rtype.RType;
import party.lemons.taniwha.block.types.*;
import party.lemons.taniwha.entity.boat.BoatType;
import party.lemons.taniwha.hooks.block.entity.BlockEntityHooks;
import party.lemons.taniwha.hooks.sign.SignTypeHooks;
import party.lemons.taniwha.item.types.TBoatItem;
import party.lemons.taniwha.registry.BlockWithItem;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WoodBlockFactory
{
    private final Map<Type, Block> blocks = Maps.newHashMap();
    private final Map<Type, Item> items = Maps.newHashMap();
    private final WoodType woodType;
    private final String name;
    private final BlockBehaviour.Properties settings;
    private final Consumer<Block> callback;
    private final BlockState defaultState;
    private final String modid;
    private final CreativeModeTab tab;

    public WoodBlockFactory(String modid, CreativeModeTab group, String name, Block.Properties settings)
    {
        this(modid, group, name, settings, null);
    }

    public WoodBlockFactory(String modid, CreativeModeTab group, String name, Block.Properties settings, Consumer<Block> callback)
    {
        this.modid = modid;
        this.name = name;
        this.settings = settings;
        this.callback = callback;
        this.tab = group;

        woodType = SignTypeHooks.register(name);

        set(Type.LOG, new TPillarBlock(settings).modifiers(FlammableModifier.WOOD, new StrippableModifier(()->getBlock(Type.STRIPPED_LOG))));
        set(Type.STRIPPED_LOG, new TPillarBlock(settings).modifiers(FlammableModifier.WOOD));;
        set(Type.PLANK, new TBlock(settings).modifiers(FlammableModifier.WOOD));

        defaultState = getBlock(Type.PLANK).defaultBlockState();
    }

    public WoodBlockFactory slab()
    {
        set(Type.SLAB, new TSlabBlock(settings).modifiers(FlammableModifier.WOOD));
        return this;
    }

    public WoodBlockFactory stair()
    {
        set(Type.STAIR, new TStairBlock(defaultState, settings).modifiers(FlammableModifier.WOOD));
        return this;
    }

    public WoodBlockFactory fence()
    {
        set(Type.FENCE, new TFenceBlock(settings).modifiers(FlammableModifier.WOOD));
        set(Type.FENCE_GATE, new TFenceGateBlock(settings).modifiers(FlammableModifier.WOOD));
        return this;
    }

    public WoodBlockFactory wood()
    {
        set(Type.WOOD, new TPillarBlock(settings).modifiers(FlammableModifier.WOOD, new StrippableModifier(()->getBlock(Type.STRIPPED_WOOD))));
        set(Type.STRIPPED_WOOD, new TPillarBlock(settings).modifiers(FlammableModifier.WOOD));

        return this;
    }

    public WoodBlockFactory pressure_plate()
    {
        set(Type.PRESSURE_PLATE, new TPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockProperties.copy(settings).noCollission()));
        return this;
    }

    public WoodBlockFactory button()
    {
        set(Type.BUTTON, new TButtonBlock(BlockProperties.copy(settings).noCollission()));
        return this;
    }

    public WoodBlockFactory trapdoor()
    {
        set(Type.TRAP_DOOR, new TTrapdoorBlock(BlockProperties.copy(settings).noOcclusion()).modifiers(RTypeModifier.create(RType.CUTOUT)));
        return this;
    }

    public WoodBlockFactory door()
    {
        set(Type.DOOR, new TDoorBlock(BlockProperties.copy(settings).noOcclusion()).modifiers(RTypeModifier.create(RType.CUTOUT)));
        return this;
    }

    public WoodBlockFactory sign()
    {
        SignBlock standing = new StandingSignBlock(BlockProperties.of(Material.WOOD).strength(1F).sound(SoundType.WOOD).noCollission(), woodType);
        WallSignBlock wall = new WallSignBlock(BlockProperties.of(Material.WOOD).strength(1F).sound(SoundType.WOOD).noCollission(), woodType);
        set(Type.SIGN, standing);
        set(Type.SIGN_WALL, wall);
        set(Type.SIGN, new SignItem(properties(), standing, wall));
        BlockEntityHooks.addAdditionalBlock(BlockEntityType.SIGN, standing, wall);

        return this;
    }

    public Item.Properties properties()
    {
        return new Item.Properties().tab(this.tab);
    }

    public WoodBlockFactory boat(Supplier<BoatType> boatType)
    {
        set(Type.BOAT, new TBoatItem(boatType, properties().stacksTo(1)));
        return this;
    }

    public WoodBlockFactory all(Supplier<BoatType> boatType)
    {
        return slab().stair().fence().wood().pressure_plate().button().trapdoor().door().sign().boat(boatType);
    }

    private void set(Type type, Block block)
    {
        this.blocks.put(type, block);
    }

    private void set(Type type, Item item)
    {
        this.items.put(type, item);
    }

    public Block getBlock(Type type)
    {
        return blocks.get(type);
    }

    public Item getItem(Type type)
    {
        return items.get(type);
    }

    public WoodBlockFactory register()
    {
        DeferredRegister<Block> bR = DeferredRegister.create(modid, Registry.BLOCK_REGISTRY);
        DeferredRegister<Item> iR = DeferredRegister.create(modid, Registry.ITEM_REGISTRY);

        for(Type key : blocks.keySet())
        {
            Block bl = blocks.get(key);
            bR.register(key.make(modid, name), ()->bl);

            if(key.hasBlockItem)
                if(bl instanceof BlockWithItem bwi)
                {
                    iR.register(key.make(modid, name), ()->bwi.makeItem(tab));
                }
                else
                {
                    iR.register(key.make(modid, name), ()->new BlockItem(bl, properties()));
                }

            if(callback != null)
            {
                callback.accept(bl);
            }
        }

        for(Type key : items.keySet())
        {
            iR.register(key.make(modid, name), ()->items.get(key));
        }

        bR.register();
        iR.register();
        return this;
    }

    public enum Type
    {
        LOG("", "log", true),
        WOOD("", "wood", true),
        PLANK("", "planks", true),
        STRIPPED_LOG("stripped", "log", true),
        STRIPPED_WOOD("stripped", "wood", true),
        SLAB("", "slab", true),
        STAIR("", "stairs", true),
        FENCE("", "fence", true),
        FENCE_GATE("", "fence_gate", true),
        PRESSURE_PLATE("", "pressure_plate", true),
        BUTTON("", "button", true),
        TRAP_DOOR("", "trapdoor", true),
        DOOR("", "door", true),
        SIGN("", "sign", false),
        SIGN_WALL("", "wall_sign", false),
        BOAT("", "boat", true);

        private final String postfix;
        private final String prefix;
        private final boolean isItem;
        private final boolean hasBlockItem;

        Type(String prefix, String postfix, boolean hasBlockItem)
        {
            this(prefix, postfix, hasBlockItem, false);
        }

        Type(String prefix, String postfix, boolean hasBlockItem, boolean isItem)
        {
            this.postfix = postfix;
            this.prefix = prefix;
            this.isItem = isItem;
            this.hasBlockItem = hasBlockItem;
        }

        public ResourceLocation make(String modid, String name)
        {
            String s = "";
            if(!prefix.isEmpty()) s += prefix + "_";

            s += name;

            if(!postfix.isEmpty()) s += "_" + postfix;

            return new ResourceLocation(modid, s);
        }
    }
}
