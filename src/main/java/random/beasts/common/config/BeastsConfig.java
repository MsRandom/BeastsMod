package random.beasts.common.config;

import net.minecraftforge.common.config.Config;
import random.beasts.common.main.BeastsReference;

@Config(modid = BeastsReference.ID)
public class BeastsConfig {

    @Config.LangKey("config.logCascadingWorldGeneration")
    public static boolean logCascadingWorldGeneration = false;
}
