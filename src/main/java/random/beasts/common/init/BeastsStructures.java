package random.beasts.common.init;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import random.beasts.common.config.BeastsConfig;
import random.beasts.common.world.gen.structure.BeastsStructure;
import random.beasts.common.world.gen.structure.WorldGenStructure;

import java.util.ArrayList;
import java.util.List;

public class BeastsStructures {

    public static final List<BeastsStructure> LIST = new ArrayList<>();
    public static final WorldGenStructure[] SHELLS = new WorldGenStructure[4];
    //public static final BeastsStructure RABBIT_VILLAGE = new RabbitVillageGenerator();

    static {
        for (int i = 0; i < SHELLS.length; i++) SHELLS[i] = new WorldGenStructure("shell" + (i + 1));
    }

    public static void init() {
        for (BeastsStructure structure : LIST) {
            if (!(structure instanceof WorldGenStructure)) {
                GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> structure.generate(world, random, world.getHeight(new BlockPos((chunkX * 16) + 8, 0, (chunkZ * 16) + 8))), 0);
                structure.registerer.apply();
            }
        }
        ForgeModContainer.logCascadingWorldGeneration = BeastsConfig.logCascadingWorldGeneration;
    }

    @FunctionalInterface
    public interface StructurePiecesRegisterer {
        void apply();
    }
}
