package party.lemons.taniwha.hooks.block;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;

import java.util.HashSet;

public class ComposterHooks {

    /*
        Internal Use only, register via .json
     */
    public static void registerCompost(Item itemLike, float chance)
    {
        extra_compost.add(Pair.of(itemLike, chance));
        ComposterBlock.COMPOSTABLES.put(itemLike, chance);
    }

    /*
        Internal Use onlu
     */
    public static void clearCompost()
    {
        extra_compost.forEach(c-> ComposterBlock.COMPOSTABLES.removeFloat(c.getFirst()));
        extra_compost.clear();
    }


    private static final HashSet<Pair<ItemLike, Float>> extra_compost = Sets.newHashSet();
}
