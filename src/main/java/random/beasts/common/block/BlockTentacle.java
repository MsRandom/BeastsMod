package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockState;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.PropertyBool;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.init.BeastsBlocks;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTentacle extends BlockBush {

    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 8);
    public static final PropertyBool FULL = PropertyBool.create("full");
    private static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[8];

    public BlockTentacle() {
        this.setDefaultState(this.getDefaultState().with(SIZE, 8).with(FULL, false));
        BeastsUtils.addToRegistry(this, "tentacle", null);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, BlockState state) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block == BeastsBlocks.JELLY_LEAVES || block == this;
    }

    @Override
    public void tick(World worldIn, BlockPos pos, BlockState state, Random rand) {
        super.tick(worldIn, pos, state, rand);

    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IWorldReader source, BlockPos pos) {
        int index = state.get(SIZE) - 1;
        if (BOUNDING_BOXES[index] == null)
            BOUNDING_BOXES[index] = new AxisAlignedBB(0.375, 1, 0.375, 0.625, 1 - ((index + 1) * 0.125), 0.625);
        return BOUNDING_BOXES[index];
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IWorldReader worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    public boolean isFullCube(BlockState state) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, BlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        if (entityIn instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) entityIn;
            if (!entity.isPotionActive(Effects.POISON)) {
                EffectInstance effect = new EffectInstance(Effects.POISON, 100);
                if (entity.isPotionApplicable(effect)) entity.addPotionEffect(effect);
            }
        }
    }

    public BlockFaceShape getBlockFaceShape(IWorldReader worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }
}
