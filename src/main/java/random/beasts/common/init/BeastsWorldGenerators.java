package random.beasts.common.init;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;
import random.beasts.api.main.BeastsRegistries;

public class BeastsWorldGenerators {
    //public static BeastsWorldGenerator SHELL = new ShellGenerator();

    public static void init() {
        for (IWorldGenerator generator : BeastsRegistries.GENERATORS.get())
            GameRegistry.registerWorldGenerator(generator, 0);
    }
}
