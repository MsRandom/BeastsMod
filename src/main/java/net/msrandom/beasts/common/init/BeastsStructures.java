package net.msrandom.beasts.common.init;

import net.msrandom.beasts.common.world.gen.WorldGenStructure;

public class BeastsStructures {
    public static final WorldGenStructure[] SHELLS = new WorldGenStructure[4];
    //public static final BeastsStructure RABBIT_VILLAGE = new RabbitVillageGenerator();

    static {
        for (int i = 0; i < SHELLS.length; i++) SHELLS[i] = new WorldGenStructure("shell" + (i + 1));
    }
}
