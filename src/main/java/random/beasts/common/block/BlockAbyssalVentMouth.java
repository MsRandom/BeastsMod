package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsBlock;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class BlockAbyssalVentMouth extends BeastsBlock {
    public BlockAbyssalVentMouth() {
        super(Material.ROCK, "abyssal_vent");
        this.setHardness(2F);
        this.setResistance(5F);
        this.setLightLevel(0.75F);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.setBlockState(pos, Blocks.LAVA.getDefaultState());
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BeastsBlocks.ABYSSAL_STONE);
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }

    @Override
    public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        for (int i = 0; i < 5; ++i) {
            double d0 = (double) pos.getX() + 0.25D + rand.nextDouble() * 0.5D;
            double d1 = (double) pos.getY() + 13D / 16D;
            double d2 = (double) pos.getZ() + 0.25D + rand.nextDouble() * 0.5D;
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
