package party.lemons.taniwha.entity.boat;

import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import party.lemons.taniwha.entity.TEntities;

public class TBoat extends Boat
{
    private static final EntityDataAccessor<String> BOAT_TYPE = SynchedEntityData.defineId(TBoat.class, EntityDataSerializers.STRING);
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
        this.entityData.define(BOAT_TYPE, BoatTypes.ACACIA.id.toString());
    }

    public void setBoatType(BoatType type)
    {
        if(type == null)
            return;

        this.getEntityData().set(BOAT_TYPE, type.id.toString());
    }

    public BoatType getNewBoatType()
    {
        return BoatTypes.TYPES_MAP.get(new ResourceLocation(this.getEntityData().get(BOAT_TYPE)));
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
        else if(tag.contains(TAG_TYPE, Tag.TAG_STRING))
        {
            this.setBoatType(BoatTypes.TYPES_MAP.get(new ResourceLocation(tag.getString(TAG_TYPE))));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putString(TAG_TYPE, getNewBoatType().id.toString());
    }


    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }
}