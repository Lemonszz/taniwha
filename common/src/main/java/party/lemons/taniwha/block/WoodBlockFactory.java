package party.lemons.taniwha.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import party.lemons.taniwha.block.modifier.FlammableModifier;
import party.lemons.taniwha.block.modifier.RTypeModifier;
import party.lemons.taniwha.block.modifier.StrippableModifier;
import party.lemons.taniwha.block.rtype.RType;
import party.lemons.taniwha.block.types.*;
import party.lemons.taniwha.entity.boat.BoatType;
import party.lemons.taniwha.hooks.block.BlockPropertiesHooks;
import party.lemons.taniwha.hooks.block.entity.BlockEntityHooks;
import party.lemons.taniwha.item.ItemHelper;
import party.lemons.taniwha.item.types.TBoatItem;
import party.lemons.taniwha.util.BlockUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
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
    private MapColor barkColor = MapColor.WOOD;
    private MapColor plankColor = MapColor.PODZOL;
    private final Consumer<Supplier<Block>> callback;
    private final String modid;
    private Supplier<BoatType> boatType;
    private final RegistrySupplier<CreativeModeTab> creativeTab;

    public WoodBlockFactory(String modid, String name, RegistrySupplier<CreativeModeTab> creativeTab)
    {
        this(modid, name, new BlockSetType(name), BlockBehaviour.Properties.of().strength(2F, 3F).sound(SoundType.WOOD), creativeTab, null);
    }

    public WoodBlockFactory(String modid, String name, BlockSetType setType, RegistrySupplier<CreativeModeTab>  creativeTab)
    {
        this(modid, name, setType, BlockBehaviour.Properties.of().strength(2F, 3F).sound(SoundType.WOOD), creativeTab, null);
    }

    public WoodBlockFactory(String modid, String name, BlockSetType setType, Block.Properties settings, RegistrySupplier<CreativeModeTab>  creativeTab, Consumer<Supplier<Block>> callback)
    {
        this.modid = modid;
        this.name = name;
        this.properties = settings;
        this.callback = callback;
        this.creativeTab = creativeTab;

        woodType = new WoodType(name, setType);
        WoodType.register(woodType);
        types.add(Type.LOG);
        types.add(Type.STRIPPED_LOG);
        types.add(Type.PLANK);
    }

    public WoodBlockFactory color(MapColor barkColor, MapColor plankColor)
    {
        this.barkColor = barkColor;
        this.plankColor = plankColor;
        return this;
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
        types.add(Type.HANGING_SIGN);
        types.add(Type.HANGING_SIGN_WALL);

        itemTypes.add(Type.SIGN_ITEM);
        itemTypes.add(Type.HANGING_SIGN);

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
        return new Item.Properties().arch$tab(creativeTab);
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

                RegistrySupplier<Block> regBlock = BlockHelper.registerBlock(blockRegister, id, blockSupplier);
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
            Consumer<Block> regularSign = (b)-> BlockEntityHooks.addAdditionalBlock(BlockEntityType.SIGN, b);
            Consumer<Block> hangingSign = (b)-> BlockEntityHooks.addAdditionalBlock(BlockEntityType.HANGING_SIGN, b);

            blocks.get(Type.SIGN).listen(regularSign);
            blocks.get(Type.SIGN_WALL).listen(regularSign);

            blocks.get(Type.HANGING_SIGN).listen(hangingSign);
            blocks.get(Type.HANGING_SIGN_WALL).listen(hangingSign);
        }
        return this;
    }

    @FunctionalInterface
    private interface TypeBlockSupplier<T>
    {
        Supplier<T> getSupplier(WoodBlockFactory factory);
    }

    private static BlockBehaviour.Properties props(BlockBehaviour.Properties props)
    {
        return BlockUtil.copyProperties(props);
    }

    private static BlockBehaviour.Properties propsPlank(WoodBlockFactory f)
    {
        return BlockUtil.copyProperties(f.properties).mapColor(f.plankColor);
    }

    private static BlockBehaviour.Properties propsBark(WoodBlockFactory f)
    {
        return BlockUtil.copyProperties(f.properties).mapColor(f.barkColor);
    }

    private static BlockBehaviour.Properties propsAxis(WoodBlockFactory f)
    {
        return props(f.properties, blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? f.plankColor : f.barkColor);
    }

    private static BlockBehaviour.Properties props(BlockBehaviour.Properties props, Function<BlockState, MapColor> colorFunction)
    {
        return BlockPropertiesHooks.withColor(BlockUtil.copyProperties(props), colorFunction);
    }

    public enum Type
    {
        STRIPPED_WOOD("stripped", "wood", true, (f)->()->new TPillarBlock(propsPlank(f).instrument(NoteBlockInstrument.BASS).ignitedByLava().explosionResistance(2.0F)).modifiers(FlammableModifier.WOOD)),
        STRIPPED_LOG("stripped", "log", true, (f)->()->new TPillarBlock(propsPlank(f).instrument(NoteBlockInstrument.BASS).ignitedByLava().explosionResistance(2.0F)).modifiers(FlammableModifier.WOOD)),
        PLANK("", "planks", true, (f)->()->new TBlock(propsPlank(f).instrument(NoteBlockInstrument.BASS).ignitedByLava()).modifiers(FlammableModifier.WOOD)),
        LOG("", "log", true, (f)->()->new TPillarBlock(propsAxis(f).instrument(NoteBlockInstrument.BASS).ignitedByLava().explosionResistance(2.0F)).modifiers(FlammableModifier.WOOD, new StrippableModifier(()->f.getBlock(Type.STRIPPED_LOG).get()))),
        WOOD("", "wood", true, (f)->()->new TPillarBlock(propsBark(f).instrument(NoteBlockInstrument.BASS).ignitedByLava().explosionResistance(2.0F)).modifiers(FlammableModifier.WOOD, new StrippableModifier(()->f.getBlock(Type.STRIPPED_WOOD).get()))),
        SLAB("", "slab", true, (f)->()->new TSlabBlock(propsPlank(f).instrument(NoteBlockInstrument.BASS).ignitedByLava()).modifiers(FlammableModifier.WOOD)),
        STAIR("", "stairs", true, (f)->()->new TStairBlock(f.getBlock(Type.PLANK).get().defaultBlockState(), propsPlank(f).instrument(NoteBlockInstrument.BASS).ignitedByLava()).modifiers(FlammableModifier.WOOD)),
        FENCE("", "fence", true, (f)->()->new TFenceBlock(propsPlank(f).instrument(NoteBlockInstrument.BASS).ignitedByLava().forceSolidOn()).modifiers(FlammableModifier.WOOD)),
        FENCE_GATE("", "fence_gate", true, (f)->()->new TFenceGateBlock(propsPlank(f).instrument(NoteBlockInstrument.BASS).ignitedByLava().forceSolidOn(), f.woodType).modifiers(FlammableModifier.WOOD)),
        PRESSURE_PLATE("", "pressure_plate", true, (f)->()->new TWoodenPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, f.woodType.setType(), propsPlank(f).pushReaction(PushReaction.DESTROY).instrument(NoteBlockInstrument.BASS).ignitedByLava().forceSolidOn().strength(0.5F).noCollission())),
        BUTTON("", "button", true, (f)-> ()->new TButtonBlock(propsPlank(f).strength(0.5F).pushReaction(PushReaction.DESTROY).noCollission(), f.woodType.setType(), 30 ,true)),
        TRAP_DOOR("", "trapdoor", true, (f)->()->new TTrapdoorBlock(propsPlank(f).strength(3F).noOcclusion().instrument(NoteBlockInstrument.BASS).ignitedByLava().isValidSpawn(BlockHelper::never), f.woodType.setType()).modifiers(RTypeModifier.create(RType.CUTOUT))),
        DOOR("", "door", true, (f)->()->new TDoorBlock(propsPlank(f).instrument(NoteBlockInstrument.BASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(3.0F).noOcclusion(), f.woodType.setType()).modifiers(RTypeModifier.create(RType.CUTOUT))),
        SIGN("", "sign", false, (f)->()->new StandingSignBlock(propsPlank(f).strength(1F).instrument(NoteBlockInstrument.BASS).ignitedByLava().pushReaction(PushReaction.DESTROY).forceSolidOn().sound(SoundType.WOOD).noCollission(), f.woodType)),
        SIGN_WALL("", "wall_sign", false, (f)->()->new WallSignBlock(BlockBehaviour.Properties.of().mapColor(f.plankColor).instrument(NoteBlockInstrument.BASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(1F).sound(SoundType.WOOD).noCollission(), f.woodType)),
        SIGN_ITEM("", "sign", false, null, (f)->()->new SignItem(f.properties().stacksTo(16), f.getBlock(Type.SIGN).get(), f.getBlock(Type.SIGN_WALL).get())),
        HANGING_SIGN("", "hanging_sign", false, (f)->()->new CeilingHangingSignBlock(BlockBehaviour.Properties.of().mapColor(f.plankColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), f.woodType)),
        HANGING_SIGN_WALL("", "wall_hanging_sign", false, (f)->()->new WallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(f.plankColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), f.woodType)),
        HANGING_SIGN_ITEM("", "hanging_sign", false, null, (f)->()->new HangingSignItem(f.getBlock(Type.HANGING_SIGN).get(), f.getBlock(Type.HANGING_SIGN_WALL).get(), f.properties().stacksTo(16))),
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

    //loop through every BlockPos in a radius

}
