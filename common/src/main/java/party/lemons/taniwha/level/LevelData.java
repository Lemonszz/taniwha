package party.lemons.taniwha.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public abstract class LevelData extends SavedData
{
    public void tick(ServerLevel level)
    {

    }

    public abstract void load(CompoundTag tag);
}
