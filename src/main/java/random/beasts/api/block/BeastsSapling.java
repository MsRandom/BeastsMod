package random.beasts.api.block;

import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public class BeastsSapling extends BlockBush implements IGrowable {
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);
    private WorldGenerator treeGen;

    public BeastsSapling(String name, @Nullable Function<Boolean, WorldGenerator> generator, Function<Block, Item> item) {
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
        setSoundType(SoundType.PLANT);
        if (generator != null) this.treeGen = generator.apply(true);
        BeastsUtils.addToRegistry(this, name, item);
    }

    public BeastsSapling(String name, Function<Boolean, WorldGenerator> generator) {
        this(name, generator, ItemBlock::new);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IWorldReader source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        BlockState soil = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && soil.getBlock() == Blocks.SAND;
    }

    @Override
    protected boolean canSustainBush(BlockState state) {
        return state.getBlock() == Blocks.SAND;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand);
            if (!worldIn.isAreaLoaded(pos, 1)) return;
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
                this.grow(worldIn, rand, pos, state);
        }
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IWorldReader worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return worldIn.rand.nextFloat() < 0.45;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.getValue(STAGE) == 0) worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        else generateTree(worldIn, pos, state, rand);
    }

    protected void generateTree(World worldIn, BlockPos pos, BlockState state, Random rand) {
        if (treeGen != null) treeGen.generate(worldIn, rand, pos);
    }

    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, (meta & 8) >> 3);
    }

    public int getMetaFromState(BlockState state) {
        return state.getValue(STAGE) << 3;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }
}
