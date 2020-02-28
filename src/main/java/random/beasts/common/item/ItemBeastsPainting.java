package random.beasts.common.item;

import net.minecraft.entity.item.HangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsItem;
import random.beasts.common.entity.item.EntityBeastsPainting;

public class ItemBeastsPainting extends BeastsItem {
    public ItemBeastsPainting() {
        super("beasts_painting");
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getItem();
        Direction facing = context.getFace();
        BlockPos pos = context.getPos().offset(facing);
        if (facing != Direction.DOWN && facing != Direction.UP && context.getPlayer().canPlayerEdit(pos, facing, stack)) {
            HangingEntity entityhanging = this.createHangingEntity(context.getWorld(), pos, facing);
            if (entityhanging.onValidSurface()) {
                if (!context.getWorld().isRemote) context.getWorld().addEntity(entityhanging);
                stack.shrink(1);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    private HangingEntity createHangingEntity(World worldIn, BlockPos pos, Direction clickedSide) {
        return new EntityBeastsPainting(worldIn, pos, clickedSide);
    }
}