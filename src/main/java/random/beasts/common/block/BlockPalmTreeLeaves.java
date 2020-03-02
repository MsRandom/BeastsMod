package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsLeaves;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.entity.item.EntityFallingCoconut;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockPalmTreeLeaves extends BeastsLeaves {
    public BlockPalmTreeLeaves() {
        BeastsUtils.addToRegistry(this, "palmleaves");
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        if (rand.nextInt(200) == 0) worldIn.addEntity(new EntityFallingCoconut(worldIn, pos));
    }

    /*protected int getSaplingDropChance(BlockState state) {
        return 40;
    }

    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BeastsBlocks.PALM_SAPLING);
    }

    public int damageDropped(BlockState state) {
        return 0;
    }*/

    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) player.addStat(Stats.BLOCK_MINED.get(this));
        else super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IWorld world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }
}
