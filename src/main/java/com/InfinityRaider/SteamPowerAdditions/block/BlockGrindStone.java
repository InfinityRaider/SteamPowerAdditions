package com.InfinityRaider.SteamPowerAdditions.block;

import com.InfinityRaider.SteamPowerAdditions.reference.Names;
import com.InfinityRaider.SteamPowerAdditions.renderer.block.RenderBlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BlockGrindStone extends BlockBase {
    protected BlockGrindStone() {
        super(Material.rock);
    }

    @Override
    public RenderBlockBase getRenderer() {
        return null;
    }

    @Override
    protected Class<? extends ItemBlock> getItemBlockClass() {
        return null;
    }

    @Override
    protected String getInternalName() {
        return Names.Objects.GRINDSTONE;
    }
}
