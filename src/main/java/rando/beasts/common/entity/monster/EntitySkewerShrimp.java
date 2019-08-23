package rando.beasts.common.entity.monster;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import rando.beasts.common.init.BeastsItems;

public class EntitySkewerShrimp extends EntityMob {

    public EntitySkewerShrimp(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.4f);
        this.tasks.addTask(0, new EntityAIWander(this, 0.6F));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.6F, true));
        this.tasks.addTask(4, new EntityAISwimming(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1F);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6F);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        this.dropItem(BeastsItems.SHRIMP, this.rand.nextInt(2) + 1);
    }
}
