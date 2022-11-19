package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TCropBlock extends CropBlock implements BlockWithModifiers<TCropBlock>
{
	private ModifierContainer<Block> modifierContainer;

	public TCropBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public TCropBlock modifiers(BlockModifier... modifiers) {
		modifierContainer = new ModifierContainer<>(this, modifiers);
		return this;
	}

	@Override
	public ModifierContainer<Block> getModifierContainer() {
		return modifierContainer;
	}
}
