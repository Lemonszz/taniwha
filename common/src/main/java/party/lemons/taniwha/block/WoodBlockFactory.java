package party.lemons.taniwha.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import party.lemons.taniwha.block.modifier.FlammableModifier;
import party.lemons.taniwha.block.modifier.RTypeModifier;
import party.lemons.taniwha.block.modifier.StrippableModifier;
import party.lemons.taniwha.block.rtype.RType;
import party.lemons.taniwha.block.types.*;
import party.lemons.taniwha.entity.boat.BoatType;
import party.lemons.taniwha.hooks.block.entity.BlockEntityHooks;
import party.lemons.taniwha.item.ItemHelper;
import party.lemons.taniwha.item.types.TBoatItem;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WoodBlockFactory
{
    private final List<WoodBlockFactory.Type> types = Lists.newArrayList();
    private final List<WoodBlockFactory.Type> itemTypes = Lists.newArrayList();

    private final Map<Type, RegistrySupplier<Block>> blocks = Maps.newHashMap();
    private final Map<Type, RegistrySupplier<Item>> items = Maps.newHashMap();
    private final WoodType woodType;
    private final String name;
    private final BlockBehaviour.Properties properties;
    private final Consumer<Supplier<Block>> callback;
    private final String modid;
    private Supplier<BoatType> boatType;
    private final CreativeTabRegistry.TabSupplier tabSupplier;

    public WoodBlockFactory(String modid, String name, CreativeTabRegistry.TabSupplier tabSupplier)
    {
        this(modid, name, new BlockSetType(name), BlockBehaviour.Properties.of(Material.WOOD).strength(2F, 3F).sound(SoundType.WOOD), tabSupplier, null);
    }


    public WoodBlockFactory(String modid, String name, BlockSetType setType, CreativeTabRegistry.TabSupplier tabSupplier)
    {
        this(modid, name, setType, BlockBehaviour.Properties.of(Material.WOOD).strength(2F, 3F).sound(SoundType.WOOD), tabSupplier, null);
    }

    public WoodBlockFactory(String modid, String name, BlockSetType setType, Block.Properties settings, CreativeTabRegistry.TabSupplier tabSupplier, Consumer<Supplier<Block>> callback)
    {
        this.modid = modid;
        this.name = name;
        this.properties = settings;
        this.callback = callback;
        this.tabSupplier = tabSupplier;

        woodType = new WoodType(name, setType);
        types.add(Type.LOG);
        types.add(Type.STRIPPED_LOG);
        types.add(Type.PLANK);
    }

    public WoodBlockFactory slab()
    {
        types.add(Type.SLAB);
        return this;
    }

    public WoodBlockFactory stair()
    {
        types.add(Type.STAIR);
        return this;
    }

    public WoodBlockFactory fence()
    {
        types.add(Type.FENCE);
        types.add(Type.FENCE_GATE);

        return this;
    }

    public WoodBlockFactory wood()
    {
        types.add(Type.WOOD);
        types.add(Type.STRIPPED_WOOD);

        return this;
    }

    public WoodBlockFactory pressure_plate()
    {
        types.add(Type.PRESSURE_PLATE);
        return this;
    }

    public WoodBlockFactory button()
    {
        types.add(Type.BUTTON);
        return this;
    }

    public WoodBlockFactory trapdoor()
    {
        types.add(Type.TRAP_DOOR);
        return this;
    }

    public WoodBlockFactory door()
    {
        types.add(Type.DOOR);
        return this;
    }

    public WoodBlockFactory sign()
    {
        types.add(Type.SIGN);
        types.add(Type.SIGN_WALL);

        itemTypes.add(Type.SIGN_ITEM);

        return this;
    }

    public WoodBlockFactory boat(Supplier<BoatType> boatType)
    {
        this.boatType = boatType;
        itemTypes.add(Type.BOAT);
        itemTypes.add(Type.CHEST_BOAT);
        return this;
    }

    public Item.Properties properties()
    {
        return new Item.Properties().arch$tab(tabSupplier);
    }

    public WoodBlockFactory all(Supplier<BoatType> boatType)
    {
        return slab().stair().fence().wood().pressure_plate().button().trapdoor().door().sign().boat(boatType);
    }

    private void set(Type type, RegistrySupplier<Block> block)
    {
        this.blocks.put(type, block);
    }

    private void setItem(Type type, RegistrySupplier<Item> item)
    {
        this.items.put(type, item);
    }

    public Supplier<Block> getBlock(Type type)
    {
        return blocks.get(type);
    }

    public Supplier<Item> getItem(Type type)
    {
        return items.get(type);
    }

    public WoodBlockFactory register(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister)
    {
        for(Type type : types) {
            Supplier<Block> blockSupplier = type.blockSupplier.getSupplier(this);

            if (blockSupplier != null) {
                ResourceLocation id = type.make(this.modid, name);

                RegistrySupplier<Block> regBlock = party.lemons.taniwha.block.BlockHelper.registerBlock(blockRegister, id, blockSupplier);
                set(type, regBlock);

                if (type.hasBlockItem) {
                    ItemHelper.registerItem(itemRegister, id, ()->new BlockItem(regBlock.get(), properties()));
                }

                if (callback != null) {
                    callback.accept(regBlock);
                }
            }
        }
        for(Type type : itemTypes)
        {
            Supplier<Item> itemSupplier = type.itemSupplier.getSupplier(this);

            if(itemSupplier != null) {
                RegistrySupplier<Item> item = itemRegister.register(type.make(modid, name), itemSupplier);
                setItem(type, item);
            }
        }

        if(types.contains(Type.SIGN)) {
            Consumer<Block> beConsumer = (b)->{
                BlockEntityHooks.addAdditionalBlock(BlockEntityType.SIGN, b);
            };

            blocks.get(Type.SIGN).listen(beConsumer);
            blocks.get(Type.SIGN_WALL).listen(beConsumer);
        }
        return this;
    }

    @FunctionalInterface
    private interface TypeBlockSupplier<T>
    {
        public Supplier<T> getSupplier(WoodBlockFactory factory);
    }

    private static BlockBehaviour.Properties props(BlockBehaviour.Properties props)
    {
        BlockBehaviour be = new BlockBehaviour(props)
        {
            @Override
            public Item asItem()
            {
                return null;
            }

            @Override
            protected Block asBlock()
            {
                return null;
            }
        };

        return BlockBehaviour.Properties.copy(be);
    }

    public enum Type
    {
        STRIPPED_WOOD("stripped", "wood", true, (f)->()->new TPillarBlock(props(f.properties).explosionResistance(2.0F)).modifiers(FlammableModifier.WOOD)),
        STRIPPED_LOG("stripped", "log", true, (f)->()->new TPillarBlock(props(f.properties).explosionResistance(2.0F)).modifiers(FlammableModifier.WOOD)),
        PLANK("", "planks", true, (f)->()->new TBlock(props(f.properties)).modifiers(FlammableModifier.WOOD)),
        LOG("", "log", true, (f)->()->new TPillarBlock(props(f.properties).explosionResistance(2.0F)).modifiers(FlammableModifier.WOOD, new StrippableModifier(()->f.getBlock(Type.STRIPPED_LOG).get()))),
        WOOD("", "wood", true, (f)->()->new TPillarBlock(props(f.properties).explosionResistance(2.0F)).modifiers(FlammableModifier.WOOD, new StrippableModifier(()->f.getBlock(Type.STRIPPED_WOOD).get()))),
        SLAB("", "slab", true, (f)->()->new TSlabBlock(props(f.properties)).modifiers(FlammableModifier.WOOD)),
        STAIR("", "stairs", true, (f)->()->new TStairBlock(f.getBlock(Type.PLANK).get().defaultBlockState(), props(f.properties)).modifiers(FlammableModifier.WOOD)),
        FENCE("", "fence", true, (f)->()->new TFenceBlock(props(f.properties)).modifiers(FlammableModifier.WOOD)),
        FENCE_GATE("", "fence_gate", true, (f)->()->new TFenceGateBlock(props(f.properties), f.woodType).modifiers(FlammableModifier.WOOD)),
        PRESSURE_PLATE("", "pressure_plate", true, (f)->()->new TWoodenPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, f.woodType.setType(), props(f.properties).strength(0.5F).noCollission())),
        BUTTON("", "button", true, (f)-> ()->new TButtonBlock(props(f.properties).strength(0.5F).noCollission(), f.woodType.setType(), 30 ,true)),
        TRAP_DOOR("", "trapdoor", true, (f)->()->new TTrapdoorBlock(props(f.properties).strength(3F).noOcclusion(), f.woodType.setType()).modifiers(RTypeModifier.create(RType.CUTOUT))),
        DOOR("", "door", true, (f)->()->new TDoorBlock(props(f.properties).strength(3.0F).noOcclusion(), f.woodType.setType()).modifiers(RTypeModifier.create(RType.CUTOUT))),
        SIGN("", "sign", false, (f)->()->new StandingSignBlock(props(f.properties).strength(1F).sound(SoundType.WOOD).noCollission(), f.woodType)),
        SIGN_WALL("", "wall_sign", false, (f)->()->new WallSignBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1F).sound(SoundType.WOOD).noCollission(), f.woodType)),
        SIGN_ITEM("", "sign", false, null, (f)->()->new SignItem(f.properties().stacksTo(16), f.getBlock(Type.SIGN).get(), f.getBlock(Type.SIGN_WALL).get())),
        BOAT("", "boat", false, null, (f)->()->new TBoatItem(f.boatType, false, f.properties().stacksTo(1))),
        CHEST_BOAT("", "chest_boat", false, null, (f)->()->new TBoatItem(f.boatType, true, f.properties().stacksTo(1)));

        private final String postfix;
        private final String prefix;
        private final boolean hasBlockItem;
        private final TypeBlockSupplier<Block> blockSupplier;
        private final TypeBlockSupplier<Item> itemSupplier;

        Type(String prefix, String postfix, boolean hasBlockItem, TypeBlockSupplier<Block> blockSupplier)
        {
            this(prefix, postfix, hasBlockItem, blockSupplier, null);
        }

        Type(String prefix, String postfix, boolean hasBlockItem, TypeBlockSupplier<Block> blockSupplier, TypeBlockSupplier<Item> itemSupplier)
        {
            this.postfix = postfix;
            this.prefix = prefix;
            this.hasBlockItem = hasBlockItem;
            this.blockSupplier = blockSupplier;
            this.itemSupplier = itemSupplier;
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
