package random.beasts.api.world.gen.structure;

import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.api.main.BeastsRegistries;

public abstract class BeastsStructure extends WorldGenerator {
    public final Runnable registerer;

    public BeastsStructure(Runnable registerer) {
        this.registerer = registerer;
        BeastsRegistries.STRUCTURES.add(this);
    }
}
