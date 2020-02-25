package random.beasts.common.item;

import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsItem;
import random.beasts.common.entity.item.EntityBeastsPainting;

public class ItemBeastsPainting extends BeastsItem {
    public ItemBeastsPainting() {
        super("beasts_painting");
    }

    @Override
    public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos blockpos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.inventory.getCurrentItem();
        BlockPos pos = blockpos.offset(facing);
        if (facing != Direction.DOWN && facing != Direction.UP && player.canPlayerEdit(pos, facing, stack)) {
            EntityHanging entityhanging = this.createHangingEntity(worldIn, pos, facing);
            if (entityhanging.onValidSurface()) {
                if (!worldIn.isRemote) worldIn.addEntity(entityhanging);
                stack.shrink(1);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    private EntityHanging createHangingEntity(World worldIn, BlockPos pos, Direction clickedSide) {
        return new EntityBeastsPainting(worldIn, pos, clickedSide);
    }
}