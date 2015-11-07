package com.InfinityRaider.SteamPowerAdditions.item;

import com.InfinityRaider.SteamPowerAdditions.utility.LogHelper;
import com.InfinityRaider.SteamPowerAdditions.utility.RegisterHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.steamcraft.Steamcraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public abstract class ItemBase extends Item {
    public ItemBase() {
        super();
        this.setCreativeTab(Steamcraft.tab);
        this.setMaxStackSize(64);
        RegisterHelper.registerItem(this, getInternalName());
    }

    protected abstract String getInternalName();

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        LogHelper.debug("registering icon for: " + this.getUnlocalizedName());
        itemIcon = reg.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf('.')+1));
    }

}
