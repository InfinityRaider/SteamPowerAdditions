package com.InfinityRaider.SteamPowerAdditions.handler;

import com.InfinityRaider.SteamPowerAdditions.utility.LogHelper;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {
    public static final String CATEGORY_COMPATIBILITY = "compatibility";
    public static final String CATEGORY_GRINDSTONE = "Grind Stone";
    public static final String CATEGORY_DEBUG = "Debug";

    public static Configuration config;
    //grindstone
    public static boolean enableGrindstone;
    public static float doublingChance;
    public static String preferredModId;
    //debug
    public static boolean debug;

    public static void init(FMLPreInitializationEvent event) {
        if(config == null) {
            config = new Configuration(event.getSuggestedConfigurationFile());
            loadConfiguration();
        }
        LogHelper.debug("Configuration Loaded");
    }

    @SideOnly(Side.CLIENT)
    public static void initClientConfigs(FMLPreInitializationEvent event) {
    }

    private static void loadConfiguration() {
        //grindstone
        enableGrindstone = config.getBoolean("Enable", CATEGORY_GRINDSTONE, true, "set to false to disable the grindstone");
        doublingChance = config.getFloat("Doubling Chance", CATEGORY_GRINDSTONE, 0.5F, 0, 1, "Chance to double ores in the grindstone");
        preferredModId = config.getString("Preferred Mod Dust", CATEGORY_GRINDSTONE, "Steamcraft", "The prefererd Mod id for the dust to which the grindstone grinds ores");
        //debug
        debug = config.getBoolean("Debug Mode", CATEGORY_DEBUG, false, "set to true to enableGrindstone debug mode");
        if(config.hasChanged()) {config.save();}
    }

    public static boolean enableModCompatibility(String modId) {
        boolean flag = config.getBoolean(modId, CATEGORY_COMPATIBILITY, true, "set to false to disable compatibility for "+modId);
        if(config.hasChanged()) {
            config.save();
        }
        return flag;
    }
}
