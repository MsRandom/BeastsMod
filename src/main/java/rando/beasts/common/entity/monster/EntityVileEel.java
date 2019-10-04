package rando.beasts.common.entity.monster;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import rando.beasts.client.init.BeastsSounds;
import rando.beasts.client.model.ModelVileEel;
import rando.beasts.common.entity.IDriedAquatic;
import rando.beasts.common.entity.passive.EntityLandwhale;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.init.BeastsItems;

public class EntityVileEel extends EntityMob implements IDriedAquatic {
    public EntityVileEel(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.8F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, true, false, entity -> entity instanceof EntityLiving && !(entity instanceof EntityLandwhale) && !(entity instanceof EntityVileEel)));
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
        if(this.getRidingEntity() != null) {
            getRidingEntity().attackEntityFrom(DamageSource.causeMobDamage(this), 1);
        }
        super.onLivingUpdate();
    }

/*    @Override
    public void updateRidden() {
        Entity ridingEntity = this.getRidingEntity();
        if(this.getRidingEntity().isDead && this.isRiding())
            this.dismountRidingEntity();
        else{
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            this.onUpdate();
            if (this.isRiding()) {
                if (ridingEntity.isPassenger(this) && ridingEntity instanceof EntityLivingBase) {
                    this.rotationYaw = ((EntityLivingBase) ridingEntity).renderYawOffset;
                    this.rotationYawHead = ((EntityLivingBase) ridingEntity).renderYawOffset;
                    this.prevRotationYaw = ((EntityLivingBase) ridingEntity).renderYawOffset;
                    float angle = (0.0174533F * ((EntityLivingBase) ridingEntity).renderYawOffset) + 30;
                    this.setPosition(ridingEntity.posX + (0.4F * MathHelper.sin((float) (Math.PI + angle))), ridingEntity.posY + 0.4D, ridingEntity.posZ + (0.4F * MathHelper.cos(angle)));
                }
            }
        }
    }*/

    @Override
    public void updatePassenger(Entity passenger) {
/*
        float calcX = MathHelper.sin(-this.renderYawOffset * 0.017453292F);
        float calcZ = MathHelper.cos(this.renderYawOffset * 0.017453292F);
        passenger.setPosition(this.posX - calcX - (calcX * 0.9F), this.posY + 0.2F, this.posZ - calcZ - (calcZ * 0.9F));
*/

        super.updatePassenger(passenger);
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
        if(!this.world.isRemote)
            entityIn.startRiding(this, true);
        return super.attackEntityAsMob(entityIn);
    }
}