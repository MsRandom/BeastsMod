package random.beasts.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import random.beasts.common.entity.projectile.EntityCoconutBomb;
import random.beasts.common.init.BeastsItems;

public class RenderCoconutBomb extends RenderSnowball<EntityCoconutBomb> {

    public RenderCoconutBomb(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, BeastsItems.COCONADE, Minecraft.getInstance().getRenderItem());
    }
}
