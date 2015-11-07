package com.InfinityRaider.SteamPowerAdditions.compatibility.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.InfinityRaider.SteamPowerAdditions.reference.Reference;
import com.InfinityRaider.SteamPowerAdditions.utility.LogHelper;

public class NEIConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        //register NEI recipe handler
        LogHelper.debug("Registering NEI recipe handlers");
        //smasher handler
        API.registerRecipeHandler(new NEI_SmasherHandler());
        API.registerUsageHandler(new NEI_SmasherHandler());
        hideItems();
    }

    private static void hideItems() {
        LogHelper.debug("Hiding stuff from NEI");
    }

    @Override
    public String getName() {
        return Reference.MOD_ID+"_NEI";
    }

    @Override
    public String getVersion() {
        return  "1.0";
    }

}