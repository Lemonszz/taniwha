package party.lemons.taniwha.entity;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;

public class TEntityTypeBuilder<T extends Entity> {

	private final EntityType.EntityFactory<T> factory;
	private final MobCategory category;
	private ImmutableSet<Block> immuneTo = ImmutableSet.of();
	private boolean serialize = true;
	private boolean summon = true;
	private boolean fireImmune;
	private boolean canSpawnFarFromPlayer;
	private int clientTrackingRange = 5;
	private int updateInterval = 3;
	private EntityDimensions dimensions = EntityDimensions.scalable(0.6f, 1.8f);

	private TEntityTypeBuilder(EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory) {
		this.factory = entityFactory;
		this.category = mobCategory;
		this.canSpawnFarFromPlayer = mobCategory == MobCategory.CREATURE || mobCategory == MobCategory.MISC;
	}

	public static <T extends Entity> TEntityTypeBuilder<T> of(EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory) {
		return new TEntityTypeBuilder<T>(entityFactory, mobCategory);
	}

	public TEntityTypeBuilder<T> sized(float f, float g) {
		this.dimensions = EntityDimensions.scalable(f, g);
		return this;
	}

	public TEntityTypeBuilder<T> noSummon() {
		this.summon = false;
		return this;
	}

	public TEntityTypeBuilder<T> noSave() {
		this.serialize = false;
		return this;
	}

	public TEntityTypeBuilder<T> fireImmune() {
		this.fireImmune = true;
		return this;
	}

	public TEntityTypeBuilder<T> immuneTo(Block ... blocks) {
		this.immuneTo = ImmutableSet.copyOf(blocks);
		return this;
	}

	public TEntityTypeBuilder<T> canSpawnFarFromPlayer() {
		this.canSpawnFarFromPlayer = true;
		return this;
	}

	public TEntityTypeBuilder<T> clientTrackingRange(int i) {
		this.clientTrackingRange = i;
		return this;
	}

	public TEntityTypeBuilder<T> updateInterval(int i) {
		this.updateInterval = i;
		return this;
	}

	public EntityType<T> build() {
		return new EntityType<>(this.factory, this.category, this.serialize, this.summon, this.fireImmune, this.canSpawnFarFromPlayer, this.immuneTo, this.dimensions, this.clientTrackingRange, this.updateInterval);
	}
}