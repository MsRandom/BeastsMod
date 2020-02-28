package random.beasts.api.world.gen.structure;

import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import random.beasts.api.main.BeastsRegistries;

public abstract class BeastsStructure<T extends IFeatureConfig> extends Structure<T> {
    public final Runnable registerer;

    public BeastsStructure(Runnable registerer) {
        super();
        this.registerer = registerer;
        BeastsRegistries.STRUCTURES.add(this);
    }
}
