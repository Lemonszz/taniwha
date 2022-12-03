package party.lemons.taniwha.registry;

public record ModifierContainer<T>(T type, Modifier<T>... modifiers)
{
    @SafeVarargs
    public ModifierContainer
    {
    }

    public void initModifiers()
    {
        for (Modifier<T> modifier : modifiers)
            modifier.accept(type);
    }
}
