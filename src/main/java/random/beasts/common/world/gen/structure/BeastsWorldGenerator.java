package random.beasts.common.world.gen.structure;

import net.minecraftforge.fml.common.IWorldGenerator;
import random.beasts.common.init.BeastsWorldGenerators;

public abstract class BeastsWorldGenerator implements IWorldGenerator {

    BeastsWorldGenerator() {
        BeastsWorldGenerators.LIST.add(this);
    }
}
