package random.beasts.api.world.gen.structure;

import net.minecraftforge.fml.common.IWorldGenerator;
import random.beasts.api.main.BeastsRegistries;

public abstract class BeastsWorldGenerator implements IWorldGenerator {

    public BeastsWorldGenerator() {
        BeastsRegistries.GENERATORS.add(this);
    }
}
