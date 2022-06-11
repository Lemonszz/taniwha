package party.lemons.testmod;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import party.lemons.taniwha.item.types.TItem;

public class TaniwhaTestMod
{
	public static final CreativeModeTab TAB = CreativeTabRegistry.create(id("test"), ()->new ItemStack(TaniwhaTestMod.TEST_ITEM.get()));

	public static final String MODID = "taniwhatest";

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MODID, Registry.ITEM_REGISTRY);
	public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register(id("test_item"), ()-> new TItem(new Item.Properties().tab(TAB)));

	public static void init()
	{
		ITEMS.register();
	}

	public static ResourceLocation id(String path)
	{
		return new ResourceLocation(MODID, path);
	}
}
