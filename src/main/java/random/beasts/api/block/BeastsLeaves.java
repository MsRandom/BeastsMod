package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BeastsLeaves extends LeavesBlock {
    public BeastsLeaves() {
        this(Block.Properties.create(Material.LEAVES).sound(SoundType.PLANT));
    }

    public BeastsLeaves(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(PERSISTENT, true));
    }

    protected int getDecayArea() {
        return 10;
    }

    protected int getCheckArea() {
        return 10;
    }

    //TODO
    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        /*if (!worldIn.isRemote) {
            if (state.get(CHECK_DECAY) && state.get(DECAYABLE)) {
                int i = getDecayArea();
                int j = getCheckArea();
                int k = pos.getX();
                int l = pos.getY();
                int i1 = pos.getZ();
                int j1 = 32;
                int k1 = 1024;
                int l1 = 16;

                if (this.surroundings == null) this.surroundings = new int[Short.MAX_VALUE + 1];
                if (worldIn.isAreaLoaded(pos, 1)) {
                    if (worldIn.isAreaLoaded(pos, j)) {
                        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                        for (int i2 = -i; i2 <= i; ++i2) {
                            for (int j2 = -i; j2 <= i; ++j2) {
                                for (int k2 = -i; k2 <= i; ++k2) {
                                    BlockState iblockstate = worldIn.getBlockState(mutablePos.setPos(k + i2, l + j2, i1 + k2));
                                    Block block = iblockstate.getBlock();

                                    final int i3 = (i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1;
                                    if (!block.canSustainLeaves(iblockstate, worldIn, mutablePos.setPos(k + i2, l + j2, i1 + k2))) {
                                        if (block.isLeaves(iblockstate, worldIn, mutablePos.setPos(k + i2, l + j2, i1 + k2))) {
                                            this.surroundings[i3] = -2;
                                        } else {
                                            this.surroundings[i3] = -1;
                                        }
                                    } else {
                                        this.surroundings[i3] = 0;
                                    }
                                }
                            }
                        }

                        for (int i3 = 1; i3 <= i; ++i3) {
                            for (int j3 = -i; j3 <= i; ++j3) {
                                for (int k3 = -i; k3 <= i; ++k3) {
                                    for (int l3 = -i; l3 <= i; ++l3) {
                                        final int x = j3 + l1;
                                        final int y = k3 + l1;
                                        final int z = l3 + l1;
                                        final int i2 = x * k1 + y * j1 + z;
                                        final int[] arr = {
                                                (x - 1) * k1 + y * j1 + z,
                                                (x + 1) * k1 + y * j1 + z,
                                                x * k1 + (y - 1) * j1 + z,
                                                x * k1 + (y + 1) * j1 + z,
                                                x * k1 + y * j1 + (z - 1),
                                                x * k1 + y * j1 + z + 1};
                                        if (this.surroundings[i2] == i3 - 1) {
                                            for (int value : arr) {
                                                if (this.surroundings[value] == -2) this.surroundings[value] = i3;
                                                else if (this.surroundings[value] == -1)
                                                    this.surroundings[value] = 10 + i3;
                                            }
                                        } else if (this.surroundings[i2] == 10 + i3 - 1) for (int value : arr)
                                            if (this.surroundings[value] == -2) this.surroundings[value] = i3;
                                    }
                                }
                            }
                        }
                    }

                    int l2 = this.surroundings[16912];

                    if (l2 >= 0) worldIn.setBlockState(pos, state.with(CHECK_DECAY, false), 4);
                    else this.destroy(worldIn, pos);
                }
            }
        }*/
    }

    private void destroy(World worldIn, BlockPos pos) {
        //this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.removeBlock(pos, false);
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IWorld world, BlockPos pos, int fortune) {
        return Collections.singletonList(new ItemStack(this).copy());
    }
}
