package party.lemons.taniwha.data.criterion;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WearArmorCriterion extends SimpleCriterionTrigger<WearArmorCriterion.Conditions>
{
    public static final List<WearArmorCriterion> CRITERION = Lists.newArrayList();

    private final ResourceLocation ID;

    public WearArmorCriterion(String modid)
    {
        ID = new ResourceLocation(modid, "wear_armor");

        CRITERION.add(this);
    }

    @Override
    protected Conditions createInstance(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {

        ItemPredicate itemPredicates = ItemPredicate.fromJson(jsonObject.get("item"));
        return new Conditions(ID, composite, itemPredicates);
    }

    public void trigger(ServerPlayer player)
    {
        this.trigger(player, (conditions)->
                conditions.matches(player.getArmorSlots()));
    }


    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public static class Conditions extends AbstractCriterionTriggerInstance
    {
        private final ItemPredicate item;


        public Conditions(ResourceLocation resourceLocation, EntityPredicate.Composite composite, ItemPredicate itemPredicate) {
            super(resourceLocation, composite);

            this.item = itemPredicate;
        }

        public boolean matches(Iterable<ItemStack> armorItems)
        {
            for(ItemStack st : armorItems)
            {
                if(item.matches(st)) return true;
            }
            return false;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext serializationContext) {
            JsonObject jsonObject = super.serializeToJson(serializationContext);
            jsonObject.add("item", item.serializeToJson());
            return jsonObject;
        }
    }
}
