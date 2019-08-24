package rando.beasts.common.item;

import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rando.beasts.common.entity.hang.EntityBeastsPainting;

public class ItemBeastsPainting extends BeastsItem
{
    public ItemBeastsPainting(String u)
    {
    	super(u);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	ItemStack stack = player.inventory.getCurrentItem();
        if (facing == EnumFacing.DOWN)
        {
        	return EnumActionResult.FAIL;
        }
        else if (facing == EnumFacing.UP)
        {
        	return EnumActionResult.FAIL;
        }
        else
        {
            BlockPos blockpos1 = pos.offset(facing);

            if (!player.canPlayerEdit(blockpos1, facing, stack))
            {
            	return EnumActionResult.FAIL;
            }
            else
            {
            	EntityHanging entityhanging = this.createHangingEntity(worldIn, blockpos1, facing);

                if (entityhanging != null && entityhanging.onValidSurface())
                {
                    if (!worldIn.isRemote)
                    {
                        worldIn.spawnEntity(entityhanging);
                    }

                    stack.shrink(1);
                }

                return EnumActionResult.SUCCESS;
            }
        }
    }

    private EntityHanging createHangingEntity(World worldIn, BlockPos pos, EnumFacing clickedSide)
    {
        return new EntityBeastsPainting(worldIn, pos, clickedSide);
    }
}