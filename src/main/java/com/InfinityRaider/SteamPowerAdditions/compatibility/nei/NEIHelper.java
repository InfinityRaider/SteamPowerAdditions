package com.InfinityRaider.SteamPowerAdditions.compatibility.nei;

import com.InfinityRaider.SteamPowerAdditions.SteamPowerAdditions;
import com.InfinityRaider.SteamPowerAdditions.compatibility.ModHelper;
import com.InfinityRaider.SteamPowerAdditions.reference.Names;

public class NEIHelper extends ModHelper {
    @Override
    protected void init() {}

    @Override
    protected void postTasks() {
        SteamPowerAdditions.proxy.initNEI();
    }

    @Override
    protected String modId() {
        return Names.Mods.nei;
    }
}
