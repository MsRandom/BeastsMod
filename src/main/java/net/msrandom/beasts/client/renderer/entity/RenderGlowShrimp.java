package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelGlowShrimp;
import net.msrandom.beasts.common.entity.monster.EntityGlowShrimp;

@SideOnly(Side.CLIENT)
public class RenderGlowShrimp extends RenderLiving<EntityGlowShrimp> {

    private static final ResourceLocation SHRIMP = new ResourceLocation(BeastsReference.ID, "textures/entity/glow_shrimp/glow_shrimp.png");

    public RenderGlowShrimp(RenderManager rm) {
        super(rm, new ModelGlowShrimp(), 0.3f);
    }

    protected ResourceLocation getEntityTexture(EntityGlowShrimp entity) {
        return SHRIMP;
    }
}