package random.beasts.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.common.init.BeastsEntities;

public class EntityCoconutBomb extends ThrowableEntity implements IRendersAsItem {
    private ItemStack stack;

    public EntityCoconutBomb(EntityType<? extends EntityCoconutBomb> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityCoconutBomb(ItemStack stack, World worldIn, LivingEntity throwerIn) {
        super(BeastsEntities.COCONADE, throwerIn, worldIn);
        this.stack = stack;
    }

    @Override
    protected void registerData() {

    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) for (int i = 0; i < 8; ++i)
            this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, getItem()), this.posX, this.posY, this.posZ, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
    }

    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote && result.getType() != RayTraceResult.Type.MISS) {
            BlockPos pos = result.getType() == RayTraceResult.Type.BLOCK ? ((BlockRayTraceResult) result).getPos() : ((EntityRayTraceResult) result).getEntity().getPosition();
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                assert result instanceof EntityRayTraceResult;
                ((EntityRayTraceResult) result).getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
            }
            this.world.createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 1.0F, Explosion.Mode.DESTROY);
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    public ItemStack getItem() {
        return stack;
    }
}
