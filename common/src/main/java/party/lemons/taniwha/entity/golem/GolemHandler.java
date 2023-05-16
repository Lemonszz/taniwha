package party.lemons.taniwha.entity.golem;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import party.lemons.taniwha.block.TBlockTags;
import party.lemons.taniwha.hooks.TEvents;
import party.lemons.taniwha.hooks.block.DispenserBlockHooks;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class GolemHandler
{
    private static final List<GolemInstance> golems = Lists.newArrayList();
    private static final List<TagKey<Block>> golemHeadTags = Lists.newArrayList();
    private static final List<Item> registeredDispenserBehaviour = Lists.newArrayList();
    private static final Map<Item, DispenseItemBehavior> dispenseBehaviourOverride = Maps.newHashMap();

    public static final Predicate<BlockState> STANDARD_HEADS = st->st.is(TBlockTags.GOLEM_HEADS);  //Standard head (pumpkin) BS predicate

    //TODO: this seems fucky
    public static final Predicate<TagKey<Block>> IS_STANDARD_GOLEM_HEAD_TAG = t->t.equals(TBlockTags.GOLEM_HEADS); //Predicate to check if a tag is the standard golem head tag

    /***
     * Registers a pattern for golems
     * @param headPredicate Is the block tag the head is in is valid. Pumpkin example: GolemHandler.IS_STANDARD_GOLEM_HEAD_TAG
     * @param fullPattern   Full pattern including head
     * @param basePattern   Base pattern, without head
     * @param result        Golem result.
     */
    public static void addPattern(Predicate<TagKey<Block>> headPredicate, BlockPattern fullPattern, BlockPattern basePattern, GolemResult result)
    {
        golems.add(new GolemInstance(headPredicate, fullPattern, basePattern, result));
    }

    public static void addPattern(BlockPattern fullPattern, BlockPattern basePattern, GolemResult result)
    {
        addPattern(IS_STANDARD_GOLEM_HEAD_TAG, fullPattern, basePattern, result);
    }

    /***
     * Adds a new tag to check for golem heads
     * @param tag The block tag to add
     */
    public static void addGolemHeadTag(TagKey<Block> tag)
    {
        golemHeadTags.add(tag);
        reloadDispenserBehaviours();
    }

    /***
     * Override default dispenser behaviour
     * Default behaviour just creates the golem
     * Vanilla uses this for it's pumpkins, since they can be dispensed onto players
     * @param item The BlockItem to add the override to.
     * @param behaviour dispensor behaviour
     */
    public static void addDispenseOverride(Item item, DispenseItemBehavior behaviour)
    {
        dispenseBehaviourOverride.put(item, behaviour);
    }

    public static void init()
    {
        //When tags are reloaded, we need to reset the dispenser behaviour.
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new GolemReloadListener());

        addGolemHeadTag(TBlockTags.GOLEM_HEADS); //Standard head tags

        registerVanillaPatterns();  //Vanilla patterns (Iron/Snow)
        registerVanillaBehaviour(); //Vanilla pumpkin behaviour (Create golem and wear pumpkin)

        //Block place event.
        //Checks if placed block is in any of the golemHeadTags, then tries to create it.
        TEvents.PLACE.register((level, pos, state, placer) -> {
            for(TagKey<Block> tag : golemHeadTags)
            {
                if(state.is(tag))
                {
                    GolemHandler.checkAndCreateGolem(level, pos);
                    return;
                }
            }
        });

        //Load in the dispenser behaviour after tags have loaded
        LifecycleEvent.SERVER_LEVEL_LOAD.register(world -> reloadDispenserBehaviours());
    }

    private static void registerVanillaBehaviour()
    {
        //Vanilla pumpkins get an override since they can be equipped through a dispenser
        DispenserBlockHooks.removeItemBehaviour(Blocks.CARVED_PUMPKIN.asItem());
        DispenserBlockHooks.removeItemBehaviour(Blocks.JACK_O_LANTERN.asItem());
        addDispenseOverride(Blocks.CARVED_PUMPKIN.asItem(), PUMPKIN_DISPENSE_BEHAVIOUR);
        addDispenseOverride(Blocks.JACK_O_LANTERN.asItem(), PUMPKIN_DISPENSE_BEHAVIOUR);
    }

    /***
     * Will placing a golem head block at the position create a golem?
     * @param level level
     * @param blockPos head position
     * @param stack Block Item Stack
     * @return Can create golem at position
     */
    public static boolean canDispenseGolem(ServerLevel level, BlockPos blockPos, ItemStack stack)
    {
        TagKey<Block> headTag = getHeadTagForStack(stack);
        if(headTag == null)
            return false;

        for(GolemInstance golemInstance : golems)
        {
            if(golemInstance.headPredicate.test(headTag) && golemInstance.basePattern.find(level, blockPos) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the head tag for a specific item stack
     * Will return null if the stack isn't a BlockItem
     * @param stack check stack
     * @return head tag
     */
    private static TagKey<Block> getHeadTagForStack(ItemStack stack)
    {
        if(stack.isEmpty())
            return null;

        Item item = stack.getItem();
        if(item instanceof BlockItem bi)
        {
            for(TagKey<Block> tag : golemHeadTags)
            {
                if(bi.getBlock().defaultBlockState().is(tag))
                    return tag;
            }
        }
        return null;
    }

    /***
     * Create a golem at the position
     * @param level level
     * @param pos head pos
     * @return if golem created successfully
     */
    public static boolean checkAndCreateGolem(Level level, BlockPos pos)
    {
        for(GolemInstance golem : golems)
        {
            BlockPattern.BlockPatternMatch fullMatch = golem.fullPattern.find(level, pos);
            if (fullMatch == null)  //Didn't find the pattern.
                continue;

            for (int x = 0; x < golem.fullPattern.getWidth(); ++x) {
                for (int y = 0; y < golem.fullPattern.getHeight(); ++y)
                {
                    BlockInWorld block = fullMatch.getBlock(x, y, 0);
                    level.setBlock(block.getPos(), Blocks.AIR.defaultBlockState(), 2);
                    level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, block.getPos(), Block.getId(block.getState()));
                }
            }
            BlockPos placePosition = fullMatch.getBlock(golem.fullPattern.getWidth() / 2, golem.fullPattern.getHeight() - 1, 0).getPos();
            if(golem.result.spawnGolem(level, golem, pos, placePosition))
            {
                for (int x = 0; x < golem.fullPattern.getWidth(); ++x) {
                    for (int y = 0; y < golem.fullPattern.getHeight(); ++y) {
                        BlockInWorld removePosition = fullMatch.getBlock(x, y, 0);
                        level.blockUpdated(removePosition.getPos(), Blocks.AIR);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static void reloadDispenserBehaviours()
    {
        for(Item item : registeredDispenserBehaviour)
            DispenserBlockHooks.removeItemBehaviour(item);

        registeredDispenserBehaviour.clear();

        for(TagKey<Block> tag : golemHeadTags)
        {

            for (Holder<Block> blockHolder : BuiltInRegistries.BLOCK.getTagOrEmpty(tag)) {
                Block bl = blockHolder.value();
                Item it = bl.asItem();

                if (it != Items.AIR) {
                    registeredDispenserBehaviour.add(it);

                    DispenserBlock.registerBehavior(it, dispenseBehaviourOverride.getOrDefault(it, DISPENSE_BEHAVIOUR));
                }
            }
        }
    }

    public record GolemInstance(Predicate<TagKey<Block>> headPredicate, BlockPattern fullPattern, BlockPattern basePattern, GolemResult result)
    {
    }

    @FunctionalInterface
    public interface GolemResult
    {
        boolean spawnGolem(Level level, GolemInstance golemInstance, BlockPos pumpkinPos, BlockPos placePosition);
    }

    public static class SummonGolemResult<T extends AbstractGolem & PlayerCreatable> implements GolemResult
    {
        private final EntityType<T> golemType;

        public SummonGolemResult(EntityType<T> golem)
        {
            this.golemType = golem;
        }

        @Override
        public boolean spawnGolem(Level level, GolemInstance golemInstance, BlockPos pumpkinPos, BlockPos placePosition)
        {
            T golem = golemType.create(level);
            if(golem != null) {
                golem.setPlayerCreated(true);
                golem.moveTo((double) placePosition.getX() + 0.5, (double) placePosition.getY() + 0.05, (double) placePosition.getZ() + 0.5, 0.0f, 0.0f);
                level.addFreshEntity(golem);
                for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, golem);
                }
            }
            return true;
        }
    }
    public static class SummonIronGolemResult implements GolemResult
    {
        @Override
        public boolean spawnGolem(Level level, GolemInstance golemInstance, BlockPos pumpkinPos, BlockPos placePosition)
        {
            IronGolem golem = EntityType.IRON_GOLEM.create(level);
            if(golem != null) {
                golem.moveTo((double) placePosition.getX() + 0.5, (double) placePosition.getY() + 0.05, (double) placePosition.getZ() + 0.5, 0.0f, 0.0f);
                golem.setPlayerCreated(true);

                level.addFreshEntity(golem);
                for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, golem);
                }
            }
            return true;
        }
    }


    public static class SummonEntityResult implements GolemResult
    {
        private final EntityType<?> golemType;

        public SummonEntityResult(EntityType<?> golem)
        {
            this.golemType = golem;
        }

        @Override
        public boolean spawnGolem(Level level, GolemInstance golemInstance, BlockPos pumpkinPos, BlockPos placePosition)
        {
            Entity golem = golemType.create(level);
            if(golem != null) {
                golem.moveTo((double) placePosition.getX() + 0.5, (double) placePosition.getY() + 0.05, (double) placePosition.getZ() + 0.5, 0.0f, 0.0f);
                level.addFreshEntity(golem);

                for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, golem);
                }
            }
            return true;
        }
    }

    private static void registerVanillaPatterns()
    {
        //Iron golem
        GolemHandler.addPattern(
                BlockPatternBuilder.start().aisle("~^~", "###", "~#~")
                        .where('^', BlockInWorld.hasState(STANDARD_HEADS))
                        .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
                        .where('~', (block) -> block.getState().isAir())
                        .build(),
                BlockPatternBuilder.start().aisle("~ ~", "###", "~#~")
                        .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
                        .where('~', (block) -> block.getState().isAir())
                        .build(),
                new SummonIronGolemResult()
        );

        //Snow Golem
        GolemHandler.addPattern(
                BlockPatternBuilder.start().aisle("^", "#", "#")
                        .where('^', BlockInWorld.hasState(STANDARD_HEADS))
                        .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build(),
                BlockPatternBuilder.start().aisle(" ", "#", "#")
                        .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
                        .build(),
                new SummonEntityResult(EntityType.SNOW_GOLEM)
        );
    }

    private GolemHandler() {

    }

    private static final OptionalDispenseItemBehavior DISPENSE_BEHAVIOUR = new OptionalDispenseItemBehavior(){
        @Override
        protected ItemStack execute(@NotNull BlockSource bs, @NotNull ItemStack stack) {
            if (dispenseGolem(bs, stack)) {
                stack.shrink(1);
                this.setSuccess(true);
            }
            return stack;
        }
    };

    private static final OptionalDispenseItemBehavior PUMPKIN_DISPENSE_BEHAVIOUR = new OptionalDispenseItemBehavior(){
        @Override
        protected ItemStack execute(@NotNull BlockSource bs, @NotNull ItemStack stack) {
            if (GolemHandler.dispenseGolem(bs, stack)) {
                setSuccess(true);
                stack.shrink(1);
            }
            else
            {
                this.setSuccess(ArmorItem.dispenseArmor(bs, stack));
            }
            return stack;
        }
    };

    private static boolean dispenseGolem(BlockSource bs, ItemStack stack)
    {
        ServerLevel level = bs.getLevel();
        BlockPos blockpos = bs.getPos().relative(bs.getBlockState().getValue(DispenserBlock.FACING));

        if(stack.getItem() instanceof  BlockItem bi) {
            if (level.isEmptyBlock(blockpos) && canDispenseGolem(level, blockpos, stack)) {
                if (!level.isClientSide) {
                    level.setBlock(blockpos, bi.getBlock().defaultBlockState(), 3);
                    level.gameEvent(null, GameEvent.BLOCK_PLACE, blockpos);

                    return GolemHandler.checkAndCreateGolem(level, blockpos);
                }
            }
        }
        return false;
    }

    private static class GolemReloadListener extends SimplePreparableReloadListener<String>{
        @Override
        protected String prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
            return ":)";
        }

        @Override
        protected void apply(@NotNull String object, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
            GolemHandler.reloadDispenserBehaviours();
        }
    }
}
