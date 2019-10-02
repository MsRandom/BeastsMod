package rando.beasts.common.entity.monster;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import rando.beasts.client.init.BeastsSounds;

public abstract class EntityBranchieBase extends EntityMob {

    public static final Map<Collection<? extends Block>, Function<BlockEvent.BreakEvent, ? extends EntityBranchieBase>> TYPES = new HashMap<>();

    EntityBranchieBase(World worldIn) {
        super(worldIn);
        this.setSize(0.2F, 0.9F);
    }

    protected void initEntityAI() {
    	this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.5D));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.targetTasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityPlayer.class, entity -> getAttackTarget() == null, 6.0F, 4, 2));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        scream();
        return super.attackEntityAsMob(entityIn);
    }
    
    

    @Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
    	setAttackTarget(null);
		return super.attackEntityFrom(source, amount);
	}

	public void scream() {
        playSound(BeastsSounds.BRANCHIE_SCREAM, getSoundVolume(), getSoundPitch());
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public float getEyeHeight() {
        return this.isChild() ? this.height : 0.8F;
    }
    
}
