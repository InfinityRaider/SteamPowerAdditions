package com.InfinityRaider.SteamPowerAdditions.utility;

import com.InfinityRaider.SteamPowerAdditions.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class RegisterHelper {
    public static void registerBlock(Block block,String name) {
        RegisterHelper.registerBlock(block, name, null);
    }

    public static void registerBlock(Block block,String name, Class<? extends ItemBlock> itemClass) {
        block.setBlockName(Reference.MOD_ID.toLowerCase() + ':' + name);
        LogHelper.info("registering " + block.getUnlocalizedName());
        if(itemClass!=null) {
            GameRegistry.registerBlock(block, itemClass, name);
        }
        else {
            GameRegistry.registerBlock(block, name);
        }
    }

    public static void registerItem(Item item,String name) {
        item.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ':' + name);
        LogHelper.info("registering " + item.getUnlocalizedName());
        GameRegistry.registerItem(item, name);
    }

    public static void removeRecipe(ItemStack stack) {
        ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();
        ItemStack result;
        for(int i=0;i<recipes.size();i++) {
            IRecipe recipe = (IRecipe) recipes.get(i);
            result = recipe.getRecipeOutput();
            if(result!=null && stack.getItem()==result.getItem() && stack.getItemDamage()==result.getItemDamage()) {
                recipes.remove(i);
            }
        }
    }
}
