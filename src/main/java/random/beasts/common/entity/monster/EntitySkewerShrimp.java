package random.beasts.common.entity.monster;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.init.BeastsItems;

public class EntitySkewerShrimp extends MonsterEntity {

    public EntitySkewerShrimp(EntityType<? extends EntitySkewerShrimp> type, World worldIn) {
        super(type, worldIn);
        this.goalSelector.addGoal(0, new EntityAIWander(this, 0.6F));
        this.goalSelector.addGoal(0, new EntityAIHurtByTarget(this, true));
        this.goalSelector.addGoal(1, new EntityAIAttackMelee(this, 0.6F, true));
        this.goalSelector.addGoal(2, new EntityAIPanic(this, 0.0D));
        this.goalSelector.addGoal(3, new SwimGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1F);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6F);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
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
        return BeastsSounds.SKEWER_SHRIMP_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }
}
