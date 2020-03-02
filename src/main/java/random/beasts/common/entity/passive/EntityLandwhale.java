package random.beasts.common.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeEventFactory;
import random.beasts.api.entity.IDriedAquatic;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.BeastsMod;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsEntities;
import random.beasts.common.init.BeastsItems;
import random.beasts.common.inventory.ContainerLandwhaleInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntityLandwhale extends TameableEntity implements IShearable, IDriedAquatic, IInventoryChangedListener, INamedContainerProvider {
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(EntityLandwhale.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SADDLE = EntityDataManager.createKey(EntityLandwhale.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_ID_CHEST = EntityDataManager.createKey(EntityLandwhale.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> COCONUT = EntityDataManager.createKey(EntityLandwhale.class, DataSerializers.BOOLEAN);
    public Inventory inventory;
    public int animationTicks = 0;
    private int ticksSinceSheared = 0;
    private ItemEntity target;

    public EntityLandwhale(EntityType<? extends EntityLandwhale> type, World worldIn) {
        super(type, worldIn);
        this.initChest();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHEARED, false);
        this.dataManager.register(SADDLE, false);
        this.dataManager.register(DATA_ID_CHEST, false);
        this.dataManager.register(COCONUT, false);
    }

    public void setTarget(ItemEntity coconut) {
        this.target = coconut;
    }

    public boolean hasChest() {
        return this.dataManager.get(DATA_ID_CHEST);
    }

    public void setChested(boolean chested) {
        this.dataManager.set(DATA_ID_CHEST, chested);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if (this.hasChest()) {
            if (!this.world.isRemote) {
                this.entityDropItem(new ItemStack(Blocks.CHEST, 1));
            }

            this.setChested(false);
        }
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("sheared", getSheared());
        compound.putInt("shearTicks", ticksSinceSheared);
        compound.putBoolean("Chest", this.hasChest());
        compound.putBoolean("Coconut", this.dataManager.get(COCONUT));

        if (this.hasChest()) {
            ListNBT nbttaglist = new ListNBT();

            for (int i = 2; i < this.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = this.inventory.getStackInSlot(i);

                if (!itemstack.isEmpty()) {
                    CompoundNBT nbttagcompound = new CompoundNBT();
                    nbttagcompound.putByte("Slot", (byte) i);
                    itemstack.write(nbttagcompound);
                    nbttaglist.add(nbttagcompound);
                }
            }

            compound.put("Items", nbttaglist);
        }
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setSheared(compound.getBoolean("sheared"));
        this.ticksSinceSheared = compound.getInt("shearTicks");
        this.setChested(compound.getBoolean("Chest"));
        this.dataManager.set(COCONUT, compound.getBoolean("Coconut"));

        if (this.hasChest()) {
            ListNBT nbttaglist = compound.getList("Items", 10);
            this.initChest();

            for (int i = 0; i < nbttaglist.size(); ++i) {
                CompoundNBT nbttagcompound = nbttaglist.getCompound(i);
                int j = nbttagcompound.getByte("Slot") & 255;

                if (j >= 2 && j < this.inventory.getSizeInventory()) {
                    this.inventory.setInventorySlotContents(j, ItemStack.read(nbttagcompound));
                }
            }
        }

        if (!this.world.isRemote) this.dataManager.set(SADDLE, !this.inventory.getStackInSlot(0).isEmpty());
    }

    protected void initChest() {
        Inventory inv = this.inventory;
        this.inventory = new Inventory(this.hasChest() ? 17 : 1);

        if (inv != null) {
            inv.removeListener(this);
            int i = Math.min(inv.getSizeInventory(), this.inventory.getSizeInventory());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inv.getStackInSlot(j);

                if (!itemstack.isEmpty()) {
                    this.inventory.setInventorySlotContents(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        if (!this.world.isRemote) this.dataManager.set(SADDLE, !this.inventory.getStackInSlot(0).isEmpty());
    }

    /*@Override
    protected Item getDropItem() {
        return Items.LEATHER;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        Block chop = BeastsBlocks.CORAL_BLOCK;
        int i = this.rand.nextInt(4);
        if (lootingModifier > 0) i += this.rand.nextInt(lootingModifier + 1);
        for (int j = 0; j < i; ++j) this.dropItem(Item.getItemFromBlock(chop), 1);
        this.dropItem(Objects.requireNonNull(Item.getItemFromBlock(chop)), 1);
    }*/

    protected void registerAttributes() {
        super.registerAttributes();
        if (isTamed()) this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60);
        else this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60);
        else this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
    }

    @Override
    public boolean canBeSteered() {
        return !inventory.getStackInSlot(0).isEmpty();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            if (world.isRemote && passenger == BeastsMod.proxy.getPlayer() && BeastsMod.proxy.isClientSneaking()) {
                removePassenger(passenger);
                return;
            }
            float f;
            int i = this.getPassengers().indexOf(passenger);
            if (i == 0) f = 0.2F;
            else f = -0.6F;
            Vec3d vec3d = (new Vec3d(f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
            passenger.setPosition(this.posX + vec3d.x, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ + vec3d.z);
        }
    }

    public void travel(Vec3d motion) {
        if (this.getControllingPassenger() != null && this.canBeSteered() && !inventory.getStackInSlot(0).isEmpty()) {
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
                this.setAIMoveSpeed((float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
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
    public boolean processInteract(PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == Items.SHEARS && !this.getSheared()) return super.processInteract(player, hand);
        if (!this.hasChest() && stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
            this.setChested(true);
            this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.initChest();
            return true;
        }
        if (!this.isChild() && this.isTamed() && (this.isOwner(player) || this.getControllingPassenger() != null)) {
            if (player.isSneaking() && this.getSheared()) {
                player.openContainer(this);
                return true;
            }
            if (!this.inventory.getStackInSlot(0).isEmpty() && !player.isPassenger(this) && this.getPassengers().size() < 2) {
                player.startRiding(this);
                return true;
            }
        } else if (!this.isTamed() && stack.getItem() == BeastsItems.COCONUT_JUICE) {
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

    @Override
    public void livingTick() {
        super.livingTick();
        if (world.isRemote) {
            if (animationTicks > 0 && animationTicks < 180) animationTicks += 5;
            else animationTicks = 0;
        }

        if (getSheared()) {
            if (inventory.getStackInSlot(0).isEmpty() && ticksSinceSheared > 48000) setSheared(false);
            ticksSinceSheared++;
        } else ticksSinceSheared = 0;
        if (!world.isRemote) {
            if (target != null) {
                if (!target.isAlive()) target = null;
                else {
                    this.getNavigator().tryMoveToEntityLiving(target, 2);
                    if (getDistanceSq(target) < 3) {
                        this.target.remove();
                        this.dataManager.set(COCONUT, true);
                    }
                }
            }
            LivingEntity owner = getOwner();
            if (this.dataManager.get(COCONUT) && owner != null) {
                this.getNavigator().tryMoveToEntityLiving(owner, 2);
                if (getDistanceSq(owner) < 3) {
                    entityDropItem(new ItemStack(BeastsItems.COCONUT));
                    this.dataManager.set(COCONUT, false);
                }
            }
        }
    }

    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public double getMountedYOffset() {
        return this.getHeight() + 0.1F;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == BeastsItems.REEF_MIXTURE;
    }

    protected SoundEvent getAmbientSound() {
        return BeastsSounds.LANDWHALE_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.LANDWHALE_HURT;
    }

    @Override
    public void playAmbientSound() {
        super.playAmbientSound();
        if (world.isRemote) animationTicks = 1;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public EntityLandwhale createChild(AgeableEntity ageable) {
        return BeastsEntities.LANDWHALE.create(this.world);
    }

    public float getEyeHeight(Pose pose) {
        return this.isChild() ? this.getHeight() : 2.0F;
    }

    public boolean getSheared() {
        return this.dataManager.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.dataManager.set(SHEARED, sheared);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IWorldReader world, BlockPos pos) {
        return !this.getSheared() && !this.isChild();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IWorld world, BlockPos pos, int fortune) {
        setSheared(true);
        int i = 1 + this.rand.nextInt(3);
        List<ItemStack> ret = new ArrayList<>();
        for (int j = 0; j < i; ++j)
            ret.add(new ItemStack(BeastsItems.CORAL_BLOCKS.get(CoralColor.getRandom(rand))));
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {
        boolean flag = this.dataManager.get(SADDLE);
        if (!this.world.isRemote) this.dataManager.set(SADDLE, !this.inventory.getStackInSlot(0).isEmpty());
        if (this.ticksExisted > 20 && !flag && this.dataManager.get(SADDLE))
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new ContainerLandwhaleInventory(p_createMenu_1_, this, p_createMenu_3_);
    }
}
