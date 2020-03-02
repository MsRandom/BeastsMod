package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsBlock;
import random.beasts.common.init.BeastsBlocks;

@SuppressWarnings("deprecation")
public class BlockGlowRoot extends BeastsBlock {

    private boolean isTop;

    public BlockGlowRoot(boolean top) {
        super(Block.Properties.create(Material.PLANTS, MaterialColor.GRASS).sound(SoundType.PLANT).lightValue(15), "glow_root_" + (top ? "top" : "bottom"), null);
        this.isTop = top;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos up = pos.up();
        if (isTop) return worldIn.getBlockState(up).getBlock() != Blocks.AIR;
        return worldIn.getBlockState(up).getBlock() == BeastsBlocks.GLOW_ROOT_TOP;
    }

    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (isTop) {
            if (player.abilities.isCreativeMode) worldIn.removeBlock(pos.down(), false);
            else worldIn.destroyBlock(pos.down(), true);
        } else worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    /*@Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return isTop ? Item.getItemFromBlock(Blocks.AIR) : BeastsItems.GLOW_ROOT;
    }*/
}
