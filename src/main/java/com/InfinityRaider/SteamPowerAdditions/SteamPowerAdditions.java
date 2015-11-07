package com.InfinityRaider.SteamPowerAdditions;

import com.InfinityRaider.SteamPowerAdditions.compatibility.ModHelper;
import com.InfinityRaider.SteamPowerAdditions.handler.ConfigurationHandler;
import com.InfinityRaider.SteamPowerAdditions.init.Blocks;
import com.InfinityRaider.SteamPowerAdditions.init.Items;
import com.InfinityRaider.SteamPowerAdditions.init.Recipes;
import com.InfinityRaider.SteamPowerAdditions.proxy.IProxy;
import com.InfinityRaider.SteamPowerAdditions.reference.Reference;
import com.InfinityRaider.SteamPowerAdditions.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;

@Mod(modid = Reference.MOD_ID,name = Reference.MOD_NAME,version = Reference.VERSION)
public class SteamPowerAdditions {
    @Mod.Instance(Reference.MOD_ID)
    public static SteamPowerAdditions instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        LogHelper.debug("Starting Pre-Initialization");
        proxy.initConfiguration(event);
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        ModHelper.findHelpers();
        Blocks.init();
        Items.init();
        ModHelper.initHelpers();
        LogHelper.debug("Pre-Initialization Complete");
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        LogHelper.debug("Starting Initialization");
        proxy.registerEventHandlers();
        proxy.registerRenderers();
        LogHelper.debug("Initialization Complete");
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        LogHelper.debug("Starting Post-Initialization");
        //Have to do this in postInit because some mods don't register their items/blocks until init
        Recipes.init();
        ModHelper.postInit();
        LogHelper.debug("Post-Initialization Complete");
    }

    @Mod.EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {}

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {}

    @Mod.EventHandler
    public void onMissingMappings(FMLMissingMappingsEvent event) {}
}
