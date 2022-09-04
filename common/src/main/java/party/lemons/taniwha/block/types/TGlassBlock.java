package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;
import party.lemons.taniwha.registry.ModifierContainer;

public class TGlassBlock extends AbstractGlassBlock implements BlockWithItem, BlockWithModifiers<TGlassBlock>
{
	private ModifierContainer<Block> modifierContainer;

	public TGlassBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public TGlassBlock modifiers(BlockModifier... modifiers)
	{
			modifierContainer = new ModifierContainer<>(this, modifiers);
			return this;
	}

	@Override
	public ModifierContainer<Block> getModifierContainer()
	{
		return modifierContainer;
	}
}