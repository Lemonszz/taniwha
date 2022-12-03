package party.lemons.taniwha.block.types;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TDropExperienceBlock extends DropExperienceBlock implements BlockWithModifiers<TDropExperienceBlock>
{
	private ModifierContainer<Block> modifierContainer;

	public TDropExperienceBlock(Properties properties)
	{
		super(properties);
	}

	public TDropExperienceBlock(Properties properties, IntProvider intProvider)
	{
		super(properties, intProvider);
	}

	@Override
	public TDropExperienceBlock modifiers(BlockModifier... modifiers) {
		modifierContainer = new ModifierContainer<>(this, modifiers);
		return this;
	}

	@Override
	public @Nullable ModifierContainer<Block> getModifierContainer() {
		return modifierContainer;
	}
}
