package random.beasts.api.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;

//replaces the old 1.12 generators cause i'm lazy
public interface IBeastsGenerator {
    boolean generate(IWorld world, Random rand, BlockPos pos);
}
