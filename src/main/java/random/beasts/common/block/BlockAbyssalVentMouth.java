package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import random.beasts.api.block.BeastsBlock;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockAbyssalVentMouth extends BeastsBlock {
    public BlockAbyssalVentMouth() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(2, 5).lightValue(11).sound(SoundType.STONE).tickRandomly().harvestTool(ToolType.PICKAXE).harvestLevel(1), "abyssal_vent", BlockItem::new);
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockState(pos, Blocks.LAVA.getDefaultState());
    }

    /*@Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BeastsBlocks.ABYSSAL_STONE);
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }*/

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.animateTick(stateIn, worldIn, pos, rand);
        for (int i = 0; i < 5; ++i) {
            double d0 = (double) pos.getX() + 0.25D + rand.nextDouble() * 0.5D;
            double d1 = (double) pos.getY() + 13D / 16D;
            double d2 = (double) pos.getZ() + 0.25D + rand.nextDouble() * 0.5D;
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
