package net.msrandom.beasts.common.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.client.init.BeastsSounds;
import net.msrandom.beasts.common.init.BeastsEntities;
import net.msrandom.beasts.common.init.BeastsItems;

import javax.annotation.Nonnull;
import java.util.UUID;

public class EntityPufferfishDog extends EntityTameable {
    private final DamageSource deathSource = new PufferfishDamage(this);
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(EntityPufferfishDog.class, DataSerializers.VARINT);
    private static final DataParameter<Float> THREAT_TIME = EntityDataManager.createKey(EntityPufferfishDog.class, DataSerializers.FLOAT);
    private int bounces = 0;
    private BlockPos jukeboxPosition;
    private boolean partyPufferfishDog;

    public EntityPufferfishDog(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(2, aiSit);
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(2, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIFollowOwner(this, 0.5, 2f, 5f) {
            @Override
            public boolean shouldExecute() {
                return !isInflated() && super.shouldExecute();
            }
        });
        this.tasks.addTask(2, new EntityAIWander(this, 0.5, 50) {
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
    public EntityAgeable createChild(@Nonnull EntityAgeable ageable) {
        EntityPufferfishDog child = BeastsEntities.PUFFERFISH_DOG.create(world);
        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            child.setOwnerId(uuid);
            child.setTamed(true);
        }
        return child;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        this.dropItem(BeastsItems.PUFFER_SCALE, 1 + this.getRNG().nextInt(2));
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(COLLAR_COLOR, EnumDyeColor.RED.getDyeDamage());
        this.dataManager.register(THREAT_TIME, 0.0f);
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("collarColor", this.getCollarColor().getDyeDamage());
        compound.setBoolean("sitting", this.isSitting());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setCollarColor(EnumDyeColor.byDyeDamage(compound.getInteger("collarColor")));
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
    public void onLivingUpdate() {
        if (this.jukeboxPosition == null || this.jukeboxPosition.distanceSq(this.posX, this.posY, this.posZ) > 12.0D || this.world.getBlockState(this.jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
            this.partyPufferfishDog = false;
            this.jukeboxPosition = null;
        }

        if (!world.isRemote) {
            if (isInWater()) this.setAir(300);
            if (isInflated()) {
                setThreatTime(getThreatTime() + 1);
                if (onGround) {
                    if (bounces == 0) bounces = 1;
                    else motionY += 0.25 / bounces++;
                } else motionY -= 0.01;
                if (getThreatTime() > 140) setInflated(false);
                for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(1)))
                    if (entity != this.getOwner()) entity.attackEntityFrom(deathSource, 1.0F);
            } else bounces = 0;
        }
        super.onLivingUpdate();
    }

    @Override
    public boolean processInteract(EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (this.isTamed()) {
            if (!stack.isEmpty() && stack.getItem() == Items.DYE) {
                EnumDyeColor color = EnumDyeColor.byDyeDamage(stack.getMetadata());
                if (color != this.getCollarColor()) {
                    this.setCollarColor(color);
                    if (!player.capabilities.isCreativeMode) stack.shrink(1);
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
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                this.isJumping = false;
                this.navigator.clearPath();
                this.motionX = 0;
                this.motionZ = 0;
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
    public boolean canMateWith(@Nonnull EntityAnimal animal) {
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
    @SideOnly(Side.CLIENT)
    public void setPartying(BlockPos pos, boolean p_191987_2_) {
        this.jukeboxPosition = pos;
        this.partyPufferfishDog = p_191987_2_;
    }

    @SideOnly(Side.CLIENT)
    public boolean isPartying() {
        return this.partyPufferfishDog;
    }

    public EnumDyeColor getCollarColor() {
        return EnumDyeColor.byDyeDamage(this.dataManager.get(COLLAR_COLOR) & 15);
    }

    private void setCollarColor(EnumDyeColor color) {
        this.dataManager.set(COLLAR_COLOR, color.getDyeDamage());
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
        motionY += inflated ? 0.5 : 0;
    }

    private static class PufferfishDamage extends EntityDamageSource {
        public PufferfishDamage(@Nonnull EntityPufferfishDog pufferpup) {
            super("pufferpup", pufferpup);
        }

        @SideOnly(Side.CLIENT)
        @Override
        public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
            EntityLivingBase entitylivingbase = entityLivingBaseIn.getAttackingEntity();
            String s = "death.attack." + this.damageType;
            String s1 = s + ".player";
            assert damageSourceEntity != null;
            return entitylivingbase != null && I18n.hasKey(s1) ? new TextComponentTranslation(s1, entityLivingBaseIn.getDisplayName(), damageSourceEntity.getDisplayName(), entitylivingbase.getDisplayName()) : new TextComponentTranslation(s, entityLivingBaseIn.getDisplayName(), damageSourceEntity.getDisplayName());
        }
    }
}
