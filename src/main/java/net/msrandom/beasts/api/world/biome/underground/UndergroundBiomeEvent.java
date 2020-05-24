package net.msrandom.beasts.api.world.biome.underground;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Random;

public class UndergroundBiomeEvent extends Event {
    private final World world;
    private final Random rand;
    private final BlockPos pos;
    private final UndergroundBiomeBounds bounds;

    public UndergroundBiomeEvent(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
        this.world = world;
        this.rand = rand;
        this.pos = pos;
        this.bounds = bounds;
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

    public UndergroundBiomeBounds getBounds() {
        return this.bounds;
    }

    public static class Populate extends UndergroundBiomeEvent {
        public Populate(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
            super(world, rand, pos, bounds);
        }
    }

    public static class Decorate extends UndergroundBiomeEvent {
        public Decorate(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
            super(world, rand, pos, bounds);
        }
    }

    public static class Generate extends UndergroundBiomeEvent {
        public Generate(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
            super(world, rand, pos, bounds);
        }
    }
}
