package random.beasts.common.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.entity.IDriedAquatic;
import random.beasts.common.init.BeastsItems;

import java.util.Objects;

public class EntityVileEel extends EntityMob implements IDriedAquatic {
    public EntityVileEel(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.8F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, true, false, entity -> !(entity instanceof IDriedAquatic) && getRidingEntity() != entity));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.1D, true));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.world.isDaytime();
    }

    @Override
    public void onLivingUpdate() {
        if(this.getRidingEntity() != null) getRidingEntity().attackEntityFrom(DamageSource.causeMobDamage(this), 1);
        super.onLivingUpdate();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        //fucking edit this and i'll bite your hand off, it took me way too long to figure out the math for this
        if (isPassenger(passenger))
            passenger.setPosition(posX + width * Math.sin(Math.toRadians(-rotationYaw)), posY + 0.45, posZ + width * Math.cos(Math.toRadians(rotationYaw)));
    }

    @Override
    protected Item getDropItem() {
        return Items.LEATHER;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        Item chop = isBurning() ? BeastsItems.COOKED_EEL_CHOP : BeastsItems.EEL_CHOP;
        int i = this.rand.nextInt(4);
        if (lootingModifier > 0) i += this.rand.nextInt(lootingModifier + 1);
        for (int j = 0; j < i; ++j) this.dropItem(chop, 1);
        this.dropItem(Objects.requireNonNull(chop), 1);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(70);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    protected SoundEvent getAmbientSound() {
        return BeastsSounds.VILE_EEL_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.VILE_EEL_HURT;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public float getEyeHeight() {
        return this.isChild() ? this.height : 1.3F;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (!this.world.isRemote) entityIn.startRiding(this, true);
        return super.attackEntityAsMob(entityIn);
    }
}