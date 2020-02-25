package random.beasts.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import random.beasts.common.entity.item.EntityThrownCoconut;
import random.beasts.common.init.BeastsItems;

public class RenderThrownCoconut extends SpriteRenderer<EntityThrownCoconut> {

    public RenderThrownCoconut(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, BeastsItems.COCONUT, Minecraft.getInstance().getItemRenderer());
    }
}
