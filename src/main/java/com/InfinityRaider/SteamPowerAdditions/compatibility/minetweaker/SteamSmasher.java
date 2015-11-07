package com.InfinityRaider.SteamPowerAdditions.compatibility.minetweaker;

import com.InfinityRaider.SteamPowerAdditions.recipe.SmasherRecipeRegistry;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.steampower.SteamSmasher")
public class SteamSmasher {
    @ZenMethod
    public static void add(IOreDictEntry input, IItemStack output) {
        MineTweakerAPI.apply(new AddOreAction(input.getName(), MineTweakerMC.getItemStack(output)));
    }

    @ZenMethod
    public static void add(IItemStack input, IItemStack output) {
        MineTweakerAPI.apply(new AddAction(MineTweakerMC.getItemStack(input), MineTweakerMC.getItemStack(output)));
    }

    @ZenMethod
    public static void remove(IOreDictEntry input) {
        MineTweakerAPI.apply((new RemoveOreAction(input.getName())));
    }

    @ZenMethod
    public static void remove(IItemStack input) {
        MineTweakerAPI.apply(new RemoveAction(MineTweakerMC.getItemStack(input)));
    }

    @ZenMethod
    public static void removeForOutput(IItemStack output) {
        MineTweakerAPI.apply(new RemoveForOutPutAction(MineTweakerMC.getItemStack(output)));
    }

    private static class AddOreAction implements IUndoableAction {
        private String oreName;
        private ItemStack output;

        public AddOreAction(String ore, ItemStack output) {
            this.oreName = ore;
            this.output = output;
        }

        @Override
        public void apply() {
            SmasherRecipeRegistry.getInstance().registerSmashable(oreName, output);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            SmasherRecipeRegistry.getInstance().removeSmashable(oreName);
        }

        @Override
        public String describe() {
            return "Adding Steam Smasher recipe: " + oreName + " --> " + output.getUnlocalizedName();
        }

        @Override
        public String describeUndo() {
            return "Removing previously added Steam Smasher recipe '" + oreName + " --> " + output.getUnlocalizedName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class AddAction implements IUndoableAction {
        private ItemStack input;
        private ItemStack output;

        public AddAction(ItemStack input, ItemStack output) {
            this.input = input;
            this.output = output;
        }

        @Override
        public void apply() {
            SmasherRecipeRegistry.getInstance().registerSmashable(input, output);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            SmasherRecipeRegistry.getInstance().removeSmashable(input);
        }

        @Override
        public String describe() {
            return "Adding Steam Smasher recipe: " + input.getUnlocalizedName() + " --> " + output.getUnlocalizedName();
        }

        @Override
        public String describeUndo() {
            return "Removing previously added Steam Smasher recipe '" + input.getUnlocalizedName() + " --> " + output.getUnlocalizedName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class RemoveOreAction implements IUndoableAction {
        private String input;
        private ItemStack output;

        public RemoveOreAction(String input) {
            this.input = input;
            this.output = SmasherRecipeRegistry.getInstance().getOutput(OreDictionary.getOres(input).get(0));
        }

        @Override
        public void apply() {
            SmasherRecipeRegistry.getInstance().removeSmashable(input);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            SmasherRecipeRegistry.getInstance().registerSmashable(input, output);
        }

        @Override
        public String describe() {
            return "Removing Steam Smasher recipe for" + input;
        }

        @Override
        public String describeUndo() {
            return "Restoring previously removed Steam Smasher recipe for " + input;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class RemoveAction implements IUndoableAction {
        private ItemStack input;
        private ItemStack output;

        public RemoveAction(ItemStack input) {
            this.input = input;
            this.output = SmasherRecipeRegistry.getInstance().getOutput(input);
        }

        @Override
        public void apply() {
            SmasherRecipeRegistry.getInstance().removeSmashable(input);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            SmasherRecipeRegistry.getInstance().registerSmashable(input, output);
        }

        @Override
        public String describe() {
            return "Removing Steam Smasher recipe for" + input.getUnlocalizedName();
        }

        @Override
        public String describeUndo() {
            return "Restoring previously removed Steam Smasher recipe for " + input.getUnlocalizedName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class RemoveForOutPutAction implements IUndoableAction {
        private ItemStack output;

        public RemoveForOutPutAction(ItemStack output) {
            this.output = output;
            SmasherRecipeRegistry.getInstance().getInputsForOutput(output);
        }

        @Override
        public void apply() {
            SmasherRecipeRegistry.getInstance().removeAllSmashablesForOutput(output);
        }

        @Override
        public boolean canUndo() {
            return false;
        }

        @Override
        public void undo() {
        }

        @Override
        public String describe() {
            return "Removing all Steam Smasher recipe for" + output.getUnlocalizedName();
        }

        @Override
        public String describeUndo() {
            return "Undo not possible";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
