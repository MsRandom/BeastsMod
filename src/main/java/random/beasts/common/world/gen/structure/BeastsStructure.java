package random.beasts.common.world.gen.structure;

import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.common.init.BeastsStructures;

public abstract class BeastsStructure extends WorldGenerator {

    public final Runnable registerer;

    BeastsStructure(Runnable registerer) {
        this.registerer = registerer;
        BeastsStructures.LIST.add(this);
    }
}
