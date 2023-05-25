package party.lemons.taniwha.entity.boat;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import party.lemons.taniwha.TConstants;

import java.util.function.Supplier;

public class BoatType
{
    public final ResourceLocation id;
    public final Supplier<ItemLike> item;
    public final Supplier<ItemLike> chestItem;


    public BoatType(ResourceLocation id, Supplier<ItemLike> item, Supplier<ItemLike> chestBoatItem)
    {
        this.id = id;
        this.item = item;
        this.chestItem = chestBoatItem;

        BoatTypes.TYPES.add(this);
        EntityModelLayerRegistry.register(new ModelLayerLocation(new ResourceLocation(TConstants.MOD_ID, getModelLocation()), "main"), BoatModel::createBodyModel);
        EntityModelLayerRegistry.register(new ModelLayerLocation(new ResourceLocation(TConstants.MOD_ID, getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
    }

    public ResourceLocation getTexture(boolean hasChest)
    {
        if(hasChest)
            return new ResourceLocation(id.getNamespace(), "textures/entity/boat/" + id.getPath() + "_chest.png");
        return new ResourceLocation(id.getNamespace(), "textures/entity/boat/" + id.getPath() + ".png");
    }

    public String getModelLocation()
    {
        return "boat/" + id.getPath();
    }

    public String getChestModelLocation()
    {
        return "chestboat/" + id.getPath();
    }

    public static class BoatDispenseItemBehavior extends DefaultDispenseItemBehavior
    {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior;
        private final Supplier<BoatType> type;
        private final boolean isChestBoat;

        public BoatDispenseItemBehavior(Supplier<BoatType> type) {
            this(type, false);
        }

        public BoatDispenseItemBehavior(Supplier<BoatType> type, boolean hasChest) {
            this.defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
            this.type = type;
            this.isChestBoat = hasChest;
        }

        public ItemStack execute(BlockSource pos, ItemStack stack) {
            Direction direction = pos.getBlockState().getValue(DispenserBlock.FACING);
            Level level = pos.getLevel();
            double d = pos.x() + (double)((float)direction.getStepX() * 1.125F);
            double e = pos.y() + (double)((float)direction.getStepY() * 1.125F);
            double f = pos.z() + (double)((float)direction.getStepZ() * 1.125F);
            BlockPos blockPos = pos.getPos().relative(direction);
            double g;
            if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                g = 1.0D;
            } else {
                if (!level.getBlockState(blockPos).isAir() || !level.getFluidState(blockPos.below()).is(FluidTags.WATER)) {
                    return this.defaultDispenseItemBehavior.dispense(pos, stack);
                }

                g = 0.0D;
            }

            TBoat boat = this.isChestBoat ? new TChestBoat(level, d, e + g, f) : new TBoat(level, d, e + g, f);
            boat.setBoatType(this.type.get());
            boat.setYRot(direction.toYRot());
            level.addFreshEntity((Entity)boat);
            stack.shrink(1);
            return stack;
        }

        protected void playSound(BlockSource arg) {
            arg.getLevel().levelEvent(1000, arg.getPos(), 0);
        }
    }

}