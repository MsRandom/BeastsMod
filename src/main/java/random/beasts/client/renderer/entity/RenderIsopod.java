package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelAnglerQueen;
import random.beasts.client.model.ModelIsopod;
import random.beasts.client.model.ModelVileEel;
import random.beasts.common.entity.monster.EntityAnglerQueen;
import random.beasts.common.entity.monster.EntityIsopod;
import random.beasts.common.entity.monster.EntityVileEel;

@SideOnly(Side.CLIENT)
public class RenderIsopod extends RenderLiving<EntityIsopod> {
	
    private static final ResourceLocation SPARTAPOD = new ResourceLocation(BeastsReference.ID, "textures/entity/spartapod.png");
    private static final ResourceLocation ATHAPOD = new ResourceLocation(BeastsReference.ID, "textures/entity/athapod.png");

    public RenderIsopod(RenderManager rm) {
        super(rm, new ModelIsopod(), 0.4f);
    }

    protected ResourceLocation getEntityTexture(EntityIsopod entity) {
        return SPARTAPOD;
    }
}