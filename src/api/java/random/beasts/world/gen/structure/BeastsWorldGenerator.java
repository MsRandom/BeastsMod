package random.beasts.world.gen.structure;

import net.minecraftforge.fml.common.IWorldGenerator;
import random.beasts.init.BeastsRegistries;

public abstract class BeastsWorldGenerator implements IWorldGenerator {

    public BeastsWorldGenerator() {
        BeastsRegistries.GENERATORS.add(this);
    }
}
