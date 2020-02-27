package random.beasts.client.renderer.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import random.beasts.client.renderer.RenderCoconut;
import random.beasts.common.tileentity.TileEntityCoconut;

public class TileEntityCoconutRenderer extends TileEntityRenderer<TileEntityCoconut> {
    @Override
    public void render(TileEntityCoconut te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.render(te, x, y, z, partialTicks, destroyStage);
        RenderCoconut.render(x, y, z, this::bindTexture, true, -1);
    }
}
