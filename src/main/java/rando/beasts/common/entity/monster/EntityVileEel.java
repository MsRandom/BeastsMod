package rando.beasts.common.entity.monster;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
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
import net.minecraft.world.World;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.init.BeastsItems;

public class EntityVileEel extends EntityMob {
    public EntityVileEel(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.8F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityCow.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPig.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntitySheep.class, true));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityChicken.class, true));
        this.targetTasks.addTask(6, new EntityAINearestAttackableTarget<>(this, EntityHorse.class, true));
        this.targetTasks.addTask(7, new EntityAINearestAttackableTarget<>(this, EntityMooshroom.class, true));
        this.targetTasks.addTask(8, new EntityAINearestAttackableTarget<>(this, EntityPufferfishDog.class, true));
        this.targetTasks.addTask(9, new EntityAINearestAttackableTarget<>(this, EntityCoconutCrab.class, true));
        this.tasks.addTask(11, new EntityAIAttackMelee(this, 1.1D, true));
        this.tasks.addTask(12, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(13, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(14, new EntityAILookIdle(this));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return null;
    }

    protected SoundEvent getDeathSound() {
        return null;
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
}