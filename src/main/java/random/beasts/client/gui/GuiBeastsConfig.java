package random.beasts.client.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import random.beasts.api.configuration.BeastsConfig;

import java.util.stream.Collectors;

public class GuiBeastsConfig extends GuiConfig {
    public GuiBeastsConfig(Screen parent) {
        super(parent, BeastsConfig.config.getCategoryNames().stream().map(name -> new ConfigElement(BeastsConfig.config.getCategory(name))).collect(Collectors.toList()), BeastsMod.MOD_ID, true, true, "Beasts Config");
    }
}
