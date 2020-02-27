package random.beasts.common;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import random.beasts.api.configuration.BeastsConfig;
import random.beasts.proxy.ClientProxy;
import random.beasts.proxy.CommonProxy;

@SuppressWarnings("unused")
@Mod(BeastsMod.MOD_ID)
public class BeastsMod {
    public static final String MOD_ID = "beasts";
    public static final CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public BeastsMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BeastsConfig.CONFIG_SPEC);
    }
}
