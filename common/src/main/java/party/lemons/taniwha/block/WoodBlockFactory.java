package party.lemons.taniwha.block;

import com.google.common.collect.Maps;
import dev.architectury.registry.block.BlockProperties;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import org.apache.commons.compress.utils.Lists;
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

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WoodBlockFactory
{
    private final List<WoodBlockFactory.Type> types = Lists.newArrayList();
    private final List<WoodBlockFactory.Type> itemTypes = Lists.newArrayList();

    private final Map<Type, Supplier<Block>> blocks = Maps.newHashMap();
    private final Map<Type, Supplier<Item>> items = Maps.newHashMap();
    private final WoodType woodType;
    private final String name;
    private final BlockBehaviour.Properties settings;
    private final Consumer<Supplier<Block>> callback;
    private final String modid;
    private final CreativeModeTab tab;
    private Supplier<BoatType> boatType;

    public WoodBlockFactory(String modid, CreativeModeTab group, String name, Block.Properties settings)
    {
        this(modid, group, name, settings, null);
    }

    public WoodBlockFactory(String modid, CreativeModeTab group, String name, Block.Properties settings, Consumer<Supplier<Block>> callback)
    {
        this.modid = modid;
        this.name = name;
        this.settings = settings;
        this.callback = callback;
        this.tab = group;

        woodType = SignTypeHooks.createWoodType(name);
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

        itemTypes.add(Type.SIGN);

        return this;
    }

    public WoodBlockFactory boat(Supplier<BoatType> boatType)
    {
        this.boatType = boatType;
        itemTypes.add(Type.BOAT);
        return this;
    }

    public Item.Properties properties()
    {
        return new Item.Properties().tab(this.tab);
    }

    public WoodBlockFactory all(Supplier<BoatType> boatType)
    {
        return slab().stair().fence().wood().pressure_plate().button().trapdoor().door().sign().boat(boatType);
    }

    private void set(Type type, Supplier<Block> block)
    {
        this.blocks.put(type, block);
    }

    private void setItem(Type type, Supplier<Item> item)
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
        for(Type type : types)
        {
            Supplier<Block> blockSupplier = null;

            //TODO: move this to the Type enum
            switch (type)
            {
                case LOG -> blockSupplier = ()->new TPillarBlock(settings).modifiers(FlammableModifier.WOOD, new StrippableModifier(()->getBlock(Type.STRIPPED_LOG).get()));
                case WOOD -> blockSupplier = ()->new TPillarBlock(settings).modifiers(FlammableModifier.WOOD, new StrippableModifier(()->getBlock(Type.STRIPPED_WOOD).get()));
                case PLANK -> blockSupplier = ()->new TBlock(settings).modifiers(FlammableModifier.WOOD);
                case STRIPPED_LOG, STRIPPED_WOOD -> blockSupplier = ()->new TPillarBlock(settings).modifiers(FlammableModifier.WOOD);
                case SLAB -> blockSupplier =  ()->new TSlabBlock(settings).modifiers(FlammableModifier.WOOD);
                case STAIR -> blockSupplier =  ()->new TStairBlock(getBlock(Type.PLANK).get().defaultBlockState(), settings).modifiers(FlammableModifier.WOOD);
                case FENCE -> blockSupplier =  ()->new TFenceBlock(settings).modifiers(FlammableModifier.WOOD);
                case FENCE_GATE -> blockSupplier =  ()->new TFenceGateBlock(settings).modifiers(FlammableModifier.WOOD);
                case PRESSURE_PLATE -> blockSupplier =  ()->new TPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockProperties.copy(settings).noCollission());
                case BUTTON -> blockSupplier =  ()->new TButtonBlock(BlockProperties.copy(settings).noCollission());
                case TRAP_DOOR -> blockSupplier =  ()->new TTrapdoorBlock(BlockProperties.copy(settings).noOcclusion()).modifiers(RTypeModifier.create(RType.CUTOUT));
                case DOOR -> blockSupplier =  ()->new TDoorBlock(BlockProperties.copy(settings).noOcclusion()).modifiers(RTypeModifier.create(RType.CUTOUT));
                case SIGN -> blockSupplier =  ()->new StandingSignBlock(BlockProperties.of(Material.WOOD).strength(1F).sound(SoundType.WOOD).noCollission(), woodType);
                case SIGN_WALL -> blockSupplier =  ()-> new WallSignBlock(BlockProperties.of(Material.WOOD).strength(1F).sound(SoundType.WOOD).noCollission(), woodType);
            }

            ResourceLocation id = type.make(this.modid, name);
            RegistrySupplier<Block> regBlock = blockRegister.register(id, blockSupplier);
            set(type, regBlock);

            if(type.hasBlockItem)
            {
                itemRegister.register(id, ()->new BlockItem(regBlock.get(), properties()));
            }

            if(callback != null)
            {
                callback.accept(regBlock);
            }
        }

        for(Type type : itemTypes)
        {
            Supplier<Item> itemSupplier = null;
            switch (type)
            {
                case SIGN -> itemSupplier = ()->new SignItem(properties(), getBlock(Type.SIGN).get(), getBlock(Type.SIGN_WALL).get());
                case BOAT -> itemSupplier =  ()->new TBoatItem(boatType, properties().stacksTo(1));
            }

            RegistrySupplier<Item> item = itemRegister.register(type.make(modid, name), itemSupplier);
            setItem(type, item);
        }

       // if(types.contains(Type.SIGN))
       //     BlockEntityHooks.addAdditionalBlock(BlockEntityType.SIGN, blocks.get(Type.SIGN).get(), blocks.get(Type.SIGN_WALL).get());

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
