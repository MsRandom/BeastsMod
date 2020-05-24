package net.msrandom.beasts.api.world.biome.underground;

import java.util.Objects;

public class UndergroundBiomeBounds {
    public final UndergroundBiome biome;
    public final int minX;
    public final byte minY;
    public final int minZ;
    public final int maxX;
    public final byte maxY;
    public final int maxZ;

    public UndergroundBiomeBounds(UndergroundBiome biome, int minX, byte minY, int minZ, int maxX, byte maxY, int maxZ) {
        this.biome = biome;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UndergroundBiomeBounds)) return false;
        UndergroundBiomeBounds that = (UndergroundBiomeBounds) o;
        return minX == that.minX &&
                minY == that.minY &&
                minZ == that.minZ &&
                maxX == that.maxX &&
                maxY == that.maxY &&
                maxZ == that.maxZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
