package party.lemons.taniwha.level.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class SuspiciousBlockProcessor extends StructureProcessor
{
	public static final Codec<SuspiciousBlockProcessor> CODEC =  RecordCodecBuilder.create(instance ->
			instance.group(
							BuiltInRegistries.BLOCK.byNameCodec().fieldOf("target").forGetter(i->i.target),
							BuiltInRegistries.BLOCK.byNameCodec().fieldOf("output_regular").forGetter(i->i.regularOutput),
							BuiltInRegistries.BLOCK.byNameCodec().fieldOf("output_suspicious").forGetter(i->i.suspiciousOutput),
							Codec.FLOAT.fieldOf("suspicious_chance").forGetter(i->i.suspiciousChance),
							ResourceLocation.CODEC.fieldOf("loot_table").forGetter(c->c.loottable)
					)
					.apply(instance, SuspiciousBlockProcessor::new));

	private final Block target;
	private final Block regularOutput;
	private final Block suspiciousOutput;
	private final float suspiciousChance;
	private final ResourceLocation loottable;

	public SuspiciousBlockProcessor(Block target, Block regularOutput, Block suspiciousOutput, float suspiciousChance, ResourceLocation loottable)
	{
		this.target = target;
		this.regularOutput = regularOutput;
		this.suspiciousOutput = suspiciousOutput;
		this.suspiciousChance = suspiciousChance;
		this.loottable = loottable;
	}

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos blockPos, BlockPos blockPos2, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo input, StructurePlaceSettings settings)
	{
		if(input.state().is(target))
		{
			BlockPos pos = input.pos();
			RandomSource randomSource = settings.getRandom(pos);
			if(randomSource.nextFloat() > suspiciousChance)
				return new StructureTemplate.StructureBlockInfo(pos, regularOutput.defaultBlockState(), null);

			CompoundTag tags = new CompoundTag();
			tags.putString("LootTable", loottable.toString());

			return new StructureTemplate.StructureBlockInfo(pos, suspiciousOutput.defaultBlockState(), tags);
		}

		return input;
	}

	@Override
	protected StructureProcessorType<?> getType()
	{
		return TStructureProcessors.SUSPICIOUS_BLOCK_REPLACEMENT.get();
	}
}
