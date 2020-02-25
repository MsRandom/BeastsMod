package random.beasts.common.entity.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import random.beasts.common.entity.passive.EntityLandwhale;
import random.beasts.common.init.BeastsItems;

public class EntityThrownCoconut extends EntityThrowable {
    public EntityThrownCoconut(World worldIn) {
        super(worldIn);
    }

    public EntityThrownCoconut(World worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            LivingEntity thrower = this.getThrower();
            ItemEntity drop = this.dropItem(BeastsItems.COCONUT, 1);
            this.world.getEntitiesWithinAABB(EntityLandwhale.class, this.getEntityBoundingBox().grow(64)).stream().filter(v -> {
                LivingEntity owner = v.getOwner();
                return owner != null && owner == thrower;
            }).forEach(v -> v.setTarget(drop));
            this.setDead();
        }
    }
}
