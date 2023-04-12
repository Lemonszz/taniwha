package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.TBlockExtension;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TFarmBlock extends FarmBlock implements BlockWithModifiers<TFarmBlock>, TBlockExtension
{
	private ModifierContainer<Block> modifierContainer;

	public TFarmBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public TFarmBlock modifiers(BlockModifier... modifiers)
	{
		modifierContainer = new ModifierContainer<>(this, modifiers);
		return this;
	}

	@Override
	public boolean isFarmlandMoist(BlockState state)
	{
		return state.getValue(MOISTURE) > 0;
	}

	@Override
	public @Nullable ModifierContainer<Block> getModifierContainer()
	{
		return modifierContainer;
	}
}
