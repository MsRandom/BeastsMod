package rando.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import rando.beasts.common.utils.BeastsUtil;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public class BeastsSapling extends BlockBush implements IGrowable {

    static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);
    private WorldGenerator treeGen;

    public BeastsSapling(String name, Function<Block, Item> item) {
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
        setHardness(0.6F);
        setSoundType(SoundType.PLANT);
        BeastsUtil.addToRegistry(this, name, true, item);
    }

    public BeastsSapling(String name, WorldGenerator generator) {
        this(name, ItemBlock::new);
        this.treeGen = generator;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.SAND;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand);

            if (!worldIn.isAreaLoaded(pos, 1)) return;
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) this.grow(worldIn, rand, pos, state);
        }
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return worldIn.rand.nextFloat() < 0.45;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (state.getValue(STAGE) == 0) worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        else generateTree(worldIn, pos, state, rand);
    }

    protected void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(treeGen != null) treeGen.generate(worldIn, rand, pos);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, (meta & 8) >> 3);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE) << 3;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }
}
