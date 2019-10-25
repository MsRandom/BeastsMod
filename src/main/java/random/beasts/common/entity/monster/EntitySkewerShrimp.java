package random.beasts.common.entity.monster;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import random.beasts.common.init.BeastsItems;

public class EntitySkewerShrimp extends EntityMob {

    public EntitySkewerShrimp(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.4f);
        this.tasks.addTask(0, new EntityAIWander(this, 0.6F));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.6F, true));
        this.tasks.addTask(2, new EntityAIPanic(this, 0.0D));
        this.tasks.addTask(3, new EntityAISwimming(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1F);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6F);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        this.dropItem(isBurning() ? BeastsItems.COOKED_SHRIMP : BeastsItems.SHRIMP, this.rand.nextInt(2) + 1);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }
}
