package party.lemons.taniwha.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import party.lemons.taniwha.hooks.block.ComposterHooks;

import java.util.Map;

public class CompostReloadListener extends SimpleJsonResourceReloadListener
{
    public static final String COMPOSTABLE = "compostable";
    private static final Gson GSON = new Gson();

    public CompostReloadListener() {
        super(GSON, COMPOSTABLE);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        ComposterHooks.clearCompost();

        elements.forEach((l, json)->{
            if(json.isJsonArray())
            {
                JsonArray array = json.getAsJsonArray();
                for(JsonElement element : array)
                {
                    if(element.isJsonObject()) {
                        load(element.getAsJsonObject());
                    }
                }
            }
            else
            {
                JsonObject object = json.getAsJsonObject();
                //Empty objects ( {} ) are ignored. This is so you can override a fuel.
                if(!object.entrySet().isEmpty()) {
                    load(object);
                }
            }
        });
    }

    private static void load(JsonObject obj)
    {
        ResourceLocation location = new ResourceLocation(obj.getAsJsonPrimitive("item").getAsString());
        Item item = Registry.ITEM.get(location);
        if (item != null) {
            ComposterHooks.registerCompost(item, obj.getAsJsonPrimitive("chane").getAsFloat());
        }
    }
}
