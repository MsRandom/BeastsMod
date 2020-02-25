package random.beasts.common.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntityMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.init.BeastsTriggers;

import java.util.List;
import java.util.function.Predicate;

public class EntityGiantGardenEel extends MonsterEntity {

    private LivingEntity targetedEntity;

    private float slamTimer = 250;
    private int lastSlam = 0;

    public EntityGiantGardenEel(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.goalSelector.addGoal(6, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(0, new EntityAILookIdle(this));
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1000);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.GARDEN_EEL_HURT;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @OnlyIn(Dist.CLIENT)
    public float getSlamTimer() {
        return this.slamTimer;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        lastSlam++;
        if (slamTimer < 250) slamTimer += 10;
        if (lastSlam > 25) {
            Predicate<Entity> predicate = e -> EntitySelectors.NOT_SPECTATING.apply(e) && e instanceof LivingEntity && !(e instanceof EntityGiantGardenEel) && (!(e instanceof PlayerEntity) || !((PlayerEntity) e).capabilities.isCreativeMode);
            List<Entity> entitiesInRange = world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(3), predicate::test);
            entitiesInRange.forEach(e -> {
                if (targetedEntity == null || (getDistanceSq(targetedEntity) > getDistanceSq(e) && e instanceof LivingEntity))
                    targetedEntity = (LivingEntity) e;
            });
            if (!entitiesInRange.contains(targetedEntity)) targetedEntity = null;
            if (targetedEntity != null && (!(targetedEntity instanceof MobEntity) || !((MobEntity) targetedEntity).isAIDisabled())) {
                this.getLookHelper().setLookPositionWithEntity(targetedEntity, 10.0F, 10.0F);
                if ((slamTimer -= 50) < 0) slamTimer = 0;
                if (slamTimer == 0) {
                    if (!targetedEntity.isDead) {
                        targetedEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 4.0F);
                        if (targetedEntity instanceof PlayerEntityMP)
                            BeastsTriggers.HAMMERTIME.trigger((PlayerEntityMP) targetedEntity);
                    }
                    slamTimer = 0;
                    lastSlam = 0;
                }
            }
        }
    }
}
