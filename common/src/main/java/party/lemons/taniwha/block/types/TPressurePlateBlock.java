package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.PressurePlateBlock;
import party.lemons.taniwha.registry.BlockWithItem;

public class TPressurePlateBlock extends PressurePlateBlock implements BlockWithItem
{
    public TPressurePlateBlock(Sensitivity type, Properties settings)
    {
        super(type, settings);
    }
}