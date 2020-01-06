package random.beasts.common.world.gen.structure;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import random.beasts.common.entity.passive.EntityRabbitman;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
public class StructureRabbitVillagePieces {

    public static void register() {
        MapGenStructureIO.registerStructure(RabbitVillageGenerator.Start.class, "RabbitVillage");
        MapGenStructureIO.registerStructureComponent(House.class, "RVBH");
        MapGenStructureIO.registerStructureComponent(Field.class, "RVDF");
        MapGenStructureIO.registerStructureComponent(Start.class, "RVStart");
        MapGenStructureIO.registerStructureComponent(Path.class, "RVSR");
        MapGenStructureIO.registerStructureComponent(Pen.class, "RVP");
    }

    static List<PieceWeight> getStructureVillageWeightedPieceList(Random random) {
        List<PieceWeight> list = new ArrayList<>();
        list.add(new PieceWeight(House.class, 8, MathHelper.getInt(random, 3, 5)));
        list.add(new PieceWeight(Field.class, 5, MathHelper.getInt(random, 3, 5)));
        list.removeIf(pieceWeight -> pieceWeight.villagePiecesLimit == 0);
        return list;
    }

    private static int updatePieceWeight(List<PieceWeight> structureVillageWeightedPieceList) {
        boolean flag = false;
        int i = 0;

        for (PieceWeight weight : structureVillageWeightedPieceList) {
            if (weight.villagePiecesLimit > 0 && weight.villagePiecesSpawned < weight.villagePiecesLimit) {
                flag = true;
            }

            i += weight.villagePieceWeight;
        }

        return flag ? i : -1;
    }

    private static Village findAndCreateComponentFactory(Start start, PieceWeight weight, List<StructureComponent> structureComponents, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType) {
        return weight.villagePieceClass == House.class ? House.createPiece(start, structureComponents, structureMinX, structureMinY, structureMinZ, facing, componentType) : weight.villagePieceClass == Field.class ? Field.createPiece(start, structureComponents, structureMinX, structureMinY, structureMinZ, facing, componentType) : null;
    }

    private static Village generateComponent(Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType) {
        int i = updatePieceWeight(start.structureVillageWeightedPieceList);

        if (i <= 0) {
            return null;
        } else {
            int j = 0;
            while (j < 5) {
                ++j;
                int k = rand.nextInt(i);
                for (PieceWeight weight : start.structureVillageWeightedPieceList) {
                    k -= weight.villagePieceWeight;
                    if (k < 0) {
                        if (weight.canSpawnMoreVillagePieces() || weight == start.lastPlaced && start.structureVillageWeightedPieceList.size() > 1) {
                            break;
                        }
                        Village piece = findAndCreateComponentFactory(start, weight, structureComponents, structureMinX, structureMinY, structureMinZ, facing, componentType);
                        if (piece != null) {
                            ++weight.villagePiecesSpawned;
                            start.lastPlaced = weight;
                            if (weight.canSpawnMoreVillagePieces()) {
                                start.structureVillageWeightedPieceList.remove(weight);
                            }
                            return piece;
                        }
                    }
                }
            }
            return null;
        }
    }

