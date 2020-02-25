package random.beasts.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.block.BeastsBlock;

public class BlockHermitShell extends BeastsBlock {

    public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.15F, 0, 0.15F, 0.85F, 0.7F, 0.85F);

    public BlockHermitShell() {
        super(Material.ROCK, "hermit_turtle_shell");
        this.setHardness(1.5F);
        this.setResistance(10.0F);
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
        return true;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }
}
