package random.beasts.client.renderer.entity;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelButterflyfish;
import random.beasts.client.model.ModelSlimeSlug;
import random.beasts.common.entity.passive.EntityButterflyFish;
import random.beasts.common.entity.passive.EntitySlimeSlug;

public class RenderButterflyFish extends RenderLiving<EntityButterflyFish> {

	private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];

	static {
		for (int i = 0; i < TEXTURES.length; i++)
			TEXTURES[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/butterfly_fish/texture_" + (i + 1) + ".png");
	}

	public RenderButterflyFish(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelButterflyfish(), 0.4f);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityButterflyFish entity) {
		return TEXTURES[entity.getVariant()];
	}
}
