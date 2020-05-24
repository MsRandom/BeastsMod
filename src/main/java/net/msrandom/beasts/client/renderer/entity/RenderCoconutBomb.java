package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.msrandom.beasts.common.entity.projectile.EntityCoconutBomb;
import net.msrandom.beasts.common.init.BeastsItems;

public class RenderCoconutBomb extends RenderSnowball<EntityCoconutBomb> {

    public RenderCoconutBomb(RenderManager renderManagerIn) {
        super(renderManagerIn, BeastsItems.COCONADE, Minecraft.getMinecraft().getRenderItem());
    }
}
