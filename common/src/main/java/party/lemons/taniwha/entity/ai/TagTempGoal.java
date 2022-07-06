package party.lemons.taniwha.entity.ai;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.Item;

public class TagTempGoal extends TemptGoal
{
	private final TagKey<Item> temptItemTag;

	public TagTempGoal(PathfinderMob pathfinderMob, TagKey<Item> itemTag, double speed, boolean canScare)
	{
		super(pathfinderMob, speed, null, canScare);

		this.temptItemTag = itemTag;
	}

	private boolean shouldFollow(LivingEntity entity) {
		return entity.getMainHandItem().is(temptItemTag) || entity.getOffhandItem().is(temptItemTag);
	}
}
