package random.beasts.api.world.biome.underground;

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
}
