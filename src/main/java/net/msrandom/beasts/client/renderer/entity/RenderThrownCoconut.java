package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.msrandom.beasts.common.entity.item.EntityThrownCoconut;
import net.msrandom.beasts.common.init.BeastsItems;

public class RenderThrownCoconut extends RenderSnowball<EntityThrownCoconut> {

    public RenderThrownCoconut(RenderManager renderManagerIn) {
        super(renderManagerIn, BeastsItems.COCONUT, Minecraft.getMinecraft().getRenderItem());
    }
}
