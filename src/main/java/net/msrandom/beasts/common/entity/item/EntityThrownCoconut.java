package net.msrandom.beasts.common.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.msrandom.beasts.common.entity.passive.EntityLandwhale;
import net.msrandom.beasts.common.init.BeastsItems;

public class EntityThrownCoconut extends EntityThrowable {
    public EntityThrownCoconut(World worldIn) {
        super(worldIn);
    }

    public EntityThrownCoconut(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            EntityLivingBase thrower = this.getThrower();
            EntityItem drop = this.dropItem(BeastsItems.COCONUT, 1);
            this.world.getEntitiesWithinAABB(EntityLandwhale.class, this.getEntityBoundingBox().grow(64)).stream().filter(v -> {
                EntityLivingBase owner = v.getOwner();
                return owner != null && owner == thrower;
            }).forEach(v -> v.setTarget(drop));
            this.setDead();
        }
    }
}
