package party.lemons.taniwha.hooks.sign;

import net.minecraft.world.level.block.state.properties.WoodType;

public interface SignTypeHooks
{
    static WoodType registerSign(String name)
    {
        return ((SignTypeHooks)WoodType.OAK).registerType(name);
    }

    WoodType registerType(String name);
}
