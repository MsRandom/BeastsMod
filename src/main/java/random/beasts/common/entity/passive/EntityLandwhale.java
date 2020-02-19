package random.beasts.common.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeEventFactory;
import random.beasts.api.entity.IDriedAquatic;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.BeastsMod;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsEntities;
import random.beasts.common.init.BeastsItems;
import random.beasts.common.network.BeastsGuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityLandwhale extends EntityTameable implements IShearable, IDriedAquatic, IInventoryChangedListener {
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(EntityLandwhale.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SADDLE = EntityDataManager.createKey(EntityLandwhale.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_ID_CHEST = EntityDataManager.createKey(EntityLandwhale.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> COCONUT = EntityDataManager.createKey(EntityLandwhale.class, DataSerializers.BOOLEAN);
    public InventoryBasic inventory;
    public int animationTicks = 0;
    private int ticksSinceSheared = 0;
    private EntityItem target;

    public EntityLandwhale(World worldIn) {
        super(worldIn);
        this.initChest();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SHEARED, false);
        this.dataManager.register(SADDLE, false);
        this.dataManager.register(DATA_ID_CHEST, false);
        this.dataManager.register(COCONUT, false);
    }

    public void setTarget(EntityItem coconut) {
        this.target = coconut;
    }

    public boolean hasChest() {
        return this.dataManager.get(DATA_ID_CHEST);
    }

    public void setChested(boolean chested) {
        this.dataManager.set(DATA_ID_CHEST, chested);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if (this.hasChest()) {
            if (!this.world.isRemote) {
                this.dropItem(Item.getItemFromBlock(Blocks.CHEST), 1);
            }

            this.setChested(false);
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("sheared", getSheared());
        compound.setInteger("shearTicks", ticksSinceSheared);
        compound.setBoolean("Chest", this.hasChest());
        compound.setBoolean("Coconut", this.dataManager.get(COCONUT));

        if (this.hasChest()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 2; i < this.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = this.inventory.getStackInSlot(i);

                if (!itemstack.isEmpty()) {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    nbttagcompound.setByte("Slot", (byte) i);
                    itemstack.writeToNBT(nbttagcompound);
                    nbttaglist.appendTag(nbttagcompound);
                }
            }

            compound.setTag("Items", nbttaglist);
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setSheared(compound.getBoolean("sheared"));
        this.ticksSinceSheared = compound.getInteger("shearTicks");
        this.setChested(compound.getBoolean("Chest"));
        this.dataManager.set(COCONUT, compound.getBoolean("Coconut"));

        if (this.hasChest()) {
            NBTTagList nbttaglist = compound.getTagList("Items", 10);
            this.initChest();

            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("Slot") & 255;

                if (j >= 2 && j < this.inventory.getSizeInventory()) {
                    this.inventory.setInventorySlotContents(j, new ItemStack(nbttagcompound));
                }
            }
        }

        if (!this.world.isRemote) this.dataManager.set(SADDLE, !this.inventory.getStackInSlot(0).isEmpty());
    }

    protected void initChest() {
        InventoryBasic inv = this.inventory;
        this.inventory = new InventoryBasic(hasCustomName() ? getCustomNameTag() : "LandwhaleInventory", hasCustomName(), this.hasChest() ? 17 : 1);
        this.inventory.setCustomName(this.getName());

        if (inv != null) {
            inv.removeInventoryChangeListener(this);
            int i = Math.min(inv.getSizeInventory(), this.inventory.getSizeInventory());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inv.getStackInSlot(j);

                if (!itemstack.isEmpty()) {
                    this.inventory.setInventorySlotContents(j, itemstack.copy());
                }
            }
        }

        this.inventory.addInventoryChangeListener(this);
        if (!this.world.isRemote) this.dataManager.set(SADDLE, !this.inventory.getStackInSlot(0).isEmpty());
    }

    @Override
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
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (isTamed()) this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60);
        else this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60);
        else this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
    }

    @Override
    public boolean canBeSteered() {
        return !inventory.getStackInSlot(0).isEmpty();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float f;
            int i = this.getPassengers().indexOf(passenger);
            if (i == 0) f = 0.2F;
            else f = -0.6F;
            Vec3d vec3d = (new Vec3d(f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
            passenger.setPosition(this.posX + vec3d.x, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ + vec3d.z);
        }
    }

    public void travel(float strafe, float vertical, float forward) {
        if (this.getControllingPassenger() != null && this.canBeSteered() && !inventory.getStackInSlot(0).isEmpty()) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) this.getControllingPassenger();
            this.rotationYaw = entitylivingbase.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.renderYawOffset;
            strafe = entitylivingbase.moveStrafing * 0.5F;
            forward = entitylivingbase.moveForward;
            this.stepHeight = 1.0F;
            if (forward <= 0.0F) forward *= 0.25F;

            if (this.canPassengerSteer()) {
                this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                super.travel(strafe, vertical, forward);
            } else if (entitylivingbase instanceof EntityPlayer) {
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

            if (f2 > 1.0F) f2 = 1.0F;
            this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        } else super.travel(strafe, vertical, forward);
    }

    @Override
    public boolean processInteract(EntityPlayer player, @Nonnull EnumHand hand) {
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
                player.openGui(BeastsMod.instance, BeastsGuiHandler.GUI_LANDWHALE.getId(), world, this.getEntityId(), 0, 0);
                return true;
            }
            if (!this.inventory.getStackInSlot(0).isEmpty() && !player.isPassenger(this) && this.getPassengers().size() < 2) {
                player.startRiding(this);
                return true;
            }
        } else if (!this.isTamed() && stack.getItem() == BeastsItems.COCONUT_JUICE) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
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
    public void onLivingUpdate() {
        super.onLivingUpdate();
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
                if (target.isDead) target = null;
                else {
                    this.getNavigator().tryMoveToEntityLiving(target, 2);
                    if (getDistanceSq(target) < 3) {
                        this.target.setDead();
                        this.dataManager.set(COCONUT, true);
                    }
                }
            }
            EntityLivingBase owner = getOwner();
            if (this.dataManager.get(COCONUT) && owner != null) {
                this.getNavigator().tryMoveToEntityLiving(owner, 2);
                if (getDistanceSq(owner) < 3) {
                    this.dropItem(BeastsItems.COCONUT, 1);
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
        return this.height + 0.1F;
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
    public void playLivingSound() {
        super.playLivingSound();
        if (world.isRemote) animationTicks = 1;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public EntityLandwhale createChild(EntityAgeable ageable) {
        return BeastsEntities.LANDWHALE.create(this.world);
    }

    public float getEyeHeight() {
        return this.isChild() ? this.height : 2.0F;
    }

    public boolean getSheared() {
        return this.dataManager.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.dataManager.set(SHEARED, sheared);
    }

    @Override
    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        if (this.inventory != null) this.inventory.setCustomName(name);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return !this.getSheared() && !this.isChild();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        setSheared(true);
        int i = 1 + this.rand.nextInt(3);
        List<ItemStack> ret = new ArrayList<>();
        for (int j = 0; j < i; ++j)
            ret.add(new ItemStack(Item.getItemFromBlock(BeastsBlocks.CORAL_BLOCK), 1, CoralColor.getRandom(rand).ordinal()));
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
}
