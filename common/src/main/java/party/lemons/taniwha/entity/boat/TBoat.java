package party.lemons.taniwha.entity.boat;

import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import party.lemons.taniwha.entity.TEntities;

public class TBoat extends Boat
{
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(TBoat.class, EntityDataSerializers.INT);
    private static final String TAG_TYPE = "NewType";

    public TBoat(EntityType<? extends Boat> entityType, Level world)
    {
        super(entityType, world);
    }

    public TBoat(Level world, double x, double y, double z)
    {
        this(TEntities.T_BOAT.get(), world);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public TBoat(Boat boatEntity, BoatType type)
    {
        this(TEntities.T_BOAT.get(), boatEntity.level);

        this.copyPosition(boatEntity);
        setBoatType(type);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(BOAT_TYPE, 0);
    }

    public void setBoatType(BoatType type)
    {
        this.getEntityData().set(BOAT_TYPE, BoatTypes.TYPES.indexOf(type));
    }

    public BoatType getNewBoatType()
    {
        return BoatTypes.TYPES.get(this.getEntityData().get(BOAT_TYPE));
    }

    @Override
    public Item getDropItem()
    {
        return getNewBoatType().item.get().asItem();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if(tag.contains(TAG_TYPE, Tag.TAG_INT))
        {
            this.setBoatType(BoatTypes.TYPES.get(tag.getInt(TAG_TYPE)));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt(TAG_TYPE, BoatTypes.TYPES.indexOf(this.getNewBoatType()));
    }


    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }
}