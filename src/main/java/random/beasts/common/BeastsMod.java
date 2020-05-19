package random.beasts.common;

import net.minecraft.block.BlockFlowerPot;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import random.beasts.api.main.BeastsReference;
import random.beasts.proxy.CommonProxy;

@SuppressWarnings("unused")
@Mod(modid = BeastsReference.ID, name = BeastsReference.NAME, version = BeastsReference.CURRENT_VERSION, acceptableRemoteVersions = BeastsReference.VERSION_RANGE, guiFactory = BeastsReference.GUI_FACTORY)
public class BeastsMod {

    @SidedProxy(serverSide = BeastsReference.COMMON_PROXY, clientSide = BeastsReference.CLIENT_PROXY)
    public static CommonProxy proxy;
    @Mod.Instance(BeastsReference.ID)
    public static BeastsMod instance;
    private static Logger logger;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
    }

    public static Logger getLogger() {
        return logger;
    }
}
