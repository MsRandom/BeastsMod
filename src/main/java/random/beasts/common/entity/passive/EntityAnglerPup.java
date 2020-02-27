package random.beasts.common.entity.passive;

import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nonnull;

public class EntityAnglerPup extends TameableEntity {

    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(EntityAnglerPup.class, DataSerializers.VARINT);
    private static final DataParameter<Float> THREAT_TIME = EntityDataManager.createKey(EntityAnglerPup.class, DataSerializers.FLOAT);
    private BlockPos jukeboxPosition;
    private boolean partyPufferfishDog;

    public EntityAnglerPup(EntityType<? extends EntityAnglerPup> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(2, sitGoal);
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new SwimGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 0.5, 2f, 5f));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 0.5, 50) {
            @Override
            public boolean shouldExecute() {
                return !isSitting() && super.shouldExecute();
            }
        });
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8d);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(COLLAR_COLOR, DyeColor.RED.getId());
        this.dataManager.register(THREAT_TIME, 0.0f);
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

    @Override
    public void livingTick() {
        if (this.jukeboxPosition == null || this.jukeboxPosition.distanceSq(this.posX, this.posY, this.posZ, false) > 12.0D || this.world.getBlockState(this.jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
            this.partyPufferfishDog = false;
            this.jukeboxPosition = null;
        }

        if (!world.isRemote) {
            if (isInWater()) this.setAir(300);
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
}
