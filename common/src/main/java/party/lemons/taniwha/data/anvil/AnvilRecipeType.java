package party.lemons.taniwha.data.anvil;

import com.mojang.serialization.Codec;

public record AnvilRecipeType<T extends AnvilRecipe>(Codec<T> codec)
{
}
