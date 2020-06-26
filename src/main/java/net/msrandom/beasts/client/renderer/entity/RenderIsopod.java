package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelIsopod;
import net.msrandom.beasts.common.entity.monster.EntityIsopod;

@SideOnly(Side.CLIENT)
public class RenderIsopod extends RenderLiving<EntityIsopod> {
	
    private static final ResourceLocation SPARTAPOD = new ResourceLocation(BeastsReference.ID, "textures/entity/spartapod.png");
    private static final ResourceLocation ATHAPOD = new ResourceLocation(BeastsReference.ID, "textures/entity/athapod.png");

    public RenderIsopod(RenderManager rm) {
        super(rm, new ModelIsopod(), 0.4f);
    }

    protected ResourceLocation getEntityTexture(EntityIsopod entity) {
        return entity.isSpartapod() ? SPARTAPOD : ATHAPOD;
    }
}
