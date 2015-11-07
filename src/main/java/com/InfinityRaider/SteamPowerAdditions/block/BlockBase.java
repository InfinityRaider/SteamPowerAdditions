package com.InfinityRaider.SteamPowerAdditions.block;

import com.InfinityRaider.SteamPowerAdditions.SteamPowerAdditions;
import com.InfinityRaider.SteamPowerAdditions.renderer.block.RenderBlockBase;
import com.InfinityRaider.SteamPowerAdditions.utility.RegisterHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public abstract class BlockBase extends Block {

    /**
     * The default, base constructor for all AgriCraft blocks.
     * This method runs the super constructor from the block class, then registers the new block with the {@link RegisterHelper}.
     *
     * @param mat the {@link Material} the block is comprised of.
     */
    protected BlockBase(Material mat) {
        super(mat);
        RegisterHelper.registerBlock(this, getInternalName(), getItemBlockClass());
    }

    /**
     * Retrieves the block's renderer.
     *
     * @return the block's renderer.
     */
    @SideOnly(Side.CLIENT)
    public abstract RenderBlockBase getRenderer();

    /**
     * Determines the block's rendering type via {@link SteamPowerAdditions#proxy}
     *
     * @return the block's render type.
     */
    @Override
    public int getRenderType() {
        return SteamPowerAdditions.proxy.getRenderId(this);
    }

    /**
     * Retrieves the block's ItemBlock class, as a generic class bounded by the ItemBlock class.
     *
     * @return the block's class, may be null if no specific ItemBlock class is desired.
     */
    protected abstract Class<? extends ItemBlock> getItemBlockClass();

    /**
     * Retrieves the name of the block used internally.
     *
     * @return the internal name of the block.
     */
    protected abstract String getInternalName();
}
