package rando.beasts.common.world.gen.structure;

import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RabbitVillageGenerator extends WorldGenerator {

    private static ChunkPos[] structureCoords = new ChunkPos[128];
    private static int generated = 0;

    private static boolean canSpawnStructureAtCoords(World world, int chunkX, int chunkZ) {
        int[] p = getRandomPos(world, chunkX, chunkZ);
        int k = p[0];
        int l = p[1];
        if (chunkX == k && chunkZ == l) return world.getBiomeProvider().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 0, Collections.singletonList(Biomes.PLAINS));
        return false;
    }

    public static BlockPos getNearestStructurePos(World worldIn, BlockPos pos) {
        BlockPos blockpos = null;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(worldIn.getChunkFromBlockCoords(pos).getPos().getBlock(0, 0, 0));
        double d0 = Double.MAX_VALUE;
        for (ChunkPos chunkpos : structureCoords) {
            if(chunkpos != null) {
                mutableBlockPos.setPos((chunkpos.x << 4) + 8, 32, (chunkpos.z << 4) + 8);
                double d1 = mutableBlockPos.distanceSq(pos);
                if (blockpos == null) {
                    blockpos = new BlockPos(mutableBlockPos);
                    d0 = d1;
                } else if (d1 < d0) {
                    blockpos = new BlockPos(mutableBlockPos);
                    d0 = d1;
                }
            }
        }
        return blockpos;
    }

    private static int[] getRandomPos(World worldIn, int a, int b) {
        int j1 = a;
        int k1 = b;
        if (j1 < 0) j1 -= 31;
        if (k1 < 0) k1 -= 31;
        int l1 = j1 / 32;
        int i2 = k1 / 32;
        Random rand = worldIn.setRandomSeed(l1, i2, 1351);
        return new int[] {(l1 * 32) + rand.nextInt(24), (i2 * 32) + rand.nextInt(24)};
    }

    @Nonnull
    private StructureStart getStructureStart(World world, int chunkX, int chunkZ, Random rand) {
        return new RabbitVillageGenerator.Start(world, rand, chunkX, chunkZ);
    }

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
        boolean canSpawn = canSpawnStructureAtCoords(worldIn, position.getX() >> 4, position.getZ() >> 4);
        if(rand.nextInt(1351) == 0) {
            this.getStructureStart(worldIn, position.getX() >> 4, position.getZ() >> 4, rand).generateStructure(worldIn, rand, new StructureBoundingBox(position.getX() - 48, position.getZ() - 48, position.getX() + 48, position.getZ() + 48));
            if(structureCoords.length-1 < generated) structureCoords = Arrays.copyOf(structureCoords, structureCoords.length + 128);
            structureCoords[generated++] = worldIn.getChunkFromBlockCoords(position).getPos();
        }
        return canSpawn;
    }

    public static class Start extends StructureStart {
        private boolean hasMoreThanTwoComponents;

        @SuppressWarnings("unused")
        public Start(){}

        Start(World worldIn, Random rand, int x, int z) {
            super(x, z);
            List<StructureRabbitVillagePieces.PieceWeight> list = StructureRabbitVillagePieces.getStructureVillageWeightedPieceList(rand);
            StructureRabbitVillagePieces.Start start = new StructureRabbitVillagePieces.Start(worldIn.getBiomeProvider(), rand, (x << 4) + 2, (z << 4) + 2, list, 2);
            this.components.add(start);
            start.buildComponent(start, this.components, rand);
            List<StructureComponent> list1 = start.pendingRoads;
            List<StructureComponent> list2 = start.pendingHouses;

            while (!list1.isEmpty() || !list2.isEmpty()) {
                if (list1.isEmpty()) {
                    int i = rand.nextInt(list2.size());
                    StructureComponent component = list2.remove(i);
                    component.buildComponent(start, this.components, rand);
                } else {
                    int j = rand.nextInt(list1.size());
                    StructureComponent component = list1.remove(j);
                    component.buildComponent(start, this.components, rand);
                }
            }

            this.updateBoundingBox();
            int k = 0;
            for (StructureComponent component : this.components) if (!(component instanceof StructureRabbitVillagePieces.Path)) ++k;
            this.hasMoreThanTwoComponents = k > 2;
        }

        public boolean isSizeableStructure() {
            return this.hasMoreThanTwoComponents;
        }

        public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
            tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
        }

        public void readFromNBT(NBTTagCompound tagCompound) {
            super.readFromNBT(tagCompound);
            this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
        }
    }
}
