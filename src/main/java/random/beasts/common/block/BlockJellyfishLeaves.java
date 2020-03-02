package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.block.BeastsLeaves;
import random.beasts.api.main.BeastsUtils;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockJellyfishLeaves extends BeastsLeaves {

    public BlockJellyfishLeaves() {
        super(Block.Properties.create(Material.LEAVES).sound(SoundType.SLIME));
        BeastsUtils.addToRegistry(this, "jellyleaves");
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
    public boolean doesSideBlockRendering(BlockState blockState, IEnviromentBlockReader blockAccess, BlockPos pos, Direction side) {
        BlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        if (blockState != iblockstate) return true;
        return block != this && block.doesSideBlockRendering(blockState, blockAccess, pos, side);
    }

    /*@Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BeastsBlocks.JELLYWOOD_SAPLING);
    }*/

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IWorld world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }
}
