package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.block.modifier.RTypeModifier;

public class TFlowerPotBlock extends FlowerPotBlock implements BlockWithModifiers<TFlowerPotBlock>
{
    public TFlowerPotBlock(Block block, Properties properties) {
        super(block, properties);

        modifiers(RTypeModifier.CUTOUT);
    }
    @Override
    public TFlowerPotBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);

        return this;
    }
}
