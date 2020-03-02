package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.block.BeastsBlock;

public class BlockHermitShell extends BeastsBlock {
    public static final VoxelShape BOUNDING_BOX = VoxelShapes.create(0.15F, 0, 0.15F, 0.85F, 0.7F, 0.85F);

    public BlockHermitShell() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 10), "hermit_turtle_shell", BlockItem::new);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean doesSideBlockRendering(BlockState blockState, IEnviromentBlockReader blockAccess, BlockPos pos, Direction side) {
        return true;
    }

    public VoxelShape getShape(BlockState state, IBlockReader source, BlockPos pos, ISelectionContext context) {
        return BOUNDING_BOX;
    }
}
