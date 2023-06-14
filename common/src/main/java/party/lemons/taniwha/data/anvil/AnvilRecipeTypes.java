package party.lemons.taniwha.data.anvil;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.data.anvil.impl.SimpleAnvilRecipe;

import java.util.Optional;

public class AnvilRecipeTypes
{
    public static final ResourceKey<Registry<AnvilRecipeType<?>>> KEY = ResourceKey.createRegistryKey(Taniwha.id("anvil_recipe_type"));
    public static final Registrar<AnvilRecipeType<?>> REGISTRY = RegistrarManager.get(TConstants.MOD_ID).builder(KEY.location(), new AnvilRecipeType<?>[0]).build();
    public static final DeferredRegister<AnvilRecipeType<?>> ANVIL_RECIPE_TYPES = DeferredRegister.create(TConstants.MOD_ID, KEY);

    public static final RegistrySupplier<AnvilRecipeType<?>> SIMPLE = ANVIL_RECIPE_TYPES.register(Taniwha.id("simple"), ()->new AnvilRecipeType<>(SimpleAnvilRecipe.CODEC));

    public static Codec<AnvilRecipeType<?>> byNameCodec() {
        Codec<AnvilRecipeType<?>> codec = ResourceLocation.CODEC
                .flatXmap(
                        resourceLocation -> Optional.ofNullable(REGISTRY.get(resourceLocation))
                                .map(DataResult::success)
                                .orElseGet(() -> DataResult.error(() -> "Unknown registry key in " + REGISTRY.key() + ": " + resourceLocation)),
                        object -> REGISTRY.getKey(object)
                                .map(ResourceKey::location)
                                .map(DataResult::success)
                                .orElseGet(() -> DataResult.error(() -> "Unknown registry element in " + REGISTRY.key() + ":" + object))
                );
        Codec<AnvilRecipeType<?>> codec2 = ExtraCodecs.idResolverCodec(object -> REGISTRY.getKey(object).isPresent() ? REGISTRY.getRawId(object) : -1, REGISTRY::byRawId, -1);
        return ExtraCodecs.overrideLifecycle(ExtraCodecs.orCompressed(codec, codec2), (e)-> Lifecycle.stable(),  (e)->Lifecycle.stable());
    }
    public static final Codec<AnvilRecipe> CODEC = byNameCodec().dispatch(AnvilRecipe::type, AnvilRecipeType::codec);

    public static void init()
    {
        ANVIL_RECIPE_TYPES.register();
    }
}
