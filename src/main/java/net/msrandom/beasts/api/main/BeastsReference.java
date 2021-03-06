package net.msrandom.beasts.api.main;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class BeastsReference {

    public static final String ID = "beasts";
    public static final String NAME = "Beasts";
    public static final String CURRENT_VERSION = "1.2.2";
    public static final String MIN_VERSION = "1.2.1";
    //this is for quickly getting the version range using CURRENT_VERSION and MIN_VERSION, modify as needed
    public static final String VERSION_RANGE = "[" + MIN_VERSION + ",)";
    public static final String MAIN_PACKAGE = "net.msrandom." + ID + ".";
    public static final String GUI_FACTORY = MAIN_PACKAGE + "client.gui.BeastsGuiFactory";
    public static final String PROXY_LOCATION = MAIN_PACKAGE + "proxy.";
    public static final String COMMON_PROXY = PROXY_LOCATION + "CommonProxy";
    public static final String CLIENT_PROXY = PROXY_LOCATION + "ClientProxy";
    public static final SimpleNetworkWrapper NETWORK_CHANNEL = new SimpleNetworkWrapper(BeastsReference.ID);
}
