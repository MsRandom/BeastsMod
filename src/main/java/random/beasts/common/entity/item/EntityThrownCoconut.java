package random.beasts.common.entity.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import random.beasts.common.entity.passive.EntityLandwhale;
import random.beasts.common.init.BeastsEntities;
import random.beasts.common.init.BeastsItems;

public class EntityThrownCoconut extends ThrowableEntity {
    public EntityThrownCoconut(EntityType<? extends EntityThrownCoconut> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityThrownCoconut(World worldIn, LivingEntity throwerIn) {
        super(BeastsEntities.THROWN_COCONUT, throwerIn, worldIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            LivingEntity thrower = this.getThrower();
            ItemEntity drop = this.entityDropItem(new ItemStack(BeastsItems.COCONUT, 1));
            this.world.getEntitiesWithinAABB(EntityLandwhale.class, this.getBoundingBox().grow(64)).stream().filter(v -> {
                LivingEntity owner = v.getOwner();
                return owner != null && owner == thrower;
            }).forEach(v -> v.setTarget(drop));
            this.remove();
        }
    }

    @Override
    protected void registerData() {
    }
}
