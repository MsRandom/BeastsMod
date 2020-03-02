package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import random.beasts.api.block.BeastsBlock;

public class BlockAbyssalOre extends BeastsBlock {

    public static final EnumProperty<OreType> ORE = EnumProperty.create("ore", OreType.class);

    public BlockAbyssalOre() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 10).sound(SoundType.STONE), "abyssal_ore", null);
        this.setDefaultState(this.stateContainer.getBaseState().with(ORE, OreType.COAL));
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return ItemStack.EMPTY;
    }
}