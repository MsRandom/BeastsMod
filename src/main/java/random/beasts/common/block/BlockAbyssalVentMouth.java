package random.beasts.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsBlock;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class BlockAbyssalVentMouth extends BeastsBlock {

    public BlockAbyssalVentMouth(String name) {
        super(Material.ROCK, name);
        this.setHardness(2F);
        this.setResistance(5F);
        this.setLightLevel(0.75F);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.setBlockState(pos, Blocks.LAVA.getDefaultState());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BeastsBlocks.ABYSSAL_STONE);
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this);
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        for (int i = 0; i < 5; ++i) {
            double d0 = (double) pos.getX() + 0.25D + rand.nextDouble() * 0.5D;
            double d1 = (double) pos.getY() + 13D / 16D;
            double d2 = (double) pos.getZ() + 0.25D + rand.nextDouble() * 0.5D;
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
