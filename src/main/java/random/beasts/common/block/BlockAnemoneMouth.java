package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsAnemoneBlock;
import random.beasts.common.init.BeastsItems;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockAnemoneMouth extends BeastsAnemoneBlock {
    public static final IntegerProperty FED = IntegerProperty.create("fed", 0, 6);
    private static Map<ItemStack, Tuple<Integer, Integer>> dropTable;
    private static ItemStack[] items;

    public BlockAnemoneMouth() {
        super("mouth");
        this.setDefaultState(getDefaultState().with(FED, 0));
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() == BeastsItems.MEAT_SCRAPES) {
            if (!playerIn.abilities.isCreativeMode) stack.shrink(1);
            BlockState newState = state.cycle(FED);
            int fed = newState.get(FED);
            if (fed == 0 || (fed > 3 && playerIn.getRNG().nextBoolean())) {
                if (dropTable == null) {
                    dropTable = new HashMap<>();
                    dropTable.put(new ItemStack(Items.BONE), new Tuple<>(1, 2));
                    dropTable.put(new ItemStack(Items.BONE_MEAL), new Tuple<>(2, 3));
                    dropTable.put(new ItemStack(Items.FLINT), new Tuple<>(1, 2));
                    dropTable.put(new ItemStack(Items.GOLD_NUGGET), new Tuple<>(2, 3));
                    dropTable.put(new ItemStack(Items.IRON_NUGGET), new Tuple<>(3, 4));
                    items = dropTable.keySet().toArray(new ItemStack[0]);
                }
                ItemStack item = items[playerIn.getRNG().nextInt(items.length)];
                Tuple<Integer, Integer> chance = dropTable.get(item);
                for (int i = 0; i < playerIn.getRNG().nextInt(chance.getSecond()) + chance.getFirst(); i++)
                    spawnAsEntity(worldIn, pos, item.copy());
                if (fed != 0) newState = state.with(FED, 0);
            }
            worldIn.setBlockState(pos, newState);
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
