package rando.beasts.common.config;

import net.minecraftforge.common.config.Config;
import rando.beasts.common.main.BeastsReference;

@Config(modid = BeastsReference.ID)
public class BeastsConfig {

    @Config.LangKey("config.logCascadingWorldGeneration")
    public static boolean logCascadingWorldGeneration = false;
}
