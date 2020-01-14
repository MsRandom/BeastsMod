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
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
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

public class EntityTrimola extends EntityTameable implements IInventoryChangedListener {
    @SideOnly(Side.CLIENT)
    private static final KeyBinding ATTACK = new KeyBinding("trimola.attack", 19, "key.categories.misc");
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityTrimola.class, DataSerializers.VARINT);
    private static final DataParameter<ItemStack> SADDLE = EntityDataManager.createKey(EntityTrimola.class, DataSerializers.ITEM_STACK);
    public InventoryBasic inventory;
    public int attackTicks = 0;

    public EntityTrimola(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 1.0F);
        initInventory();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
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
        if (attackTicks > 0 && attackTicks < 300) ++attackTicks;
        else attackTicks = 0;
        if (attackTicks == 150) {
            world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox()).stream().filter(e -> e == this).forEach(this::attackEntityAsMob);
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        boolean attack = false;
        if (world.isRemote && ATTACK.isKeyDown()) {
            attack = true;
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
        this.inventory.setInventorySlotContents(0, saddle);
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
            setSaddle(new ItemStack(compound.getCompoundTag("Saddle")));
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
                this.heal(((ItemFood) stack.getItem()).getHealAmount(stack));
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
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getTrueSource() instanceof EntityLivingBase)
            setAttackTarget((EntityLivingBase) source.getTrueSource());
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onInventoryChanged(IInventory inv) {
        setSaddle(inv.getStackInSlot(0));
    }
}
