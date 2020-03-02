package random.beasts.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsBlock;
import random.beasts.common.item.ItemOreBlock;

import java.util.Random;

public class BlockAbyssalOre extends BeastsBlock {

    public static final PropertyEnum<OreType> ORE = PropertyEnum.create("ore", OreType.class);

    public BlockAbyssalOre(String name) {
        super(Material.ROCK, name, ItemOreBlock::new);
        this.setHardness(3F);
        this.setResistance(5F);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ORE, OreType.COAL));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < OreType.values().length; i++) items.add(i, new ItemStack(this, 1, i));
    }


    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
            int i = 0;

            if (state.getValue(ORE).equals(OreType.COAL)) {
                i = MathHelper.getInt(rand, 0, 2);
            } else if (state.getValue(ORE).equals(OreType.DIAMOND)) {
                i = MathHelper.getInt(rand, 3, 7);
            } else if (state.getValue(ORE).equals(OreType.EMERALD)) {
                i = MathHelper.getInt(rand, 3, 7);
            } else if (state.getValue(ORE).equals(OreType.LAPIS)) {
                i = MathHelper.getInt(rand, 2, 5);
            } else if (state.getValue(ORE).equals(OreType.REDSTONE)) {
                if (this.getItemDropped(state, RANDOM, fortune) != Item.getItemFromBlock(this)) {
                    i = 1 + RANDOM.nextInt(5);
                }
                i = 0;
            }

            return i;
        }
        return 0;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        int count = fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(state, rand, fortune) ? rand.nextInt(fortune + 2) - 1 : 1;
        if (count < 0) count = 1;
        for (int i = 0; i < count; i++) {
            if (state.getValue(ORE).equals(OreType.COAL)) {
                drops.add(new ItemStack(Items.COAL, 1, 0));
            } else if (state.getValue(ORE).equals(OreType.DIAMOND)) {
                drops.add(new ItemStack(Items.DIAMOND, 1, 0));
            } else if (state.getValue(ORE).equals(OreType.LAPIS)) {
                drops.add(new ItemStack(Items.DYE, 4 + rand.nextInt(5), 4));
            } else if (state.getValue(ORE).equals(OreType.EMERALD)) {
                drops.add(new ItemStack(Items.EMERALD, 1, 0));
            } else if (state.getValue(ORE).equals(OreType.REDSTONE)) {
                drops.add(new ItemStack(Items.REDSTONE, 4 + rand.nextInt(2), 0));
            } else {
                drops.add(new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(ORE).ordinal()));
            }
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getValue(ORE).equals(OreType.COAL)) {
            return Items.COAL;
        } else if (state.getValue(ORE).equals(OreType.DIAMOND)) {
            return Items.DIAMOND;
        } else if (state.getValue(ORE).equals(OreType.LAPIS)) {
            return Items.DYE;
        } else if (state.getValue(ORE).equals(OreType.EMERALD)) {
            return Items.EMERALD;
        } else if (state.getValue(ORE).equals(OreType.REDSTONE)) {
            return Items.REDSTONE;
        } else {
            return Item.getItemFromBlock(this);
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(ORE).ordinal());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ORE).ordinal();
    }

    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ORE, OreType.values()[meta]);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ORE);
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 2;
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return "pickaxe";
    }
}
