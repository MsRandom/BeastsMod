package net.msrandom.beasts.client.renderer.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.msrandom.beasts.client.renderer.RenderCoconut;
import net.msrandom.beasts.common.tileentity.TileEntityCoconut;

public class TileEntityCoconutRenderer extends TileEntitySpecialRenderer<TileEntityCoconut> {
    @Override
    public void render(TileEntityCoconut te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        RenderCoconut.render(x, y, z, this::bindTexture, true, -1);
    }
}
