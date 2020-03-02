package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockShellPiece extends BlockShell {
    private static final VoxelShape AABB = VoxelShapes.create(0.4, 0, 0.4, 0.6, 0.3, 0.6);

    public BlockShellPiece(String name) {
        super(name);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader source, BlockPos pos, ISelectionContext context) {
        return AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    /*@Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }*/
}
