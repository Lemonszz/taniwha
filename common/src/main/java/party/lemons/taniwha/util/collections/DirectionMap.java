package party.lemons.taniwha.util.collections;

import net.minecraft.core.Direction;

import java.util.HashMap;

public class DirectionMap<T> {

    private HashMap<Direction, T> map;

    public T get(Direction direction)
    {
        return map.getOrDefault(direction, null);
    }

    public void put(Direction direction, T value)
    {
        map.put(direction, value);
    }
}