    private static StructureComponent generateAndAddComponent(Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType) {
        if (componentType > 50) {
            return null;
        } else if (Math.abs(structureMinX - start.getBoundingBox().minX) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ) <= 112) {
            StructureComponent structurecomponent = generateComponent(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType + 1);

            if (structurecomponent != null) {
                structureComponents.add(structurecomponent);
                start.pendingHouses.add(structurecomponent);
                return structurecomponent;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static void generateAndAddRoadPiece(Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int type) {
        if (type <= 3 + start.terrainType && Math.abs(structureMinX - start.getBoundingBox().minX) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ) <= 112) {
            StructureBoundingBox structureboundingbox = Path.findPieceBox(structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing);
            if (structureboundingbox != null && structureboundingbox.minY > 10) {
                StructureComponent component = new Path(start, type, structureboundingbox, facing);
                structureComponents.add(component);
                start.pendingRoads.add(component);
            }
        }
    }

    public static class Field extends Village {
        private BlockCrops crop = (BlockCrops) Blocks.CARROTS;

        public Field() {
        }

        Field(Start start, int type, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
            super(start, type);
            this.setCoordBaseMode(facing);
            this.boundingBox = structureBoundingBox;
        }

        static Field createPiece(Start start, List<StructureComponent> structureComponents, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 13, 4, 9, facing);
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(structureComponents, structureboundingbox) == null ? new Field(start, componentType, structureboundingbox, facing) : null;
        }

        public boolean addComponentParts(@Nonnull World worldIn, @Nonnull Random randomIn, @Nonnull StructureBoundingBox structureBoundingBoxIn) {
            super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
            for (int i = 0; i < 2; i++) {
                EntityRabbit entity = new EntityRabbit(worldIn);
                entity.setLocationAndAngles(getXWithOffset(2 + (i * 3), 2) + 0.5D, getYWithOffset(1), getZWithOffset(2 + (i * 3), 2) + 0.5D, 0.0F, 0.0F);
                entity.onInitialSpawn(worldIn.getDifficultyForLocation(entity.getPosition()), null);
                worldIn.spawnEntity(entity);
            }
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 7, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE), Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE), false);

            for (int i = 2; i <= 6; ++i)
                for (int n = 2; n <= 6; ++n)
                    if (i != 4 || n != 4) {
                        int j = this.crop.getMaxAge();
                        int k = j / 3;
                        int r = MathHelper.getInt(randomIn, k, j);
                        this.setBlockState(worldIn, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, r), n, 0, i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, this.crop.getStateFromMeta(r), n, 1, i, structureBoundingBoxIn);
                    }

            this.setBlockState(worldIn, Blocks.WATER.getDefaultState(), 4, 0, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.WATERLILY.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);

            for (int j2 = 0; j2 < 7; ++j2) {
                for (int k2 = 0; k2 < 13; ++k2) {
                    this.clearCurrentPositionBlocksUpwards(worldIn, k2, 4, j2, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), k2, -1, j2, structureBoundingBoxIn);
                }
            }
            return true;
        }
    }

    public static class House extends Village {

        public House() {
        }

        House(Start start, int type, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
            super(start, type);
            this.setCoordBaseMode(facing.getOpposite());
            this.boundingBox = structureBoundingBox;
        }

        static House createPiece(Start start, List<StructureComponent> structureComponents, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 9, 9, 6, facing);
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(structureComponents, structureboundingbox) == null ? new House(start, componentType, structureboundingbox, facing) : null;
        }

        public boolean addComponentParts(@Nonnull World worldIn, @Nonnull Random randomIn, @Nonnull StructureBoundingBox structureBoundingBoxIn) {
            super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
            IBlockState air = Blocks.AIR.getDefaultState();
            IBlockState clay = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
            IBlockState ladder = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.NORTH);
            IBlockState leaves = Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, false);
            IBlockState bed = Blocks.BED.getDefaultState();
            IBlockState torch = Blocks.TORCH.getDefaultState();
            IBlockState ground = getBlockStateFromPos(worldIn, 3, 4, 3, structureBoundingBoxIn);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 5, 12, 5, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 1, 12, 1, air, air, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 1, 5, 12, 1, air, air, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 1, 12, 5, air, air, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 5, 5, 12, 5, air, air, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 2, 4, 7, 4, air, air, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 8, 1, 5, 11, 5, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 8, 0, 4, 11, 0, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 8, 6, 4, 11, 6, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 8, 2, 0, 11, 4, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 8, 2, 6, 11, 4, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 9, 1, 5, 11, 5, air, air, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 8, 1, 1, 11, 1, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 8, 1, 5, 11, 1, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 8, 5, 1, 11, 5, clay, clay, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 8, 5, 5, 11, 5, clay, clay, false);
            setBlockState(worldIn, ground, 1, 4, 1, structureBoundingBoxIn);
            setBlockState(worldIn, ground, 5, 4, 1, structureBoundingBoxIn);
            setBlockState(worldIn, ground, 1, 4, 5, structureBoundingBoxIn);
            setBlockState(worldIn, ground, 5, 4, 5, structureBoundingBoxIn);
            makeTable(worldIn, 4, 5, structureBoundingBoxIn);
            makeTable(worldIn, 5, 9, structureBoundingBoxIn);
            setBlockState(worldIn, bed, 3, 9, 4, structureBoundingBoxIn);
            setBlockState(worldIn, bed.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 3, 9, 5, structureBoundingBoxIn);
            for (int i = 0; i < 2; i++) {
                TileEntity tile = worldIn.getTileEntity(new BlockPos(this.getXWithOffset(3, 4 + i), this.getYWithOffset(9), this.getZWithOffset(3, 4 + i)));
                if (tile instanceof TileEntityBed) ((TileEntityBed) tile).setColor(EnumDyeColor.ORANGE);
            }
            setBlockState(worldIn, torch.withProperty(BlockTorch.FACING, EnumFacing.SOUTH), 3, 10, 5, structureBoundingBoxIn);
            setBlockState(worldIn, torch.withProperty(BlockTorch.FACING, EnumFacing.NORTH), 3, 10, 1, structureBoundingBoxIn);
            setBlockState(worldIn, torch.withProperty(BlockTorch.FACING, EnumFacing.WEST), 5, 10, 3, structureBoundingBoxIn);
            setBlockState(worldIn, torch.withProperty(BlockTorch.FACING, EnumFacing.EAST), 1, 10, 3, structureBoundingBoxIn);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 5, 2, 3, 8, 2, ladder, ladder, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 13, 2, 3, 13, 4, leaves, leaves, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 13, 3, 4, 13, 3, leaves, leaves, false);
            setBlockState(worldIn, leaves, 3, 14, 3, structureBoundingBoxIn);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 15, 1, 3, 15, 5, leaves, leaves, false);
            fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 3, 5, 15, 3, leaves, leaves, false);
            setBlockState(worldIn, air, 3, 15, 3, structureBoundingBoxIn);
            setBlockState(worldIn, leaves, 3, 14, 0, structureBoundingBoxIn);
            setBlockState(worldIn, leaves, 3, 14, 6, structureBoundingBoxIn);
            setBlockState(worldIn, leaves, 6, 14, 3, structureBoundingBoxIn);
            setBlockState(worldIn, leaves, 0, 14, 3, structureBoundingBoxIn);
            createVillageDoor(worldIn, structureBoundingBoxIn, randomIn);
            for (int l = 0; l < 6; ++l)
                for (int k = 0; k < 6; ++k)
                    clearCurrentPositionBlocksUpwards(worldIn, k, 16, l, structureBoundingBoxIn);
            this.spawnRabbitmen(worldIn, structureBoundingBoxIn);
            return true;
        }

        private void makeTable(World worldIn, int x, int y, StructureBoundingBox boundingBox) {
            IBlockState stairs = Blocks.ACACIA_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
            setBlockState(worldIn, Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), x, y, 3, boundingBox);
            setBlockState(worldIn, stairs.withProperty(BlockStairs.FACING, EnumFacing.SOUTH), x, y, 2, boundingBox);
            setBlockState(worldIn, stairs.withProperty(BlockStairs.FACING, EnumFacing.NORTH), x, y, 4, boundingBox);
            setBlockState(worldIn, Blocks.FLOWER_POT.getDefaultState().withProperty(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerType.ORANGE_TULIP), x, y + 1, 4, boundingBox);
            TileEntity tile = worldIn.getTileEntity(new BlockPos(this.getXWithOffset(x, 4), this.getYWithOffset(y + 1), this.getZWithOffset(x, 4)));
            if (tile instanceof TileEntityFlowerPot)
                ((TileEntityFlowerPot) tile).setItemStack(new ItemStack(Blocks.RED_FLOWER, 1, 5));
        }
    }

    public static class Path extends Village {
        private int length;

        public Path() {
        }

        Path(Start start, int type, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
            super(start, type);
            this.setCoordBaseMode(facing);
            this.boundingBox = structureBoundingBox;
            this.length = Math.max(structureBoundingBox.getXSize(), structureBoundingBox.getZSize());
        }

        static StructureBoundingBox findPieceBox(List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing) {
            for (int i = 7 * MathHelper.getInt(rand, 3, 5); i >= 7; i -= 7) {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, 3, 3, i, facing);
                if (StructureComponent.findIntersecting(structureComponents, structureboundingbox) == null)
                    return structureboundingbox;
            }

            return null;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound) {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInteger("Length", this.length);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, @Nonnull TemplateManager templateManager) {
            super.readStructureFromNBT(tagCompound, templateManager);
            this.length = tagCompound.getInteger("Length");
        }

        public void buildComponent(StructureComponent componentIn, List<StructureComponent> structureComponents, Random rand) {
            boolean flag = false;

            for (int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5)) {
                StructureComponent component = this.getNextComponentNN((Start) componentIn, structureComponents, rand, i);
                if (component != null) {
                    i += Math.max(component.getBoundingBox().getXSize(), component.getBoundingBox().getZSize());
                    flag = true;
                }
            }

            for (int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5)) {
                StructureComponent component = this.getNextComponentPP((Start) componentIn, structureComponents, rand, j);
                if (component != null) {
                    j += Math.max(component.getBoundingBox().getXSize(), component.getBoundingBox().getZSize());
                    flag = true;
                }
            }

            EnumFacing enumfacing = this.getCoordBaseMode();

            if (flag && rand.nextInt(3) > 0 && enumfacing != null) {
                switch (enumfacing) {
                    case NORTH:
                    default:
                        StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
                        break;
                    case SOUTH:
                        StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType());
                        break;
                    case WEST:
                        StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.SOUTH, this.getComponentType());
                        break;
                    case EAST:
                        StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.SOUTH, this.getComponentType());
                }
            }

            if (flag && rand.nextInt(3) > 0 && enumfacing != null) {
                switch (enumfacing) {
                    case NORTH:
                    default:
                        StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
                        break;
                    case SOUTH:
                        StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType());
                        break;
                    case WEST:
                        StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                        break;
                    case EAST:
                        StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
            }
        }

        public boolean addComponentParts(@Nonnull World worldIn, @Nonnull Random randomIn, @Nonnull StructureBoundingBox structureBoundingBoxIn) {
            super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
            IBlockState path = Blocks.GRASS_PATH.getDefaultState();
            IBlockState clay = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
            IBlockState gravel = Blocks.GRAVEL.getDefaultState();
            IBlockState cobble = Blocks.COBBLESTONE.getDefaultState();
            for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i) {
                for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j) {
                    BlockPos pos = new BlockPos(i, 64, j);

                    if (structureBoundingBoxIn.isVecInside(pos)) {
                        pos = worldIn.getTopSolidOrLiquidBlock(pos).down().add(1, 0, 1);
                        if (pos.getY() < worldIn.getSeaLevel())
                            pos = new BlockPos(pos.getX(), worldIn.getSeaLevel() - 1, pos.getZ());

                        while (pos.getY() >= worldIn.getSeaLevel() - 1) {
                            IBlockState state = worldIn.getBlockState(pos);
                            if (state.getBlock() == Blocks.GRASS && worldIn.isAirBlock(pos.up())) {
                                worldIn.setBlockState(pos, path, 2);
                                break;
                            }
                            if (state.getMaterial().isLiquid()) {
                                worldIn.setBlockState(pos, clay, 2);
                                break;
                            }
                            if (state.getBlock() == Blocks.SAND || state.getBlock() == Blocks.SANDSTONE || state.getBlock() == Blocks.RED_SANDSTONE) {
                                worldIn.setBlockState(pos, gravel, 2);
                                worldIn.setBlockState(pos.down(), cobble, 2);
                                break;
                            }
                            pos = pos.down();
                        }
                    }
                }
            }
            return true;
        }
    }

    static class PieceWeight {
        final int villagePieceWeight;
        Class<? extends Village> villagePieceClass;
        int villagePiecesSpawned;
        int villagePiecesLimit;

        PieceWeight(Class<? extends Village> aClass, int weight, int limit) {
            this.villagePieceClass = aClass;
            this.villagePieceWeight = weight;
            this.villagePiecesLimit = limit;
        }

        boolean canSpawnMoreVillagePieces() {
            return this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit;
        }
    }

    public static class Start extends Pen {
        BiomeProvider biomeProvider;
        int terrainType;
        PieceWeight lastPlaced;

        List<PieceWeight> structureVillageWeightedPieceList;
        List<StructureComponent> pendingHouses = new ArrayList<>();
        List<StructureComponent> pendingRoads = new ArrayList<>();
        Biome biome;

        public Start() {
        }

        Start(BiomeProvider biomeProviderIn, Random rand, int x, int z, List<PieceWeight> weights, int terrainType) {
            super(null, 0, rand, x, z);
            this.biomeProvider = biomeProviderIn;
            this.structureVillageWeightedPieceList = weights;
            this.terrainType = terrainType;
            Biome biome = biomeProviderIn.getBiome(new BlockPos(x, 0, z), Biomes.DEFAULT);
            this.biome = biome;
            this.startPiece = this;
            if (biome instanceof BiomeDesert) this.structureType = 1;
            else if (biome instanceof BiomeSavanna) this.structureType = 2;
            else if (biome instanceof BiomeTaiga) this.structureType = 3;
            this.setStructureType(this.structureType);
        }
    }

    public abstract static class Village extends StructureComponent {
        int averageGroundLvl = -1;
        int structureType;
        Start startPiece;

        public Village() {
        }

        Village(Start start, int type) {
            super(type);
            if (start != null) {
                this.structureType = start.structureType;
                startPiece = start;
            }
        }

        static boolean canVillageGoDeeper(StructureBoundingBox structureBoundingBox) {
            return structureBoundingBox != null && structureBoundingBox.minY > 10;
        }

        @Override
        public boolean addComponentParts(@Nonnull World worldIn, @Nonnull Random randomIn, @Nonnull StructureBoundingBox structureBoundingBoxIn) {
            if (this.averageGroundLvl < 0) {
                this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
                if (this.averageGroundLvl < 0) return true;
                this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 3, 0);
            }
            return false;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound) {
            tagCompound.setInteger("HPos", this.averageGroundLvl);
            tagCompound.setByte("Type", (byte) this.structureType);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, @Nonnull TemplateManager templateManager) {
            this.averageGroundLvl = tagCompound.getInteger("HPos");
            this.structureType = tagCompound.getByte("Type");
        }

        @Nullable
        StructureComponent getNextComponentNN(Start start, List<StructureComponent> structureComponents, Random rand, int x) {
            EnumFacing facing = this.getCoordBaseMode();
            if (facing != null) {
                if (facing == EnumFacing.WEST) {
                    return StructureRabbitVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + x, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                }
                return StructureRabbitVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + x, EnumFacing.WEST, this.getComponentType());
            }
            return null;
        }

        @Nullable
        StructureComponent getNextComponentPP(Start start, List<StructureComponent> structureComponents, Random rand, int x) {
            EnumFacing facing = this.getCoordBaseMode();
            if (facing != null) {
                if (facing == EnumFacing.WEST) {
                    return StructureRabbitVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + x, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
                return StructureRabbitVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + x, EnumFacing.EAST, this.getComponentType());
            }
            return null;
        }

        int getAverageGroundLevel(World worldIn, StructureBoundingBox structureBoundingBox) {
            int i = 0;
            int j = 0;
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k) {
                for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l) {
                    pos.setPos(l, 64, k);
                    if (structureBoundingBox.isVecInside(pos)) {
                        i += Math.max(worldIn.getTopSolidOrLiquidBlock(pos).getY(), worldIn.provider.getAverageGroundLevel() - 1);
                        ++j;
                    }
                }
            }
            return j == 0 ? -1 : i / j;
        }

        void spawnRabbitmen(World worldIn, StructureBoundingBox structureBoundingBox) {
            for (int i = 0; i < 2; ++i) {
                int j = this.getXWithOffset(2 + i, 2);
                int k = this.getYWithOffset(6);
                int l = this.getZWithOffset(2 + i, 2);
                if (!structureBoundingBox.isVecInside(new BlockPos(j, k, l))) break;
                EntityRabbitman entity = new EntityRabbitman(worldIn);
                entity.setLocationAndAngles((double) j + 0.5D, k, (double) l + 0.5D, 0.0F, 0.0F);
                entity.onInitialSpawn(worldIn.getDifficultyForLocation(entity.getPos()), null);
                worldIn.spawnEntity(entity);
            }
        }

        void createVillageDoor(World world, StructureBoundingBox structureBoundingBox, Random rand) {
            this.generateDoor(world, structureBoundingBox, rand, 3, 5, 5, EnumFacing.SOUTH, Blocks.ACACIA_DOOR);
        }

        void setStructureType(int type) {
            this.structureType = type;
        }
    }

    public static class Pen extends Village {
        public Pen() {
        }

        Pen(Start start, int type, Random rand, int x, int z) {
            super(start, type);
            this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

            if (this.getCoordBaseMode() != null && this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z) {
                this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
            } else {
                this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
            }
        }

        public void buildComponent(StructureComponent componentIn, List<StructureComponent> structureComponents, Random rand) {
            StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, this.getComponentType());
            StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, this.getComponentType());
            StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
            StructureRabbitVillagePieces.generateAndAddRoadPiece((Start) componentIn, structureComponents, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
        }

        public boolean addComponentParts(@Nonnull World worldIn, @Nonnull Random randomIn, @Nonnull StructureBoundingBox structureBoundingBoxIn) {
            super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
            IBlockState fence = Blocks.ACACIA_FENCE.getDefaultState();
            IBlockState gate = Blocks.ACACIA_FENCE_GATE.getDefaultState();
            IBlockState torch = Blocks.TORCH.getDefaultState();
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 11, 1, 1, 12, 1, fence, fence, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 11, 1, 5, 12, 1, fence, fence, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 11, 5, 1, 12, 5, fence, fence, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 11, 5, 5, 12, 5, fence, fence, false);
            this.setBlockState(worldIn, gate.withProperty(BlockFenceGate.FACING, EnumFacing.NORTH), 3, 11, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, gate.withProperty(BlockFenceGate.FACING, EnumFacing.EAST), 1, 11, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, gate.withProperty(BlockFenceGate.FACING, EnumFacing.SOUTH), 3, 11, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, gate.withProperty(BlockFenceGate.FACING, EnumFacing.WEST), 5, 11, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 1, 11, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 1, 11, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 2, 11, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 4, 11, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 4, 11, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 5, 11, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 5, 11, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 2, 11, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, fence, 4, 11, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, torch, 1, 13, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, torch, 1, 13, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, torch, 5, 13, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, torch, 5, 13, 5, structureBoundingBoxIn);
            this.fillWithAir(worldIn, structureBoundingBoxIn, 2, 11, 2, 4, 11, 4);
            for (int i = 0; i <= 5; ++i)
                for (int j = 0; j <= 5; ++j)
                    if (j == 0 || j == 5 || i == 0 || i == 5)
                        this.clearCurrentPositionBlocksUpwards(worldIn, j, 22, i, structureBoundingBoxIn);
            for (int i = 0; i < randomIn.nextInt(3) + 1; i++)
                for (int j = 0; i < randomIn.nextInt(3) + 1; i++)
                    for (int k = 0; i < randomIn.nextInt(2) + 1; i++) {
                        EntityRabbit entity = new EntityRabbit(worldIn);
                        BlockPos pos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(getXWithOffset(2 + i + j + k, 2), getYWithOffset(12), getZWithOffset(2 + i + j + k, 2)));
                        entity.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
                        entity.onInitialSpawn(worldIn.getDifficultyForLocation(entity.getPosition()), null);
                        if (worldIn.getBlockState(pos).getMaterial() != Material.WATER) entity.setNoAI(true);
                        worldIn.spawnEntity(entity);
                    }
            return true;
        }
    }
}
