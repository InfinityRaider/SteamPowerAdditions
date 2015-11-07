package com.InfinityRaider.SteamPowerAdditions.item;

import com.InfinityRaider.SteamPowerAdditions.reference.Names;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.steamcraft.tile.TileEntitySmasher;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ItemSmashedOre extends ItemBase {
    private final String[] ORE_NAMES = new String[] {
            "Mithril",
            "Platinum"
    };

    private ArrayList<Integer> detectedOres;

    public ItemSmashedOre() {
        super();
        this.hasSubtypes = true;
    }

    @Override
    protected String getInternalName() {
        return Names.Objects.SMASHED_ORE;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if(detectedOres==null) {
            findOres();
        }
        for(Integer meta:detectedOres) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    private void findOres() {
        this.detectedOres = new ArrayList<Integer>();
        for(int index=0;index<ORE_NAMES.length;index++) {
            ArrayList<ItemStack> stacks = OreDictionary.getOres("ore" + ORE_NAMES[index]);
            if(stacks==null || stacks.size()==0) {
                continue;
            }
            for(ItemStack stack:stacks) {
                if(stack==null || stack.getItem()==null) {
                    continue;
                }
                if(stack.getItem() instanceof ItemBlock) {
                    detectedOres.add(index);
                    ItemStack instance = new ItemStack(this, 1, index);
                    OreDictionary.registerOre("dust" + ORE_NAMES[index], instance);
                    TileEntitySmasher.REGISTRY.registerSmashable("ore" + ORE_NAMES[index], instance);
                    break;
                }
            }
        }
    }
}
