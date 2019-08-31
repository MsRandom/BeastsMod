package rando.beasts.common.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import rando.beasts.client.init.BeastsSounds;
import rando.beasts.common.block.CoralColor;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;

public class EntityBranchie extends EntityAnimal {

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityBranchie.class, DataSerializers.VARINT);

    public EntityBranchie(World worldIn) {
        super(worldIn);
        this.setSize(0.2F, 0.9F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, BeastsItems.REEF_MIXTURE, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.5D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(CoralColor.values()[rand.nextInt(CoralColor.values().length)]);
        return livingdata;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    public void setVariant(CoralColor variant) {
        this.dataManager.set(VARIANT, variant.ordinal());
    }

    public CoralColor getVariant() {
        return CoralColor.values()[this.dataManager.get(VARIANT)];
    }

    @Nullable
    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(BeastsBlocks.CORAL_BLOCK);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        Item item = this.getDropItem();

        if (item != null) {
            int i = this.rand.nextInt(3);
            if (lootingModifier > 0) i += this.rand.nextInt(lootingModifier + 1);
            for (int j = 0; j < i; ++j) this.entityDropItem(new ItemStack(item, 1, getVariant().ordinal()), 0);
        }
    }

    protected SoundEvent getAmbientSound() {
        return BeastsSounds.BRANCHIE_SCREAM;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public EntityBranchie createChild(EntityAgeable ageable) {
        return null;
    }

    public float getEyeHeight() {
        return this.isChild() ? this.height : 0.8F;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("variant", this.getVariant().ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(CoralColor.values()[compound.getInteger("variant")]);
    }
}
