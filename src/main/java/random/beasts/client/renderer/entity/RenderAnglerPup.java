package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelAnglerPup;
import random.beasts.client.model.ModelAnglerQueen;
import random.beasts.client.model.ModelVileEel;
import random.beasts.common.entity.monster.EntityAnglerQueen;
import random.beasts.common.entity.monster.EntityVileEel;
import random.beasts.common.entity.passive.EntityAnglerPup;

@SideOnly(Side.CLIENT)
public class RenderAnglerPup extends RenderLiving<EntityAnglerPup> {
	
    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/angler_pup.png");

    public RenderAnglerPup(RenderManager rm) {
        super(rm, new ModelAnglerPup(), 0.3f);
    }

    protected ResourceLocation getEntityTexture(EntityAnglerPup entity) {
        return TEXTURE;
    }
}