package party.lemons.taniwha.data.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
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
    protected Conditions createInstance(JsonObject jsonObject, ContextAwarePredicate contextAwarePredicate, DeserializationContext deserializationContext)
    {
        return new Conditions(ID, contextAwarePredicate);
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
        public Conditions(ResourceLocation resourceLocation, ContextAwarePredicate contextAwarePredicate) {
            super(resourceLocation, contextAwarePredicate);
        }
    }
}
