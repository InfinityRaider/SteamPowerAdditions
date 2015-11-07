package com.InfinityRaider.SteamPowerAdditions.compatibility.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.InfinityRaider.SteamPowerAdditions.recipe.SmasherRecipeRegistry;
import com.InfinityRaider.SteamPowerAdditions.reference.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NEI_SmasherHandler extends TemplateRecipeHandler {
    public static final int DX = -5;
    public static final int DY = -11;

    private static final String name = StatCollector.translateToLocal("SPA_nei.smasher.title");
    private static final String id = "steamSmasher";
    private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(),"textures/gui/nei/smasher.png");

    public class CachedSmasherRecipe extends TemplateRecipeHandler.CachedRecipe {

        public static final int X = 80+DX;
        public static final int Y1 = 16+DY;
        public static final int Y2 = 53+DY;

        private PositionedStack input;
        private PositionedStack output;

        public CachedSmasherRecipe(ItemStack input, ItemStack output) {
            this.input = new PositionedStack(input, X, Y1);
            this.output = new PositionedStack(output, X, Y2);
        }

        @Override
        public PositionedStack getIngredient() {
            return input;
        }

        //return ingredients
        @Override
        public List<PositionedStack> getIngredients() {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            list.add(input);
            return list;
        }

        //return result
        @Override
        public PositionedStack getResult() {
            return output;
        }
    }

    @Override
    public void loadCraftingRecipes(String id, Object... results) {
        if(id.equalsIgnoreCase(NEI_SmasherHandler.id)) {
            for(ItemStack input : SmasherRecipeRegistry.getInstance().getPossibleInputs()) {
                arecipes.add(new CachedSmasherRecipe(input, SmasherRecipeRegistry.getInstance().getOutput(input)));
            }
        }
        else if(id.equalsIgnoreCase("item")) {
            for (Object object : results) {
                if (object instanceof ItemStack && getClass()==NEI_SmasherHandler.class) {
                    loadCraftingRecipes(new ItemStack(((ItemStack) object).getItem(), 1, ((ItemStack) object).getItemDamage()));
                }
            }
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for(ItemStack input: SmasherRecipeRegistry.getInstance().getInputsForOutput(result)) {
            arecipes.add(new CachedSmasherRecipe(input, result));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        ItemStack output = SmasherRecipeRegistry.getInstance().getOutput(ingredient);
        if(output!=null && output.getItem()!=null) {
            arecipes.add(new CachedSmasherRecipe(ingredient, output));
        }
    }

    //returns the id for this recipe
    @Override
    public String getOverlayIdentifier() {
        return id;
    }

    //gets the texture to display the recipe in
    @Override
    public String getGuiTexture() {
        return texture.toString();
    }

    @Override
    public String getRecipeName() {
        return name;
    }

    @Override
    public int recipiesPerPage() {
        return 2;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(45+DX, 12+DY, 24, 24), id));
        transferRects.add(new RecipeTransferRect(new Rectangle(73+DX, 12+DY, 2, 24), id));
        transferRects.add(new RecipeTransferRect(new Rectangle(69+DX, 22+DY, 4, 4), id));
        transferRects.add(new RecipeTransferRect(new Rectangle(107+DX, 12+DY, 24, 24), id));
        transferRects.add(new RecipeTransferRect(new Rectangle(101+DX, 12+DY, 2, 24), id));
        transferRects.add(new RecipeTransferRect(new Rectangle(103+DX, 22+DY, 4, 4), id));
        transferRects.add(new RecipeTransferRect(new Rectangle(86+DX, 37+DY, 4, 10), id));
    }
}
