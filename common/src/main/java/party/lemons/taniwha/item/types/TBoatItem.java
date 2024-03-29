package party.lemons.taniwha.item.types;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import party.lemons.taniwha.entity.boat.BoatType;
import party.lemons.taniwha.entity.boat.TBoat;
import party.lemons.taniwha.entity.boat.TChestBoat;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TBoatItem extends TItem{

    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final Supplier<BoatType> type;
    private final boolean hasChest;

    public TBoatItem(Supplier<BoatType> boatType, boolean hasChest, Properties properties) {
        super(properties);

        this.type = boatType;
        this.hasChest = hasChest;

        DispenserBlock.registerBehavior(this, new BoatType.BoatDispenseItemBehavior(type, hasChest));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            Vec3 vec3 = player.getViewVector(1.0F);
            double d = 5.0;
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0)).inflate(1.0), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec32 = player.getEyePosition();

                for(Entity entity : list) {
                    AABB aABB = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (aABB.contains(vec32)) {
                        return InteractionResultHolder.pass(itemStack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                TBoat boat = createBoat(level, hitResult.getLocation(), player.getYRot());
                boat.setYRot(player.getYRot());
                if (!level.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemStack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                        if (!player.getAbilities().instabuild) {
                            itemStack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemStack);
            }
        }
    }

    public TBoat createBoat(Level world, Vec3 pos, float yaw)
    {
        TBoat boatEntity = hasChest ? new TChestBoat(world, pos.x, pos.y, pos.z) : new TBoat(world, pos.x, pos.y, pos.z);
        boatEntity.setBoatType(type.get());
        boatEntity.setYRot(yaw);
        return boatEntity;
    }
}
