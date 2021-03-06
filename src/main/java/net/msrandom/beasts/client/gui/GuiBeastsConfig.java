package net.msrandom.beasts.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.msrandom.beasts.api.configuration.BeastsConfig;
import net.msrandom.beasts.api.main.BeastsReference;

import java.util.stream.Collectors;

public class GuiBeastsConfig extends GuiConfig {
    public GuiBeastsConfig(GuiScreen parent) {
        super(parent, BeastsConfig.config.getCategoryNames().stream().map(name -> new ConfigElement(BeastsConfig.config.getCategory(name))).collect(Collectors.toList()), BeastsReference.ID, true, true, "Beasts Config");
    }
}
