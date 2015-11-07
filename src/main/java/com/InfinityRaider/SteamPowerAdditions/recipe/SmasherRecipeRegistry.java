package com.InfinityRaider.SteamPowerAdditions.recipe;

import com.InfinityRaider.SteamPowerAdditions.utility.LogHelper;
import flaxbeard.steamcraft.tile.TileEntitySmasher;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public final class SmasherRecipeRegistry extends TileEntitySmasher.SmashablesRegistry {
    public static final int WILDCARD = 32767;
    private static SmasherRecipeRegistry instance;

    private final HashMap<Item, ItemStack> wildcards;
    private final HashMap<String, ItemStack> oreDicts;
    private final HashMap<ItemStack, ItemStack> registry;

    private SmasherRecipeRegistry(TileEntitySmasher.SmashablesRegistry recipeRegistry) {
        wildcards = new HashMap<Item, ItemStack>();
        oreDicts = new HashMap<String, ItemStack>();
        registry = new HashMap<ItemStack, ItemStack>();
        copyEntriesFromOldRegistry(recipeRegistry);
        setNewRegistry();
    }

    public static SmasherRecipeRegistry getInstance() {
        if(instance == null) {
            instance = new SmasherRecipeRegistry(TileEntitySmasher.REGISTRY);
        }
        return instance;
    }

    @Override
    public ItemStack getOutput(ItemStack input) {
        if(input == null) {
            return null;
        } else {
            ItemStack output = null;
            if(input.getItemDamage() == WILDCARD) {
                output = this.wildcards.get(input.getItem());
            } else {
                int[] ids = OreDictionary.getOreIDs(input);
                if(ids != null && ids.length > 0) {
                    for(int id : ids) {
                        output = this.oreDicts.get(OreDictionary.getOreName(id));
                        if (output != null) {
                            break;
                        }
                    }
                }
                if(output == null) {
                    for (Object o : this.registry.entrySet()) {
                        Map.Entry mapEntry = (Map.Entry) o;
                        if (ItemStack.areItemStacksEqual((ItemStack) mapEntry.getKey(), input)) {
                            output = (ItemStack) mapEntry.getValue();
                            if (output != null) {
                                break;
                            }
                        }
                    }
                }
            }

            return ItemStack.copyItemStack(output);
        }
    }

    @Override
    public void registerSmashable(String input, ItemStack output) {
        this.oreDicts.put(input, output);
    }

    @Override
    public void registerSmashable(Block input, ItemStack output) {
        this.registerSmashable(new ItemStack(input, 1, WILDCARD), output);
    }

    @Override
    public void registerSmashable(Item input, ItemStack output) {
        this.registerSmashable(new ItemStack(input, 1, WILDCARD), output);
    }

    @Override
    public void registerSmashable(ItemStack input, ItemStack output) {
        if(input.getItemDamage() == 32767) {
            this.wildcards.put(input.getItem(), output);
        } else {
            this.registry.put(input, output);
        }

    }

    public void removeSmashable(String input) {
        this.oreDicts.remove(input);
    }

    public void removeSmashable(Block input) {
        this.removeSmashable(new ItemStack(input, 1, WILDCARD));
    }

    public void removeSmashable(Item input) {
        this.removeSmashable(new ItemStack(input, 1, WILDCARD));
    }

    public void removeSmashable(ItemStack input) {
        if(input.getItemDamage() == 32767) {
            wildcards.remove(input.getItem());
        } else {
            Iterator<Map.Entry<ItemStack, ItemStack>> iterator = registry.entrySet().iterator();
            while(iterator.hasNext()) {
                ItemStack entryKey = iterator.next().getKey();
                if(ItemStack.areItemStacksEqual(entryKey, input)) {
                    iterator.remove();
                }
            }
        }
    }

    public void removeAllSmashablesForOutput(ItemStack output) {
        Iterator<Map.Entry<Item, ItemStack>> iteratorWildcards = wildcards.entrySet().iterator();
        while(iteratorWildcards.hasNext()) {
            ItemStack entryValue = iteratorWildcards.next().getValue();
            if(ItemStack.areItemStacksEqual(entryValue, output)) {
                iteratorWildcards.remove();
            }
        }

        Iterator<Map.Entry<String, ItemStack>> iteratorOreDicts = oreDicts.entrySet().iterator();
        while(iteratorOreDicts.hasNext()) {
            ItemStack entryValue = iteratorOreDicts.next().getValue();
            if(ItemStack.areItemStacksEqual(entryValue, output)) {
                iteratorOreDicts.remove();
            }
        }

        Iterator<Map.Entry<ItemStack, ItemStack>> iteratorRegistry = registry.entrySet().iterator();
        while(iteratorRegistry.hasNext()) {
            ItemStack entryValue = iteratorRegistry.next().getValue();
            if(ItemStack.areItemStacksEqual(entryValue, output)) {
                iteratorRegistry.remove();
            }
        }
    }

    public List<ItemStack> getInputsForOutput(ItemStack output) {
        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();

        if(output == null || output.getItem() == null) {
            return inputs;
        }

        for (Map.Entry<Item, ItemStack> entry : wildcards.entrySet()) {
            if (ItemStack.areItemStacksEqual(entry.getValue(), output)) {
                inputs.add(new ItemStack(entry.getKey(), 1, WILDCARD));
            }
        }

        for(Map.Entry<String, ItemStack> entry : oreDicts.entrySet()) {
            if (ItemStack.areItemStacksEqual(entry.getValue(), output)) {
                inputs.addAll(OreDictionary.getOres(entry.getKey()));
            }
        }

        for(Map.Entry<ItemStack, ItemStack> entry : registry.entrySet()) {
            if (ItemStack.areItemStacksEqual(entry.getValue(), output)) {
                inputs.add(entry.getKey().copy());
            }
        }

        return inputs;
    }

    public List<ItemStack> getPossibleInputs() {
        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();

        for (Map.Entry<Item, ItemStack> entry : wildcards.entrySet()) {
            inputs.add(new ItemStack(entry.getKey(), 1, WILDCARD));
        }

        for(Map.Entry<String, ItemStack> entry : oreDicts.entrySet()) {
            inputs.addAll(OreDictionary.getOres(entry.getKey()));
        }

        for(Map.Entry<ItemStack, ItemStack> entry : registry.entrySet()) {
            inputs.add(entry.getKey().copy());
        }

        return inputs;
    }

    private void copyEntriesFromOldRegistry(TileEntitySmasher.SmashablesRegistry recipeRegistry) {
        for(Field field:recipeRegistry.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("wildcards")) {
                try {
                    Map<Item, ItemStack> oldWildcards = (Map<Item, ItemStack>) field.get(recipeRegistry);
                    wildcards.putAll(oldWildcards);
                } catch (Exception e) {
                    LogHelper.printStackTrace(e);
                }
            } else if (field.getName().equals("oreDicts")) {
                try {
                    Map<String, ItemStack> oldOreDicts = (Map<String, ItemStack>) field.get(recipeRegistry);
                    oreDicts.putAll(oldOreDicts);
                } catch (Exception e) {
                    LogHelper.printStackTrace(e);
                }
            } else if (field.getName().equals("registry")) {
                try {
                    Map<ItemStack, ItemStack> oldRegistry = (Map<ItemStack, ItemStack>) field.get(recipeRegistry);
                    registry.putAll(oldRegistry);
                } catch (Exception e) {
                    LogHelper.printStackTrace(e);
                }
            }
        }
    }

    private void setNewRegistry() {
        try {
            Field field = TileEntitySmasher.class.getDeclaredField("REGISTRY");
            field.setAccessible(true);

            Field fieldModifiers = Field.class.getDeclaredField("modifiers");
            fieldModifiers.setAccessible(true);
            fieldModifiers.setInt(field, field.getModifiers() &~ Modifier.FINAL);

            field.set(null, this);
        } catch (Exception e) {
            LogHelper.printStackTrace(e);
        }
    }
}
