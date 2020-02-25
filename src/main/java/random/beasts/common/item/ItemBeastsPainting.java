package random.beasts.common.item;

import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsItem;
import random.beasts.common.entity.item.EntityBeastsPainting;

public class ItemBeastsPainting extends BeastsItem {
    public ItemBeastsPainting() {
        super("beasts_painting");
    }

    @Override
    public EnumActionResult onItemUse(PlayerEntity player, World worldIn, BlockPos blockpos, EnumHand hand, Direction facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.inventory.getCurrentItem();
        BlockPos pos = blockpos.offset(facing);
        if (facing != Direction.DOWN && facing != Direction.UP && player.canPlayerEdit(pos, facing, stack)) {
            EntityHanging entityhanging = this.createHangingEntity(worldIn, pos, facing);
            if (entityhanging.onValidSurface()) {
                if (!worldIn.isRemote) worldIn.spawnEntity(entityhanging);
                stack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    private EntityHanging createHangingEntity(World worldIn, BlockPos pos, Direction clickedSide) {
        return new EntityBeastsPainting(worldIn, pos, clickedSide);
    }
}