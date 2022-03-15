package party.lemons.taniwha.level;

import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Map;

public class LevelDataManager extends SavedData
{
    private static final Map<ResourceLocation, LevelDataFactory> FACTORIES = Maps.newHashMap();
    public static void registerData(ResourceLocation id, LevelDataFactory factory)
    {
        FACTORIES.put(id, factory);
    }

    public static LevelDataManager get(ServerLevel level)
    {
        return ((LevelDataHolder)level).getLevelDataManager();
    }

    private final Map<ResourceLocation, LevelData> levelData = Maps.newHashMap();

    public LevelDataManager(ServerLevel serverLevel, CompoundTag tag)
    {
        loadFrom(serverLevel, tag);
    }

    private void loadFrom(ServerLevel level, CompoundTag tag) {
        if(tag == null)
            return;

        ListTag dataList = tag.getList("data", Tag.TAG_COMPOUND);
        for(int i = 0; i < dataList.size(); i++)
        {
            CompoundTag dataTag = dataList.getCompound(i);
            ResourceLocation location = ResourceLocation.tryParse(dataTag.getString("data_id"));

            if(!FACTORIES.containsKey(location))
                continue;

            LevelData data = FACTORIES.get(location).create(level, dataTag);
            addData(location, data, false);
        }
    }

    public void tick(ServerLevel level)
    {
        for(LevelData data : levelData.values())
            data.tick(level);
    }

    public LevelData getData(ResourceLocation location)
    {
        return levelData.get(location);
    }

    public LevelData getOrCreate(ResourceLocation location, ServerLevel level)
    {
        if(!levelData.containsKey(location))
            levelData.put(location, FACTORIES.get(location).create(level, null));

        return levelData.get(location);
    }

    public LevelData getOr(ResourceLocation location, LevelData data)
    {
        if(!levelData.containsKey(location))
            levelData.put(location, data);

        return levelData.get(location);
    }

    public void addData(ResourceLocation location, LevelData data)
    {
        addData(location, data, true);
    }

    public void addData(ResourceLocation location, LevelData data, boolean markDirty)
    {
        levelData.put(location, data);
        if(markDirty)
            setDirty(true);
    }

    @Override
    public CompoundTag save(CompoundTag tag)
    {
        for(ResourceLocation location : levelData.keySet())
        {
            CompoundTag levelTag = new CompoundTag();
            levelTag.putString("data_id", location.toString());
            levelData.get(location).save(tag);
        }

        return tag;
    }

    @Override
    public boolean isDirty() {

        for(ResourceLocation location : levelData.keySet())
        {
            if(levelData.get(location).isDirty())
            {
                setDirty(true);
                return true;
            }
        }

        return super.isDirty();
    }

    @FunctionalInterface
    public interface LevelDataFactory
    {
        LevelData create(ServerLevel level, CompoundTag tag);
    }

    public static String getFileId(Holder<DimensionType> holder)
    {
        ResourceLocation location = holder.unwrapKey().get().location();
        return location.getPath() + "_" + location.getNamespace();
    }
}
