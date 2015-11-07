package com.InfinityRaider.SteamPowerAdditions.init;

import com.InfinityRaider.SteamPowerAdditions.item.ItemSmashedOre;
import net.minecraft.item.Item;

public class Items {
    public static Item itemSmashedOre;

    public static void init() {
        itemSmashedOre = new ItemSmashedOre();
    }
}