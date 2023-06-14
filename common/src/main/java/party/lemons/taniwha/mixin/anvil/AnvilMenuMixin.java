package party.lemons.taniwha.mixin.anvil;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.data.anvil.AnvilRecipe;
import party.lemons.taniwha.data.anvil.AnvilRecipes;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu
{
    @Shadow @Nullable private String itemName;

    @Shadow @Final private DataSlot cost;

    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i, inventory, containerLevelAccess);
    }

    @Inject(at = @At("HEAD"), method = "createResult", cancellable = true)
    private void onCreateResult(CallbackInfo cbi)
    {
        ItemStack item1 = this.inputSlots.getItem(0);
        ItemStack item2 = this.inputSlots.getItem(1);

        if(item1.isEmpty() || item2.isEmpty())
            return;

        for(AnvilRecipe recipe : AnvilRecipes.getRecipes())
        {
            Ingredient ingA = recipe.getIngredientA();
            Ingredient ingB = recipe.getIngredientB();

            if((ingA.test(item1) && ingB.test(item2)))
            {
                ItemStack resultStack = recipe.getResult().copy();
                if(item1.hasTag())
                    resultStack.setTag(item1.getTag().copy());
                int finalCost = recipe.getCost();

                if (this.itemName != null && !Util.isBlank(this.itemName)) {
                    if (!this.itemName.equals(item1.getHoverName().getString())) {
                        finalCost += 1;
                        resultStack.setHoverName(Component.literal(this.itemName));
                    }
                } else if (resultStack.hasCustomHoverName()) {
                    finalCost += 1;
                    resultStack.resetHoverName();
                }

                this.resultSlots.setItem(0, resultStack);
                this.cost.set(finalCost);
                this.broadcastChanges();
                cbi.cancel();
                return;
            }
        }
    }
}
