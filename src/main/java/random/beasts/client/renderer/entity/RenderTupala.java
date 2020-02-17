package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelAnglerQueen;
import random.beasts.client.model.ModelGlowShrimp;
import random.beasts.client.model.ModelIsopod;
import random.beasts.client.model.ModelTupala;
import random.beasts.client.model.ModelVileEel;
import random.beasts.common.entity.monster.EntityAnglerQueen;
import random.beasts.common.entity.monster.EntityGlowShrimp;
import random.beasts.common.entity.monster.EntityIsopod;
import random.beasts.common.entity.monster.EntityVileEel;
import random.beasts.common.entity.passive.EntityTupala;

@SideOnly(Side.CLIENT)
public class RenderTupala extends RenderLiving<EntityTupala> {
	
    private static final ResourceLocation TUPALA = new ResourceLocation(BeastsReference.ID, "textures/entity/tupala.png");

    public RenderTupala(RenderManager rm) {
        super(rm, new ModelTupala(), 0.4f);
    }

    protected ResourceLocation getEntityTexture(EntityTupala entity) {
        return TUPALA;
    }
}