package random.beasts.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.entity.item.EntityFallingCoconut;
import random.beasts.common.init.BeastsItems;
import random.beasts.common.tileentity.TileEntityCoconut;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockCoconut extends ContainerBlock {
    private static final VoxelShape AABB = VoxelShapes.create(0.1, 0, 0.25, 0.6, 0.2, 0.75);

    public BlockCoconut() {
        super(Block.Properties.create(Material.WOOD));
        BeastsUtils.addToRegistry(this, "coconut", null);
    }

    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, this.tickRate(worldIn));
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if (!worldIn.isRemote) this.checkFallable(worldIn, pos);
    }

    private void checkFallable(World worldIn, BlockPos pos) {
        if ((worldIn.isAirBlock(pos.down()) || FallingBlock.canFallThrough(worldIn.getBlockState(pos.down()))) && pos.getY() >= 0) {
            if (!worldIn.isRemote) worldIn.addEntity(new EntityFallingCoconut(worldIn, pos));
        }
    }

    public int tickRate(IWorldReader worldIn) {
        return 2;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(16) == 0) {
            BlockPos blockpos = pos.down();
            if (FallingBlock.canFallThrough(worldIn.getBlockState(blockpos))) {
                double d0 = pos.getX() + rand.nextFloat();
                double d1 = pos.getY() - 0.05;
                double d2 = pos.getZ() + rand.nextFloat();
                worldIn.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, stateIn), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Nonnull
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader source, BlockPos pos, ISelectionContext context) {
        return AABB;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityCoconut();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Random rand = player.getRNG();
        for (int i = 0; i < 4; ++i)
            worldIn.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.SAND.getDefaultState()), hit.getPos().getX() + rand.nextDouble(), hit.getPos().getY() + rand.nextDouble(), hit.getPos().getZ() + rand.nextDouble(), (rand.nextDouble() - 0.5D) * 0.5D, (rand.nextDouble() - 0.5D) * 0.5D, (rand.nextDouble() - 0.5D) * 0.5D);
        removedByPlayer(state, worldIn, pos, player, true, worldIn.getFluidState(pos));
        spawnAsEntity(worldIn, pos, new ItemStack(BeastsItems.COCONUT));
        return true;
    }
}
