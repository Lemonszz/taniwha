package party.lemons.taniwha.item;

import com.google.common.collect.LinkedListMultimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import party.lemons.taniwha.item.types.TArmorItem;

import java.util.UUID;
import java.util.function.Supplier;

public class ArmorBuilder
{
	private static final UUID DUMMY_UUID = UUID.randomUUID();
	private static final UUID[] MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};

	private final LinkedListMultimap<Attribute, AttributeModifier> attributes = LinkedListMultimap.create();
	private final ArmorMaterial material;
	private int protection;
	private boolean overrideProtection = false;
	private float toughness;
	private float knockbackResistance;

	public static ArmorBuilder create(ArmorMaterial material)
	{
		return new ArmorBuilder(material);
	}

	public ArmorBuilder protection(int amount)
	{
		this.protection = amount;
		this.overrideProtection = true;
		return this;
	}

	public ArmorBuilder toughness(float amount)
	{
		this.toughness = amount;
		return this;
	}

	public ArmorBuilder knockbackResistance(float amount)
	{
		this.knockbackResistance = amount;
		return this;
	}

	public ArmorBuilder attribute(String name, Attribute attribute, double value, AttributeModifier.Operation operation)
	{
		attributes.put(attribute, new AttributeModifier(DUMMY_UUID, name, value, operation));
		return this;
	}

	public Supplier<Item> build(ArmorItem.Type type, Item.Properties properties)
	{
		if(!overrideProtection)
			protection = material.getDefenseForType(type);

		attributes.removeAll(Attributes.ARMOR);
		attributes.removeAll(Attributes.ARMOR_TOUGHNESS);
		attributes.removeAll(Attributes.KNOCKBACK_RESISTANCE);

		attributes.put(Attributes.ARMOR, new AttributeModifier(DUMMY_UUID, "Armor modifier", this.protection, AttributeModifier.Operation.ADDITION));
		attributes.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(DUMMY_UUID, "Armor toughness", this.toughness, AttributeModifier.Operation.ADDITION));
		if (this.knockbackResistance != 0.0F)
		{
			attributes.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(DUMMY_UUID, "Armor knockback resistance", this.knockbackResistance, AttributeModifier.Operation.ADDITION));
		}

		LinkedListMultimap<Attribute, AttributeModifier> builtAttributes = buildAttributes(type);
		return ()->new TArmorItem(material, builtAttributes, protection, toughness, type, properties);
	}

	public LinkedListMultimap<Attribute, AttributeModifier> buildAttributes(ArmorItem.Type type)
	{
		LinkedListMultimap<Attribute, AttributeModifier> atts = LinkedListMultimap.create();
		for(Attribute attribute : attributes.keys())
		{
			for(AttributeModifier modifier : attributes.get(attribute))
			{
				atts.put(attribute, new AttributeModifier(MODIFIERS[type.getSlot().getIndex()], modifier.getName(), modifier.getAmount(), modifier.getOperation()));
			}
		}
		return atts;
	}

	private ArmorBuilder(ArmorMaterial material)
	{
		this.material = material;
		this.toughness = material.getToughness();
		this.knockbackResistance = material.getKnockbackResistance();
	}
}