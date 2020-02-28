package random.beasts.common.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import random.beasts.common.BeastsMod;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.network.BeastsGuiHandler;
import random.beasts.common.network.packet.PacketTrimolaAttack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EntityTrimola extends TameableEntity implements IInventoryChangedListener {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityTrimola.class, DataSerializers.VARINT);
    private static final DataParameter<ItemStack> SADDLE = EntityDataManager.createKey(EntityTrimola.class, DataSerializers.ITEMSTACK);
    public Inventory inventory;
    public int attackTimeout = 0;
    private boolean rearing;
    private int rearCoolDown;
    private int rearingTime = 0;

    public EntityTrimola(EntityType<? extends EntityTrimola> type, World worldIn) {
        super(type, worldIn);
        initInventory();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
//        this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, entity -> entity == getAttackTarget()));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
    }

    public boolean isRearing(){return rearing;}

    @Override
    public boolean canBeSteered() {
        return true;
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    public void travel(Vec3d motion) {
        if (this.getControllingPassenger() != null && this.canBeSteered()) {
            LivingEntity entitylivingbase = (LivingEntity) this.getControllingPassenger();
            this.rotationYaw = entitylivingbase.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.renderYawOffset;
            motion = new Vec3d(entitylivingbase.moveStrafing * 0.5F, motion.y, entitylivingbase.moveForward);
            this.stepHeight = 1.0F;
            if (motion.z <= 0.0F) motion = motion.mul(0, 0, 0.25F);

            if (this.canPassengerSteer()) {
                this.setAIMoveSpeed((float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).get());
                super.travel(motion);
            } else if (entitylivingbase instanceof PlayerEntity) {
                setMotion(0, 0, 0);
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

            if (f2 > 1.0F) f2 = 1.0F;
            this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        } else super.travel(motion);
    }

    @Override
    public void setTamedBy(PlayerEntity player) {
        super.setTamedBy(player);
        if (this.getRevengeTarget() == player)
            this.setRevengeTarget(null);
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if(id == 69){
            this.rearing = true;
        }
        super.handleStatusUpdate(id);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(DifficultyInstance difficulty, @Nullable ILivingEntityData livingdata) {
        this.setVariant(this.rand.nextInt(2));
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.isRearing()) {
            ++rearingTime;
            if (rearingTime >= 25) {
                rearingTime = 0;
                this.rearing = false;
            }
        }
        if (rearCoolDown > 0)
            rearCoolDown--;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (world.isRemote) {
            if (isPassenger(passenger) && passenger == BeastsMod.proxy.getPlayer()) {
                if (BeastsMod.proxy.isClientSneaking()) {
                    removePassenger(passenger);
                    return;
                }
                //There is 2 cool downs??
                if (attackTimeout == 0 && BeastsMod.proxy.isTrimolaAttacking() && rearCoolDown == 0) {
                    attackTimeout = 1;
                    this.rearing = true;
                    this.rearingTime = 0;
                    rearCoolDown = 20;
                    BeastsMod.NETWORK_CHANNEL.sendToServer(new PacketTrimolaAttack(this.getEntityId()));
                }
            }
            if (attackTimeout != 0)
                if (attackTimeout++ >= 100)
                    attackTimeout = 0;
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SADDLE, ItemStack.EMPTY);
        dataManager.register(VARIANT, 0);
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    public ItemStack getSaddle() {
        return this.dataManager.get(SADDLE);
    }

    public void setSaddle(ItemStack saddle) {
        this.dataManager.set(SADDLE, saddle);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", getVariant());
        if (!getSaddle().isEmpty()) {
            CompoundNBT saddle = new CompoundNBT();
            getSaddle().write(saddle);
            compound.put("Saddle", saddle);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        initInventory();
        setVariant(compound.getInt("Variant"));
        if (compound.contains("Saddle")) {
            ItemStack saddle = ItemStack.read(compound.getCompound("Saddle"));
            setSaddle(saddle);
            this.inventory.setInventorySlotContents(0, saddle);
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!this.isChild() && this.isTamed() && (this.isOwner(player) || this.getControllingPassenger() != null)) {
            if (player.isSneaking()) {
                player.openGui(BeastsMod.instance, BeastsGuiHandler.GUI_TRIMOLA.getId(), world, this.getEntityId(), 0, 0);
                return true;
            }
            if (!this.getSaddle().isEmpty() && !player.isPassenger(this) && this.getPassengers().size() < 2) {
                player.startRiding(this);
                return true;
            }
            if (stack.getItem() == Item.getItemFromBlock(BeastsBlocks.JELLY_LEAVES)) {
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                this.heal(5);
                return true;
            }
        } else if (!this.isTamed() && stack.getItem() == Item.getItemFromBlock(BeastsBlocks.JELLY_LEAVES)) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte) 6);
                }
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    private void initInventory() {
        this.inventory = new Inventory(1);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.rearing && super.isMovementBlocked();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.rearing = true;
        this.world.setEntityState(this, (byte)69);
        entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getTrueSource() instanceof LivingEntity)
            setRevengeTarget((LivingEntity) source.getTrueSource());
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onInventoryChanged(IInventory inv) {
        setSaddle(inv.getStackInSlot(0));
    }
}
