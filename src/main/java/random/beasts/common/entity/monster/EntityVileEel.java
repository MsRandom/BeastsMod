package random.beasts.common.entity.monster;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.entity.IDriedAquatic;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.init.BeastsItems;

import java.util.Objects;

public class EntityVileEel extends MonsterEntity implements IDriedAquatic {
    public EntityVileEel(EntityType<? extends EntityVileEel> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new EntityAINearestAttackableTarget<>(this, LivingEntity.class, 10, true, false, entity -> !(entity instanceof IDriedAquatic) && getRidingEntity() != entity));
        this.goalSelector.addGoal(2, new EntityAIAttackMelee(this, 1.1D, true));
        this.goalSelector.addGoal(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.goalSelector.addGoal(4, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new EntityAILookIdle(this));
    }

    @Override
    public void livingTick() {
        if (this.getRidingEntity() != null) getRidingEntity().attackEntityFrom(DamageSource.causeMobDamage(this), 1);
        super.livingTick();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (isPassenger(passenger))
            passenger.setPosition(posX + getWidth() * Math.sin(Math.toRadians(-rotationYaw)), posY + 0.45, posZ + getWidth() * Math.cos(Math.toRadians(rotationYaw)));
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

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(70);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    protected SoundEvent getAmbientSound() {
        return BeastsSounds.VILE_EEL_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.VILE_EEL_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public float getEyeHeight(Pose pose) {
        return this.isChild() ? this.getHeight() : 1.3F;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (!this.world.isRemote) entityIn.startRiding(this, true);
        return super.attackEntityAsMob(entityIn);
    }
}