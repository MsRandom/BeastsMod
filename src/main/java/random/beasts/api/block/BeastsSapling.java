package random.beasts.api.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import random.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class BeastsSapling extends BushBlock implements IGrowable {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 1);
    static final VoxelShape SAPLING_AABB = VoxelShapes.create(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);
    private Tree treeGen;

    public BeastsSapling(String name, @Nullable Supplier<Tree> generator, BiFunction<Block, Item.Properties, Item> item) {
        super(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT));
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, 0));
        if (generator != null) this.treeGen = generator.get();
        BeastsUtils.addToRegistry(this, name, item);
    }

    public BeastsSapling(String name, Supplier<Tree> generator) {
        this(name, generator, BlockItem::new);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader source, BlockPos pos, ISelectionContext context) {
        return SAPLING_AABB;
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return state.getBlock() == Blocks.SAND;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isRemote) {
            super.tick(state, worldIn, pos, rand);
            if (!worldIn.isAreaLoaded(pos, 1)) return;
            if (worldIn.getLight(pos.up()) >= 9 && rand.nextInt(7) == 0)
                this.grow(worldIn, rand, pos, state);
        }
    }


    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return worldIn.rand.nextFloat() < 0.45;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(STAGE) == 0) worldIn.setBlockState(pos, state.cycle(STAGE), 4);
        else generateTree(worldIn, pos, state, rand);
    }

    protected void generateTree(World worldIn, BlockPos pos, BlockState state, Random rand) {
        if (treeGen != null) treeGen.spawn(worldIn, pos, worldIn.getBlockState(pos.down()), rand);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(STAGE));
    }
}
