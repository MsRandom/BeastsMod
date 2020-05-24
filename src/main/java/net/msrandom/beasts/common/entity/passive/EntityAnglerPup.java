package net.msrandom.beasts.common.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.common.init.BeastsItems;

import javax.annotation.Nonnull;

public class EntityAnglerPup extends EntityTameable {
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(EntityAnglerPup.class, DataSerializers.VARINT);
    private BlockPos jukeboxPosition;
    private boolean partyAnglerPup;

    public EntityAnglerPup(World worldIn) {
        super(worldIn);
        this.setSize(0.3f, 0.3f);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, aiSit);
        this.tasks.addTask(2, new EntityAIFollowOwner(this, 0.5, 2f, 5f));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.5d, true));
        this.tasks.addTask(4, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 0.5d, 50) {
            @Override
            public boolean shouldExecute() {
                return !isSitting() && super.shouldExecute();
            }
        });
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (isSitting()) {
            this.getNavigator().clearPath();
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityAnglerPup(this.world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(COLLAR_COLOR, EnumDyeColor.RED.getDyeDamage());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("collarColor", this.getCollarColor().getDyeDamage());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setCollarColor(EnumDyeColor.byDyeDamage(compound.getInteger("collarColor")));
    }

    @Override
    public void onLivingUpdate() {
        if (this.jukeboxPosition == null || this.jukeboxPosition.distanceSq(this.posX, this.posY, this.posZ) > 12.0D || this.world.getBlockState(this.jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
            this.partyAnglerPup = false;
            this.jukeboxPosition = null;
        }
        super.onLivingUpdate();
    }

    @Override
    public boolean processInteract(EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (this.isTamed() && this.isOwner(player)) {
            if (stack.isEmpty()) {
                this.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                return true;
            } else if (stack.getItem() == Items.DYE) {
                EnumDyeColor color = EnumDyeColor.byDyeDamage(stack.getMetadata());
                if (color != this.getCollarColor()) {
                    this.setCollarColor(color);
                    if (!player.capabilities.isCreativeMode) stack.shrink(1);
                    return true;
                }
                return false;
            } else if (stack.getItem() == Items.FISH) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                this.setHealth(2.0F);
                return true;
            } else if (stack.getItem() == BeastsItems.MEAT_SCRAPES) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                this.setInLove(player);
                return true;
            }
        } else if (stack.getItem() == BeastsItems.TUBEWORMS) {
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
    public int getBrightnessForRender() {
        return Math.max(155, super.getBrightnessForRender());
    }

    @Override
    public boolean canMateWith(@Nonnull EntityAnimal animal) {
        if (animal == this || !this.isTamed() || !(animal instanceof EntityAnglerPup)) {
            return false;
        } else {
            EntityAnglerPup entity = (EntityAnglerPup) animal;
            return !(!entity.isTamed() || entity.isSitting()) && this.isInLove() && entity.isInLove();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPartying(BlockPos pos, boolean p_191987_2_) {
        this.jukeboxPosition = pos;
        this.partyAnglerPup = p_191987_2_;
    }

    @SideOnly(Side.CLIENT)
    public boolean isPartying() {
        return this.partyAnglerPup;
    }

    public EnumDyeColor getCollarColor() {
        return EnumDyeColor.byDyeDamage(this.dataManager.get(COLLAR_COLOR) & 15);
    }

    private void setCollarColor(EnumDyeColor color) {
        this.dataManager.set(COLLAR_COLOR, color.getDyeDamage());
    }

}
