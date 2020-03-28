package random.beasts.common.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGlowShrimpShot extends EntityThrowable {

    public EntityGlowShrimpShot(World world) {
        super(world);
    }

    public EntityGlowShrimpShot(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    public void onEntityUpdate() {
        world.spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, 0F / 255F, 244F / 255F, 255F / 255F, 0, 244, 255);
        super.onEntityUpdate();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null && result.entityHit instanceof EntityLivingBase && result.entityHit != this.thrower) {
            ((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 200));
            result.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.thrower), 1f);
        }
    }

    @Override
    public int getBrightnessForRender() {
        return 255;
    }
}
