package party.lemons.taniwha.hooks.block;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;

import java.util.HashSet;
import java.util.Map;

public class BrewingStandHooks {

    public static int getBrewingFuel(BrewingStandBlockEntity brewingStand)
    {
        return ((BrewingStandAccess)brewingStand).getFuel();
    }

    public static void setBrewingFuel(BrewingStandBlockEntity brewingStand, int fuel)
    {
        ((BrewingStandAccess)brewingStand).setFuel(fuel);
    }

    /*
        Internal Use only, register via .json
     */
    public static void registerBrewingFuelItem(Ingredient ingredient, int fuelAmount)
    {
        EXTRA_FUEL.add(Pair.of(ingredient, fuelAmount));
    }

    /*
        Internal Use onlu
     */
    public static void clearBrewingFuel()
    {
        EXTRA_FUEL.clear();
    }

    public static int getFuelForItem(ItemStack stack)
    {
        if(stack.isEmpty())
            return 0;

        ItemLike checkItem = stack.getItem();
        if(checkItem == Items.BLAZE_POWDER)
            return 20;

        for(Pair<Ingredient, Integer> fuelSource : EXTRA_FUEL)
        {
            if(fuelSource.getFirst().test(stack))
                return fuelSource.getSecond();
        }

        return 0;
    }

    private static final HashSet<Pair<Ingredient, Integer>> EXTRA_FUEL = Sets.newHashSet();

    public interface BrewingStandAccess {
        int getFuel();
        void setFuel(int fuel);
    }
}
