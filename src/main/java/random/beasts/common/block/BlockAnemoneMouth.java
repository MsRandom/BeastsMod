package random.beasts.common.block;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsAnemoneBlock;
import random.beasts.common.init.BeastsItems;

import java.util.HashMap;
import java.util.Map;

public class BlockAnemoneMouth extends BeastsAnemoneBlock {
    public static final PropertyInteger FED = PropertyInteger.create("fed", 0, 6);
    private static Map<ItemStack, Tuple<Integer, Integer>> dropTable;
    private static ItemStack[] items;

    public BlockAnemoneMouth() {
        super("mouth");
        this.setDefaultState(getDefaultState().withProperty(FED, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FED);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() == BeastsItems.MEAT_SCRAPES) {
            if (!playerIn.capabilities.isCreativeMode) stack.shrink(1);
            IBlockState newState = state.cycleProperty(FED);
            int fed = newState.getValue(FED);
            if (fed == 0 || (fed > 3 && playerIn.getRNG().nextBoolean())) {
                if (dropTable == null) {
                    dropTable = new HashMap<>();
                    dropTable.put(new ItemStack(Items.BONE), new Tuple<>(1, 2));
                    dropTable.put(new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()), new Tuple<>(2, 3));
                    dropTable.put(new ItemStack(Items.FLINT), new Tuple<>(1, 2));
                    dropTable.put(new ItemStack(Items.GOLD_NUGGET), new Tuple<>(2, 3));
                    dropTable.put(new ItemStack(Items.IRON_NUGGET), new Tuple<>(3, 4));
                    items = dropTable.keySet().toArray(new ItemStack[0]);
                }
                ItemStack item = items[playerIn.getRNG().nextInt(items.length)];
                Tuple<Integer, Integer> chance = dropTable.get(item);
                for (int i = 0; i < playerIn.getRNG().nextInt(chance.getSecond()) + chance.getFirst(); i++)
                    spawnAsEntity(worldIn, pos, item.copy());
                if (fed != 0) newState = state.withProperty(FED, 0);
            }
            worldIn.setBlockState(pos, newState);
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
