package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsAnemoneBlock;
import random.beasts.common.init.BeastsItems;

import java.util.HashMap;
import java.util.Map;

public class BlockAnemoneMouth extends BeastsAnemoneBlock {
    public static final IntegerProperty FED = IntegerProperty.create("fed", 0, 6);
    private static Map<ItemStack, Tuple<Integer, Integer>> dropTable;
    private static ItemStack[] items;

    public BlockAnemoneMouth() {
        super("mouth");
        this.setDefaultState(getDefaultState().with(FED, 0));
    }

    /*@Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }*/

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == BeastsItems.MEAT_SCRAPES) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            BlockState newState = state.cycle(FED);
            int fed = newState.get(FED);
            if (fed == 0 || (fed > 3 && player.getRNG().nextBoolean())) {
                if (dropTable == null) {
                    dropTable = new HashMap<>();
                    dropTable.put(new ItemStack(Items.BONE), new Tuple<>(1, 2));
                    dropTable.put(new ItemStack(Items.BONE_MEAL), new Tuple<>(2, 3));
                    dropTable.put(new ItemStack(Items.FLINT), new Tuple<>(1, 2));
                    dropTable.put(new ItemStack(Items.GOLD_NUGGET), new Tuple<>(2, 3));
                    dropTable.put(new ItemStack(Items.IRON_NUGGET), new Tuple<>(3, 4));
                    items = dropTable.keySet().toArray(new ItemStack[0]);
                }
                ItemStack item = items[player.getRNG().nextInt(items.length)];
                Tuple<Integer, Integer> chance = dropTable.get(item);
                for (int i = 0; i < player.getRNG().nextInt(chance.getB()) + chance.getA(); i++)
                    spawnAsEntity(worldIn, pos, item.copy());
                if (fed != 0) newState = state.with(FED, 0);
            }
            worldIn.setBlockState(pos, newState);
            return true;
        }
        return super.onBlockActivated(state, worldIn, pos, player, hand, hit);
    }
}
