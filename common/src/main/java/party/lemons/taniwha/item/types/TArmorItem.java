package party.lemons.taniwha.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class TArmorItem extends ArmorItem
{
	private final Multimap<Attribute, AttributeModifier> attributes;
	private final int protection;
	private final float toughness;

	public TArmorItem(ArmorMaterial material, Multimap<Attribute, AttributeModifier> attributes, int protection, float toughness, EquipmentSlot slot, Properties properties)
	{
		super(material, slot, properties);

		this.attributes = attributes;
		this.protection = protection;
		this.toughness = toughness;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot)
	{
		return equipmentSlot == this.slot ? attributes : ImmutableMultimap.of();
	}

	@Override
	public float getToughness()
	{
		return toughness;
	}

	@Override
	public int getDefense()
	{
		return this.protection;
	}
}