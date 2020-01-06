package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelVileEel;
import random.beasts.common.entity.monster.EntityVileEel;

@SideOnly(Side.CLIENT)
public class RenderVileEel extends RenderLiving<EntityVileEel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/vile_eel.png");

    public RenderVileEel(RenderManager rm) {
        super(rm, new ModelVileEel(), 1.0F);
    }

    @Override
    protected void preRenderCallback(EntityVileEel e, float partialTickTime) {
        GlStateManager.scale(1.5F, 1.5F, 1.5F);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        super.preRenderCallback(e, partialTickTime);
    }

    protected ResourceLocation getEntityTexture(EntityVileEel entity) {
        return TEXTURE;
    }
}