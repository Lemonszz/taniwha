package party.lemons.taniwha.level.structure;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;

public class TStructureProcessors
{
	private static final DeferredRegister<StructureProcessorType<?>> PROCESSOR_TYPES = DeferredRegister.create(TConstants.MOD_ID, Registries.STRUCTURE_PROCESSOR);

	public static final RegistrySupplier<StructureProcessorType<ReplaceSelectionProcessor>> REPLACE_SELECTION = PROCESSOR_TYPES.register(Taniwha.id("replace_selection"), ()->()-> ReplaceSelectionProcessor.CODEC);
	public static final RegistrySupplier<StructureProcessorType<SuspiciousBlockProcessor>> SUSPICIOUS_BLOCK_REPLACEMENT = PROCESSOR_TYPES.register(Taniwha.id("suspicious_block_replacement"), ()->()-> SuspiciousBlockProcessor.CODEC);
	public static final RegistrySupplier<StructureProcessorType<IgnoreAirProcessor>> IGNORE_AIR = PROCESSOR_TYPES.register(Taniwha.id("ignore_air"), ()->()-> IgnoreAirProcessor.CODEC);


	public static void init()
	{
		PROCESSOR_TYPES.register();
	}
}
