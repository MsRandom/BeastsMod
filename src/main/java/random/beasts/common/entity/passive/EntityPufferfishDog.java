package random.beasts.common.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.init.BeastsEntities;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nonnull;
import java.util.UUID;

public class EntityPufferfishDog extends TameableEntity {
    private final DamageSource deathSource = new PufferfishDamage(this);
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(EntityPufferfishDog.class, DataSerializers.VARINT);
    private static final DataParameter<Float> THREAT_TIME = EntityDataManager.createKey(EntityPufferfishDog.class, DataSerializers.FLOAT);
    private int bounces = 0;
    private BlockPos jukeboxPosition;
    private boolean partyPufferfishDog;

    public EntityPufferfishDog(EntityType<? extends EntityPufferfishDog> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(2, sitGoal);
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new SwimGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 0.5, 2f, 5f) {
            @Override
            public boolean shouldExecute() {
                return !isInflated() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 0.5, 50) {
            @Override
            public boolean shouldExecute() {
                return !isSitting() && super.shouldExecute();
            }
        });
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public AgeableEntity createChild(@Nonnull AgeableEntity ageable) {
        EntityPufferfishDog child = BeastsEntities.PUFFERFISH_DOG.create(world);
        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            child.setOwnerId(uuid);
            child.setTamed(true);
        }
        return child;
    }

    /*@Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        this.dropItem(BeastsItems.PUFFER_SCALE, 1 + this.getRNG().nextInt(2));
    }*/

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(COLLAR_COLOR, DyeColor.RED.getId());
        this.dataManager.register(THREAT_TIME, 0.0f);
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("collarColor", this.getCollarColor().getId());
        compound.putBoolean("sitting", this.isSitting());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setCollarColor(DyeColor.byId(compound.getInt("collarColor")));
        this.setSitting(compound.getBoolean("sitting"));
    }

    protected SoundEvent getAmbientSound() {
        return this.rand.nextInt(3) == 0 ? (this.isTamed() && getHealth() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT) : SoundEvents.ENTITY_WOLF_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public void livingTick() {
        if (this.jukeboxPosition == null || this.jukeboxPosition.distanceSq(this.posX, this.posY, this.posZ, false) > 12.0D || this.world.getBlockState(this.jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
            this.partyPufferfishDog = false;
            this.jukeboxPosition = null;
        }

        if (!world.isRemote) {
            if (isInWater()) this.setAir(300);
            if (isInflated()) {
                setThreatTime(getThreatTime() + 1);
                if (onGround) {
                    if (bounces == 0) bounces = 1;
                    else setMotion(getMotion().add(0, 0.25 / bounces++, 0));
                } else setMotion(getMotion().add(0, -0.01, 0));
                if (getThreatTime() > 140) setInflated(false);
                for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().grow(1)))
                    if (entity != this.getOwner()) entity.attackEntityFrom(deathSource, 1.0F);
            } else bounces = 0;
        }
        super.livingTick();
    }

    @Override
    public boolean processInteract(PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (this.isTamed()) {
            if (!stack.isEmpty() && stack.getItem() instanceof DyeItem) {
                DyeColor color = ((DyeItem) stack.getItem()).getDyeColor();
                if (color != this.getCollarColor()) {
                    this.setCollarColor(color);
                    if (!player.abilities.isCreativeMode) stack.shrink(1);
                    return true;
                }
            }
            if (this.isOwner(player) && !this.world.isRemote && stack.isEmpty()) {
                this.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                return true;
            }
        } else if (stack.getItem() == BeastsItems.LEAFY_BONE) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                this.isJumping = false;
                this.navigator.clearPath();
                setMotion(0, getMotion().y, 0);
                this.setTamedBy(player);
                this.setHealth(16.0F);
                this.setSitting(true);
                this.playTameEffect(true);
                this.world.setEntityState(this, (byte) 7);
            } else {
                this.playTameEffect(false);
                this.world.setEntityState(this, (byte) 6);
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (source != DamageSource.FALL) {
            if (source.getImmediateSource() != null) setInflated(true);
            return super.attackEntityFrom(source, amount);
        }
        return false;
    }

    @Override
    public boolean canMateWith(@Nonnull AnimalEntity animal) {
        if (animal == this || !this.isTamed() || !(animal instanceof EntityPufferfishDog)) {
            return false;
        } else {
            EntityPufferfishDog entity = (EntityPufferfishDog) animal;
            return !(!entity.isTamed() || entity.isSitting()) && this.isInLove() && entity.isInLove();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SPIDER_EYE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setPartying(BlockPos pos, boolean p_191987_2_) {
        this.jukeboxPosition = pos;
        this.partyPufferfishDog = p_191987_2_;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isPartying() {
        return this.partyPufferfishDog;
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.dataManager.get(COLLAR_COLOR));
    }

    private void setCollarColor(DyeColor color) {
        this.dataManager.set(COLLAR_COLOR, color.getId());
    }

    private float getThreatTime() {
        return this.dataManager.get(THREAT_TIME);
    }

    private void setThreatTime(float time) {
        this.dataManager.set(THREAT_TIME, time);
    }

    public boolean isInflated() {
        return getThreatTime() > 0;
    }

    private void setInflated(boolean inflated) {
        playSound(inflated ? BeastsSounds.PUFFERFISH_BLOW_UP : BeastsSounds.PUFFERFISH_BLOW_OUT, getSoundVolume(), getSoundPitch());
        setSitting(false);
        setNoGravity(inflated);
        setThreatTime(inflated ? 1 : 0);
        if (inflated) setMotion(getMotion().add(0, 0.5, 0));
    }

    private static class PufferfishDamage extends EntityDamageSource {
        public PufferfishDamage(@Nonnull EntityPufferfishDog pufferpup) {
            super("pufferpup", pufferpup);
        }

        @Override
        public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
            LivingEntity entitylivingbase = entityLivingBaseIn.getAttackingEntity();
            String s = "death.attack." + this.damageType;
            String s1 = s + ".player";
            assert damageSourceEntity != null;
            return entitylivingbase != null ? new TranslationTextComponent(s1, entityLivingBaseIn.getDisplayName(), damageSourceEntity.getDisplayName(), entitylivingbase.getDisplayName()) : new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName(), damageSourceEntity.getDisplayName());
        }
    }
}
