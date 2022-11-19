package party.lemons.taniwha.util.collections;

import com.google.common.collect.Lists;
import net.minecraft.util.RandomSource;

import java.util.Collections;
import java.util.List;

public class WeightedList<T>
{
    private final List<Entry<T>> entries = Lists.newArrayList();
    private int totalWeight = 0;

    public WeightedList<T> add(T object, int weight)
    {
        entries.add(new Entry<T>(object, weight));
        totalWeight += weight;

        return this;
    }

    public T sample(RandomSource random)
    {
        Collections.shuffle(entries);
        float p = random.nextFloat() * totalWeight;
        float count = 0;
        for(Entry<T> entry : entries)
        {
            count += entry.weight;
            if(count > p)
                return entry.object;
        }

        throw new RuntimeException("Weighted List Was Empty");
    }


    private record Entry<T>(T object, int weight)
    {
    }
}