package rando.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.utils.BeastsUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTentacle extends BlockBush {

    public static final PropertyInteger SIZE = PropertyInteger.create("size", 1, 8);
    public static final PropertyBool FULL = PropertyBool.create("full");
    private static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[8];

    public BlockTentacle() {
        this.setDefaultState(this.getDefaultState().withProperty(SIZE, 8).withProperty(FULL, false));
        BeastsUtil.addToRegistry(this, "tentacle", false, null);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block == BeastsBlocks.JELLY_LEAVES || block == this;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int index = state.getValue(SIZE)-1;
        if(BOUNDING_BOXES[index] == null) BOUNDING_BOXES[index] = new AxisAlignedBB(0.375, 1, 0.375, 0.625, 1 - ((index + 1) * 0.125), 0.625);
        return BOUNDING_BOXES[index];
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SIZE, FULL);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        if(entityIn instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) entityIn;
            if(!entity.isPotionActive(MobEffects.POISON)) {
                PotionEffect effect = new PotionEffect(MobEffects.POISON, 100);
                if(entity.isPotionApplicable(effect)) entity.addPotionEffect(effect);
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int i = meta + 1;
        boolean full = i > 8;
        return getDefaultState().withProperty(SIZE, full ? i - 8 : i).withProperty(FULL, full);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SIZE) + (state.getValue(FULL) ? 8 : 0) - 1;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
