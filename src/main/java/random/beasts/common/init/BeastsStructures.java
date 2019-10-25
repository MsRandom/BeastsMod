package random.beasts.common.init;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import random.beasts.common.config.BeastsConfig;
import random.beasts.common.world.gen.structure.BeastsStructure;

import java.util.ArrayList;
import java.util.List;

public class BeastsStructures {

    public static final List<BeastsStructure> LIST = new ArrayList<>();
    //public static final BeastsStructure RABBIT_VILLAGE = new RabbitVillageGenerator();

    public static void init() {
        for (BeastsStructure structure : LIST) {
            GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> structure.generate(world, random, world.getHeight(new BlockPos((chunkX * 16) + 8, 0, (chunkZ * 16) + 8))), 0);
            structure.registerer.apply();
        }
        ForgeModContainer.logCascadingWorldGeneration = BeastsConfig.logCascadingWorldGeneration;
    }

    @FunctionalInterface
    public interface StructurePiecesRegisterer {
        void apply();
    }
}
