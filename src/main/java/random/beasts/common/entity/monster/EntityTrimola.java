package random.beasts.common.entity.monster;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.common.BeastsMod;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.network.BeastsGuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EntityTrimola extends EntityTameable implements IInventoryChangedListener {
    @SideOnly(Side.CLIENT)
    public static final KeyBinding ATTACK = new KeyBinding("trimola.attack", 19, "key.categories.misc");
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityTrimola.class, DataSerializers.VARINT);
    private static final DataParameter<ItemStack> SADDLE = EntityDataManager.createKey(EntityTrimola.class, DataSerializers.ITEM_STACK);
    public InventoryBasic inventory;
    public int attackTicks = 0;
    private boolean rearing;
    private int rearingTime = 0;

    public EntityTrimola(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 1.2F);
        initInventory();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
//        this.tasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, true, false, entity -> entity == getAttackTarget()));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(1, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0, false));
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

    public void travel(float strafe, float vertical, float forward) {
        if (this.getControllingPassenger() != null && this.canBeSteered()) {
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
    public void setTamedBy(EntityPlayer player) {
        super.setTamedBy(player);
        if(this.getRevengeTarget() == player)
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
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setVariant(this.rand.nextInt(2));
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
/*
        if (attackTicks > 0 && attackTicks < 300) ++attackTicks;
        else attackTicks = 0;
        if (attackTicks == 150) {
            world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox()).stream().filter(e -> e != this).forEach(this::attackEntityAsMob);
        }
*/

        if (this.isRearing()) {
            ++rearingTime;
            if (rearingTime >= 25) {
                rearingTime = 0;
                this.rearing = false;
            }
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        boolean attack = false;
        if (world.isRemote && ATTACK.isKeyDown()) {
            attack = true;
            this.rearing = true;
            this.rearingTime = 0;
        }
        if (attack) {
            attackTicks = 1;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
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
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", getVariant());
        if (!getSaddle().isEmpty()) {
            NBTTagCompound saddle = new NBTTagCompound();
            getSaddle().writeToNBT(saddle);
            compound.setTag("Saddle", saddle);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        initInventory();
        setVariant(compound.getInteger("Variant"));
        if (compound.hasKey("Saddle")) {
            ItemStack saddle = new ItemStack(compound.getCompoundTag("Saddle"));
            setSaddle(saddle);
            this.inventory.setInventorySlotContents(0, saddle);
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, @Nonnull EnumHand hand) {
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
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                this.heal(5);
                return true;
            }
        } else if (!this.isTamed() && stack.getItem() == Item.getItemFromBlock(BeastsBlocks.JELLY_LEAVES)) {
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

    private void initInventory() {
        this.inventory = new InventoryBasic(hasCustomName() ? getCustomNameTag() : "LandwhaleInventory", hasCustomName(), 1);
        this.inventory.addInventoryChangeListener(this);
    }

    @Override
    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        this.inventory.removeInventoryChangeListener(this);
        initInventory();
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
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
        if (source.getTrueSource() instanceof EntityLivingBase)
            setRevengeTarget((EntityLivingBase) source.getTrueSource());
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onInventoryChanged(IInventory inv) {
        setSaddle(inv.getStackInSlot(0));
    }
}
