package net.msrandom.beasts.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.msrandom.beasts.api.item.BeastsItem;

public class ItemFireflySquid extends BeastsItem {
    public ItemFireflySquid() {
        super("firefly_squid_bucket");
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        if (!worldIn.isRemote) {
            if (itemstack.hasTagCompound()) {
                if (!playerIn.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                    playerIn.addItemStackToInventory(new ItemStack(Items.BUCKET));
                }
                Entity squid = EntityList.createEntityFromNBT(itemstack.getTagCompound().getCompoundTag("EntityData"), worldIn);
                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos pos = raytraceresult.getBlockPos().offset(raytraceresult.sideHit);
                    squid.setPosition(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d);
                } else squid.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);
                worldIn.spawnEntity(squid);
                return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }
}
