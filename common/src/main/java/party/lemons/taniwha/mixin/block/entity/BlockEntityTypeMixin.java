package party.lemons.taniwha.mixin.block.entity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.hooks.block.entity.BlockEntityHooks;

import java.util.HashSet;
import java.util.Set;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin implements BlockEntityHooks {

    @Shadow
    @Final
    @Mutable
    private Set<Block> validBlocks;

    @Override
    public void addAdditionalBlockTypes(Block... blocks) {
        Set<Block> b = new HashSet<>(validBlocks);

        for (Block block : blocks)
            b.add(block);

        validBlocks = b;
    }
}