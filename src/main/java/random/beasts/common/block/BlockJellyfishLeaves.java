package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.block.BeastsLeaves;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class BlockJellyfishLeaves extends BeastsLeaves {

    public BlockJellyfishLeaves() {
        this.setSoundType(SoundType.SLIME);
        BeastsUtils.addToRegistry(this, "jellyleaves", ItemBlock::new);
    }

    @Override
    protected int getCheckArea() {
        return 14;
    }

    @Override
    protected int getDecayArea() {
        return 14;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldSideBeRendered(BlockState blockState, IWorldReader blockAccess, BlockPos pos, Direction side) {
        BlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        if (blockState != iblockstate) return true;
        return block != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BeastsBlocks.JELLYWOOD_SAPLING);
    }

    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    public int getMetaFromState(BlockState state) {
        int i = 0;
        if (!state.getValue(DECAYABLE)) i |= 4;
        if (state.getValue(CHECK_DECAY)) i |= 8;
        return i;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    public int damageDropped(BlockState state) {
        return 0;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, IWorldReader world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }
}
