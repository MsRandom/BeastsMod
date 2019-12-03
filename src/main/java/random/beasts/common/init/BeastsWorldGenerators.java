package random.beasts.common.init;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class BeastsWorldGenerators {

    public static final List<IWorldGenerator> LIST = new ArrayList<>();
    //public static BeastsWorldGenerator SHELL = new ShellGenerator();

    public static void init() {
        for (IWorldGenerator generator : LIST) GameRegistry.registerWorldGenerator(generator, 0);
    }
}
