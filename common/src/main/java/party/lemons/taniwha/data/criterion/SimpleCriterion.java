package party.lemons.taniwha.data.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SimpleCriterion extends SimpleCriterionTrigger<SimpleCriterion.Conditions>
{
    private final ResourceLocation ID;

    public SimpleCriterion(ResourceLocation location)
    {
        ID = location;
    }

    @Override
    protected Conditions createInstance(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return new Conditions(ID, composite);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player)
    {
        this.trigger(player, (conditions)->true);
    }

    public static class Conditions extends AbstractCriterionTriggerInstance
    {
        public Conditions(ResourceLocation resourceLocation, EntityPredicate.Composite composite) {
            super(resourceLocation, composite);
        }
    }
}
