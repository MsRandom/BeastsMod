package random.beasts.common.init;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.api.world.gen.structure.BeastsStructure;
import random.beasts.common.world.gen.structure.WorldGenStructure;

public class BeastsStructures {
    public static final WorldGenStructure[] SHELLS = new WorldGenStructure[4];
    //public static final BeastsStructure RABBIT_VILLAGE = new RabbitVillageGenerator();

    static {
        for (int i = 0; i < SHELLS.length; i++) SHELLS[i] = new WorldGenStructure("shell" + (i + 1));
    }

    public static void init() {
        for (BeastsStructure structure : BeastsRegistries.STRUCTURES) {
            if (!(structure instanceof WorldGenStructure)) {
                GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> structure.generate(world, random, world.getHeight(new BlockPos((chunkX * 16) + 8, 0, (chunkZ * 16) + 8))), 0);
                structure.registerer.run();
            }
        }
    }
}
