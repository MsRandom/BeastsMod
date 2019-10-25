package random.beasts.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import random.beasts.common.entity.projectile.EntityCoconutBomb;
import random.beasts.common.init.BeastsItems;

public class RenderCoconutBomb extends RenderSnowball<EntityCoconutBomb> {

    public RenderCoconutBomb(RenderManager renderManagerIn) {
        super(renderManagerIn, BeastsItems.COCONADE, Minecraft.getMinecraft().getRenderItem());
    }
}
