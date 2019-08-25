package rando.beasts.common.item;

import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rando.beasts.common.entity.item.EntityBeastsPainting;

public class ItemBeastsPainting extends BeastsItem {
    public ItemBeastsPainting() {
    	super("beasts_painting");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos blockpos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.inventory.getCurrentItem();
        BlockPos pos = blockpos.offset(facing);
        if (facing != EnumFacing.DOWN && facing != EnumFacing.UP && player.canPlayerEdit(pos, facing, stack)) {
            EntityHanging entityhanging = this.createHangingEntity(worldIn, pos, facing);
            if (entityhanging.onValidSurface()) {
                if (!worldIn.isRemote) worldIn.spawnEntity(entityhanging);
                stack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    private EntityHanging createHangingEntity(World worldIn, BlockPos pos, EnumFacing clickedSide) {
        return new EntityBeastsPainting(worldIn, pos, clickedSide);
    }
}