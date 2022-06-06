package party.lemons.taniwha.mixin.level.data;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.level.LevelDataHolder;
import party.lemons.taniwha.level.LevelDataManager;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements LevelDataHolder {

    @Unique private LevelDataManager dataManager;

    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i)
    {
        super(writableLevelData, resourceKey, holder, supplier, bl, bl2, l, i);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void onInit(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey<Level> resourceKey, LevelStem levelStem, ChunkProgressListener chunkProgressListener, boolean bl, long l, List<CustomSpawner> list, boolean bl2, CallbackInfo cbi)
    {
        dataManager = this.getDataStorage().computeIfAbsent(tag -> new LevelDataManager(((ServerLevel) (Object)this),tag), () -> new LevelDataManager(((ServerLevel) (Object)this),null), LevelDataManager.getFileId(this.dimensionTypeRegistration()));

    }

    @Inject(at = @At("TAIL"), method = "tick")
    void tick(BooleanSupplier booleanSupplier, CallbackInfo cbi)
    {
        dataManager.tick(((ServerLevel) (Object)this));
    }

    @Shadow
    public DimensionDataStorage getDataStorage()
    {
        throw new AssertionError();
    }

    @Override
    public LevelDataManager getLevelDataManager() {
        return dataManager;
    }

}
