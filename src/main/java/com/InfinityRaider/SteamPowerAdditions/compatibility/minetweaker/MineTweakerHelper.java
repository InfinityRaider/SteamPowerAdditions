package com.InfinityRaider.SteamPowerAdditions.compatibility.minetweaker;

import com.InfinityRaider.SteamPowerAdditions.compatibility.ModHelper;
import com.InfinityRaider.SteamPowerAdditions.reference.Names;
import minetweaker.MineTweakerAPI;

public class MineTweakerHelper extends ModHelper {
    @Override
    protected void init() {
        MineTweakerAPI.registerClass(SteamSmasher.class);
    }

    @Override
    protected void postTasks() {

    }

    @Override
    protected String modId() {
        return Names.Mods.minetweaker;
    }
}
