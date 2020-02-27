package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.block.BeastsBlock;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockGlowRoot extends BeastsBlock {

    private boolean isTop;

    public BlockGlowRoot(boolean top) {
        super(Material.GRASS, MaterialColor.GRASS, "glow_root_" + (top ? "top" : "bottom"), null);
        this.isTop = top;
        this.setSoundType(SoundType.PLANT);
        this.setLightLevel(1.0F);
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
        BlockPos up = pos.up();
        if (isTop) return worldIn.getBlockState(up).getBlock() != Blocks.AIR;
        return worldIn.getBlockState(up).getBlock() == BeastsBlocks.GLOW_ROOT_TOP;
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        this.checkAndDropBlock(worldIn, pos, state);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IWorldReader worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand) {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    private void checkAndDropBlock(World worldIn, BlockPos pos, BlockState state) {
        if (!this.canBlockStay(worldIn, pos)) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (isTop) {
            if (player.abilities.isCreativeMode) worldIn.removeBlock(pos.down());
            else worldIn.destroyBlock(pos.down(), true);
        } else worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public boolean isReplaceable(IWorldReader worldIn, BlockPos pos) {
        return false;
    }

    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    public boolean isFullCube(BlockState state) {
        return false;
    }

    public BlockFaceShape getBlockFaceShape(IWorldReader worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return isTop ? Item.getItemFromBlock(Blocks.AIR) : BeastsItems.GLOW_ROOT;
    }
}
