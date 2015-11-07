package com.InfinityRaider.SteamPowerAdditions.proxy;

import codechicken.nei.api.API;
import com.InfinityRaider.SteamPowerAdditions.compatibility.nei.NEIConfig;
import com.InfinityRaider.SteamPowerAdditions.handler.ConfigurationHandler;
import com.InfinityRaider.SteamPowerAdditions.renderer.block.RenderBlockBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Iterator;

public class ClientProxy extends CommonProxy {

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public World getWorldByDimensionId(int dimension) {
        return FMLClientHandler.instance().getServer().worldServerForDimension(dimension);
    }

    @Override
    public Entity getEntityById(World world, int id) {
        return world.getEntityByID(id);
    }

    @Override
    public void registerRenderers() {
        /*
        //BLOCKS
        //------
        for(Field field:Blocks.class.getDeclaredFields()) {
            if(field.getType().isAssignableFrom(BlockBase.class)) {
                try {
                    Object obj = field.get(null);
                    if(obj!=null) {
                        ((BlockBase) obj).getRenderer();
                    }
                } catch (IllegalAccessException e) {
                    LogHelper.printStackTrace(e);
                }
            }
        }

        //ITEMS
        //-----
        for(Field field: Items.class.getDeclaredFields()) {
            if(field.getType().isAssignableFrom(ItemBase.class)) {
                try {
                    Object obj = field.get(null);
                    if(obj!=null) {
                        ((ItemBase) obj).getItemRenderer();
                    }
                }catch (IllegalAccessException e) {
                    LogHelper.printStackTrace(e);
                }
            }
        }

        LogHelper.debug("Renderers registered");
        */
    }

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
    }

    @Override
    public void initNEI() {
        NEIConfig configNEI = new NEIConfig();
        configNEI.loadConfig();
    }

    @Override
    public void hideItemInNEI(ItemStack stack) {
        Iterator mods = Loader.instance().getActiveModList().iterator();
        ModContainer modContainer;
        while(mods.hasNext()) {
            modContainer = (ModContainer) mods.next();
            if(modContainer.getModId().equalsIgnoreCase("NotEnoughItems")) {
                API.hideItem(stack);
            }
        }
    }

    @Override
    public int getRenderId(Block block) {
        return RenderBlockBase.getRenderId(block);
    }

    @Override
    public void registerVillagerSkin(int id, String resource) {
    }

    @Override
    public void initConfiguration(FMLPreInitializationEvent event) {
        super.initConfiguration(event);
        ConfigurationHandler.initClientConfigs(event);
    }
}
