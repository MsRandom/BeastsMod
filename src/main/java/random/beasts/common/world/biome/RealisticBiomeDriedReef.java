package random.beasts.common.world.biome;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import random.beasts.common.init.BeastsBiomes;
import rtg.api.world.RTGWorld;
import rtg.api.world.biome.RealisticBiomeBase;
import rtg.api.world.gen.RTGChunkGenSettings;
import rtg.api.world.surface.SurfaceBase;
import rtg.api.world.terrain.TerrainBase;
import rtg.world.biome.realistic.vanilla.RealisticBiomeVanillaDesert;

public class RealisticBiomeDriedReef extends RealisticBiomeBase {
    public RealisticBiomeDriedReef() {
        super(BeastsBiomes.DRIED_REEF);
    }

    @Override
    public void initConfig() {
        this.getConfig().SURFACE_WATER_LAKE_MULT.set(0.0f);
        this.getConfig().ALLOW_SCENIC_LAKES.set(false);
        this.getConfig().SURFACE_FILLER_BLOCK.set("minecraft:sandstone");
        this.getConfig().addProperty(this.getConfig().ALLOW_CACTUS).set(false);
    }

    @Override
    public TerrainBase initTerrain() {
        return new TerrainDriedReef();
    }

    @Override
    public SurfaceBase initSurface() {
        return new RealisticBiomeVanillaDesert.SurfaceVanillaDesert(getConfig(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
    }

    @Override
    public void rReplace(ChunkPrimer primer, int i, int j, int x, int y, int depth, RTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
        this.rReplaceWithRiver(primer, i, j, x, y, depth, rtgWorld, noise, river, base);
    }

    @Override
    public void initDecos() {
    }

    public static class TerrainDriedReef extends TerrainBase {
        public TerrainDriedReef() {
            super(64);
        }

        @Override
        public float generateNoise(RTGWorld rtgWorld, int x, int y, float border, float river) {
            RTGChunkGenSettings settings = rtgWorld.getGeneratorSettings();
            float duneHeight = (minDuneHeight + settings.sandDuneHeight);
            duneHeight *= (1f + rtgWorld.simplexInstance(2).noise2f(x / 330f, y / 330f)) / 2f;
            float stPitch = 200f;
            float stFactor = duneHeight;
            float hPitch = 70;
            float hDivisor = 40;
            return terrainPolar(x, y, rtgWorld, river, stPitch, stFactor, hPitch, hDivisor, base + 8) + groundNoise(x, y, 1f, rtgWorld);
        }
    }
}
