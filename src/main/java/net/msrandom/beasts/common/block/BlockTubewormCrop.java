package net.msrandom.beasts.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.msrandom.beasts.api.main.BeastsUtils;
import net.msrandom.beasts.common.init.BeastsBlocks;
import net.msrandom.beasts.common.init.BeastsItems;

import java.util.Random;

public class BlockTubewormCrop extends BlockBush implements IGrowable {

    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 4);
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

    public BlockTubewormCrop() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
        this.setTickRandomly(true);
        this.setSoundType(SoundType.PLANT);
        BeastsUtils.addToRegistry(this, "tubeworm_crop");
        this.setCreativeTab(null);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CROPS_AABB[state.getValue(AGE)];
    }

    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == BeastsBlocks.ABYSSAL_STONE;
    }

    public int getMaxAge() {
        return 4;
    }

    protected int getAge(IBlockState state) {
        return state.getValue(AGE);
    }

    public IBlockState withAge(int age) {
        return this.getDefaultState().withProperty(AGE, age);
    }

    public boolean isMaxAge(IBlockState state) {
        return getAge(state) >= this.getMaxAge();
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
            int i = this.getAge(state);

            if (i < this.getMaxAge()) {
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(25) == 0)) {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();

        if (i > j) {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i), 2);
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.getInt(worldIn.rand, 1, 2);
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }

    protected Item getSeed() {
        return BeastsItems.TUBEWORM_SEEDS;
    }

    protected Item getCrop() {
        return BeastsItems.TUBEWORMS;
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, 0);
        int age = getAge(state);
        Random rand = world instanceof World ? ((World) world).rand : new Random();

        if (age >= getMaxAge()) {
            int k = 3 + fortune;

            for (int i = 0; i < 3 + fortune; ++i) {
                if (rand.nextInt(2 * getMaxAge()) <= age) {
                    drops.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);

        if (false && !worldIn.isRemote) // Forge: NOP all this.
        {
            int i = this.getAge(state);

            if (i >= this.getMaxAge()) {
                int j = 3 + fortune;

                for (int k = 0; k < j; ++k) {
                    if (worldIn.rand.nextInt(2 * this.getMaxAge()) <= i) {
                        spawnAsEntity(worldIn, pos, new ItemStack(this.getSeed()));
                    }
                }
            }
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this.getSeed());
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.withAge(meta);
    }

    public int getMetaFromState(IBlockState state) {
        return this.getAge(state);
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        this.grow(worldIn, pos, state);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }
}