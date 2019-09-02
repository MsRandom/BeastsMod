package rando.beasts.common.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityGiantGardenEel extends EntityMob {

    public float slamTimer = 250;
    private int lastSlam = 0;

    public EntityGiantGardenEel(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 2.7f);
        this.experienceValue = 3;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(0, new EntityAILookIdle(this));
    }

    @Override
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked();
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {}

    @Override
    public void applyEntityCollision(Entity entityIn) {}

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1000);
    }


    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        lastSlam++;
        if (slamTimer < 250) slamTimer += 10;
        if(lastSlam > 25) {
            final Entity[] entity = new Entity[1];
            world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(3)).stream().filter(e -> e instanceof EntityLivingBase && !(e instanceof EntityGiantGardenEel) && (!(e instanceof EntityPlayer) || !((EntityPlayer) e).capabilities.isCreativeMode)).forEach(e -> {
                if (entity[0] == null || (getDistanceSq(entity[0]) > getDistanceSq(e))) entity[0] = e;
            });
            if (entity[0] != null && (!(entity[0] instanceof EntityLiving) || !((EntityLiving) entity[0]).isAIDisabled())) {
                this.getLookHelper().setLookPositionWithEntity(entity[0], 10.0F, 10.0F);
                if ((slamTimer -= 50) < 0) slamTimer = 0;
                if (slamTimer == 0) {
                    if (!entity[0].isDead) entity[0].attackEntityFrom(DamageSource.causeMobDamage(this), 4.0F);
                    slamTimer = 0;
                    lastSlam = 0;
                }
            }
        }
    }
}
