package party.lemons.taniwha.registry;

public class ModifierContainer<T>
{
    private final Modifier<T>[] modifiers;
    private final T type;

    @SafeVarargs
    public ModifierContainer(T type, Modifier<T>... modifiers)
    {
        this.type = type;
        this.modifiers = modifiers;
    }

    public void initModifiers()
    {
        for(Modifier<T> modifier : modifiers)
            modifier.accept(type);
    }
}
