package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.init.BeastsBlocks;

public class BlockTentacle extends BushBlock {

    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 8);
    public static final BooleanProperty FULL = BooleanProperty.create("full");
    private static final VoxelShape[] BOUNDING_BOXES = new VoxelShape[8];

    public BlockTentacle() {
        super(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT));
        this.setDefaultState(this.getDefaultState().with(SIZE, 8).with(FULL, false));
        BeastsUtils.addToRegistry(this, "tentacle", null);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        Block block = world.getBlockState(pos.up()).getBlock();
        return block == BeastsBlocks.JELLY_LEAVES || block == this;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader source, BlockPos pos, ISelectionContext context) {
        int index = state.get(SIZE) - 1;
        if (BOUNDING_BOXES[index] == null)
            BOUNDING_BOXES[index] = VoxelShapes.create(0.375, 1, 0.375, 0.625, 1 - ((index + 1) * 0.125), 0.625);
        return BOUNDING_BOXES[index];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);
        if (entityIn instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) entityIn;
            if (!entity.isPotionActive(Effects.POISON)) {
                EffectInstance effect = new EffectInstance(Effects.POISON, 100);
                if (entity.isPotionApplicable(effect)) entity.addPotionEffect(effect);
            }
        }
    }
}
