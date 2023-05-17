package party.lemons.taniwha.data.criterion;

import net.minecraft.advancements.CriteriaTriggers;
import party.lemons.taniwha.TConstants;

public class TAdvancements
{
	public static final WearArmorCriterion WEAR_ARMOUR = new WearArmorCriterion(TConstants.MOD_ID);

	public static void init()
	{
		CriteriaTriggers.register(WEAR_ARMOUR);
	}

}
