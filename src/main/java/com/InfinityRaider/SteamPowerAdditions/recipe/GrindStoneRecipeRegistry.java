package com.InfinityRaider.SteamPowerAdditions.recipe;

import com.InfinityRaider.SteamPowerAdditions.handler.ConfigurationHandler;

public class GrindStoneRecipeRegistry {
    private double doublingChance;

    private GrindStoneRecipeRegistry() {
        this.doublingChance = ConfigurationHandler.doublingChance;
    }
}
