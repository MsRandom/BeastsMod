package random.beasts.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsItem;
import random.beasts.common.entity.passive.EntityFireflySquid;

public class ItemFireflySquid extends BeastsItem {

    public ItemFireflySquid() {
        super("firefly_squid_bucket");
    }

    public static boolean isNBTValid(NBTTagCompound nbt) {
        return nbt != null && nbt.hasKey("entity_data");
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        if (!worldIn.isRemote) {
            if (itemstack.hasTagCompound()) {
                EntityFireflySquid squid = new EntityFireflySquid(worldIn);
                squid.readFromNBT(itemstack.getTagCompound());
                squid.setUniqueId(MathHelper.getRandomUUID());
                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos pos = raytraceresult.getBlockPos().offset(raytraceresult.sideHit);
                    squid.setPosition(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d);
                } else
                    squid.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);

                worldIn.spawnEntity(squid);
            } else {
                EntityFireflySquid squid = new EntityFireflySquid(worldIn);
                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos pos = raytraceresult.getBlockPos().offset(raytraceresult.sideHit);
                    squid.setPosition(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d);
                } else
                    squid.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);

                worldIn.spawnEntity(squid);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
