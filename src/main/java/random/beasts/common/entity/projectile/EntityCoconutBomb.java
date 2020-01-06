package random.beasts.common.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.common.init.BeastsItems;

public class EntityCoconutBomb extends EntityThrowable {
    public EntityCoconutBomb(World worldIn) {
        super(worldIn);
    }

    public EntityCoconutBomb(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) for (int i = 0; i < 8; ++i)
            this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(BeastsItems.COCONADE));
    }

    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote && result.typeOfHit != RayTraceResult.Type.MISS) {
            BlockPos pos = result.typeOfHit == RayTraceResult.Type.BLOCK ? result.getBlockPos() : result.entityHit.getPosition();
            if (result.typeOfHit == RayTraceResult.Type.ENTITY)
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
            this.world.createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 1.0F, true);
            this.world.setEntityState(this, (byte) 3);
            this.setDead();
        }
    }
}
