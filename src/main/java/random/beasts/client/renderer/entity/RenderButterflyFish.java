package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelButterflyFish;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityButterflyFish;

import javax.annotation.Nullable;

public class RenderButterflyFish extends MobRenderer<EntityButterflyFish, ModelButterflyFish> {

	private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];

	static {
		for (int i = 0; i < TEXTURES.length; i++)
			TEXTURES[i] = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/butterfly_fish/texture_" + (i + 1) + ".png");
	}

	public RenderButterflyFish(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, new ModelButterflyFish(), 0.4f);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityButterflyFish entity) {
		return TEXTURES[entity.getVariant()];
	}
}
