package rando.beasts.common.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGiantGardenEel extends EntityMob {

    private int slamTimer = 0;
    public boolean slam = false;

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

    @Override
    public void onUpdate() {
        super.onUpdate();
        EntityGiantGardenEel entity = world.getEntitiesWithinAABB(EntityGiantGardenEel.class, getEntityBoundingBox().grow(4)).stream().sorted((entityGiantGardenEel, t1) -> (int) (getDistanceSq(t1) - entityGiantGardenEel.getDistanceSq(t1))).toArray(EntityGiantGardenEel[]::new)[0];
        if(entity != null && !entity.isAIDisabled()){
            this.getLookHelper().setLookPositionWithEntity(entity, 10.0F, 10.0F);
            if(slamTimer < 90){
                slamTimer++;
                if(slamTimer > 3) slam = false;
            } else {
                slamTimer = 0;
                slam = true;
                if(!entity.isDead) entity.attackEntityFrom(DamageSource.causeMobDamage(this), 4.0F);
            }
        }
    }
}
