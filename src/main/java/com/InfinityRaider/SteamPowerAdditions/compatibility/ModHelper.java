package com.InfinityRaider.SteamPowerAdditions.compatibility;

import com.InfinityRaider.SteamPowerAdditions.compatibility.minetweaker.MineTweakerHelper;
import com.InfinityRaider.SteamPowerAdditions.handler.ConfigurationHandler;
import cpw.mods.fml.common.Loader;

import java.util.HashMap;

public abstract class ModHelper {
    /** HashMap holding all ModHelpers, with the respective mod id as key */
    private static final HashMap<String, ModHelper> modHelpers = new HashMap<String, ModHelper>();

    /** Method to create only one instance for each mod helper */
    private static ModHelper createInstance(Class<? extends ModHelper> clazz) {
        ModHelper helper = null;
        try {
            helper = clazz.newInstance();
        } catch (Exception e) {
            if(ConfigurationHandler.debug) {
                e.printStackTrace();
            }
        }
        if(helper!=null) {
            modHelpers.put(helper.modId(), helper);
        }
        return helper;
    }

    /** Checks if integration for this mod id is allowed, meaning the mod is present, and integration is allowed in the config */
    public static boolean allowIntegration(String modId) {
        ModHelper helper = modHelpers.get(modId);
        if(helper != null ) {
            return helper.allowIntegration();
        } else {
            return Loader.isModLoaded(modId) && ConfigurationHandler.enableModCompatibility(modId);
        }
    }

    /** Checks if integration for this mod id is allowed, meaning the mod is present, and integration is allowed in the config */
    public final boolean allowIntegration() {
        String id =this.modId();
        return Loader.isModLoaded(id) && ConfigurationHandler.enableModCompatibility(id);
    }

    /** called during the initialization phase of FML's mod loading cycle */
    protected abstract void init();

    /** called during the post-initialization phase of FML's mod loading cycle */
    protected abstract void postTasks();

    /** returns the mod id for this mod */
    protected abstract String modId();

    /** calls the init() method for all mod helpers which have their mod loaded and compatibility enabled */
    public static void initHelpers() {
        for(ModHelper helper:modHelpers.values()) {
            String id = helper.modId();
            boolean flag = Loader.isModLoaded(id) && ConfigurationHandler.enableModCompatibility(id);
            if(flag) {
                helper.init();
            }
        }
    }

    /** calls the postInit() method for all mod helpers which have their mod loaded and compatibility enabled */
    public static void postInit() {
        for (ModHelper helper : modHelpers.values()) {
            String id = helper.modId();
            boolean flag = Loader.isModLoaded(id) && ConfigurationHandler.enableModCompatibility(id);
            if(flag) {
                helper.postTasks();
            }
        }
    }

    /** method holding all ModHelper classes */
    public static void findHelpers() {
        Class[] classes = {
                MineTweakerHelper.class
        };
        for(Class clazz:classes) {
            if(ModHelper.class.isAssignableFrom(clazz)) {
                createInstance(clazz);
            }
        }
    }
}
