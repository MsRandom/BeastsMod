package random.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class ModelNone extends ModelBase {

    public ModelNone() {
        this.textureWidth = 64;
        this.textureHeight = 32;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    }

}
