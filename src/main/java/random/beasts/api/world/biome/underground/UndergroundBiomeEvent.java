package random.beasts.api.world.biome.underground;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Random;

public class UndergroundBiomeEvent extends Event {
    private final World world;
    private final Random rand;
    private final BlockPos pos;

    public UndergroundBiomeEvent(World world, Random rand, BlockPos pos) {
        this.world = world;
        this.rand = rand;
        this.pos = pos;
    }

    public World getWorld() {
        return this.world;
    }

    public Random getRandom() {
        return this.rand;
    }

    public BlockPos getPosition() {
        return this.pos;
    }

    public static class Populate extends UndergroundBiomeEvent {
        public Populate(World world, Random rand, BlockPos pos) {
            super(world, rand, pos);
        }
    }

    public static class Decorate extends UndergroundBiomeEvent {
        public Decorate(World world, Random rand, BlockPos pos) {
            super(world, rand, pos);
        }
    }

    public static class Generate extends UndergroundBiomeEvent {
        public Generate(World world, Random rand, BlockPos pos) {
            super(world, rand, pos);
        }
    }
}