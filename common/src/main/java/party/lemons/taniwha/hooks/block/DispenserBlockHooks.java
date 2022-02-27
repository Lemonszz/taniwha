package party.lemons.taniwha.hooks.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

public interface DispenserBlockHooks {
    public static void removeItemBehaviour(Item item)
    {
        ((DispenserBlockHooks) Blocks.DISPENSER).removeBehaviour(item);
    }

    public static boolean hasItemBehaviour(Item item)
    {
        return ((DispenserBlockHooks) Blocks.DISPENSER).hasBehaviour(item);
    }

    void removeBehaviour(Item item);
    boolean hasBehaviour(Item item);
}
