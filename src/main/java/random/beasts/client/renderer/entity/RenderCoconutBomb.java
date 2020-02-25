package random.beasts.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import random.beasts.common.entity.projectile.EntityCoconutBomb;

public class RenderCoconutBomb extends SpriteRenderer<EntityCoconutBomb> {

    public RenderCoconutBomb(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}
