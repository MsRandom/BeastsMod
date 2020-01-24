package random.beasts.api.main;

public class BeastsReference {

    public static final String ID = "beasts";
    public static final String NAME = "Beasts";
    public static final String CURRENT_VERSION = "1.2";
    public static final String MIN_VERSION = "1.2";
    //this is for quickly getting the version range using CURRENT_VERSION and MIN_VERSION, modify as needed
    public static final String VERSION_RANGE = "[" + CURRENT_VERSION + ",)";
    public static final String PROXY_LOCATION = "random." + ID + ".proxy.";
    public static final String COMMON_PROXY = PROXY_LOCATION + "CommonProxy";
    public static final String CLIENT_PROXY = PROXY_LOCATION + "ClientProxy";
}
