package random.beasts.world.gen.structure;

import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.init.BeastsRegistries;

public abstract class BeastsStructure extends WorldGenerator {

    public final Runnable registerer;

    public BeastsStructure(Runnable registerer) {
        this.registerer = registerer;
        BeastsRegistries.STRUCTURES.add(this);
    }
}
