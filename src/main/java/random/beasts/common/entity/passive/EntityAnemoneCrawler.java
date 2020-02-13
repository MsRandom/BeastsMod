package random.beasts.common.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;

public class EntityAnemoneCrawler extends EntityAnimal {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityAnemoneCrawler.class, DataSerializers.VARINT);

    public EntityAnemoneCrawler(World worldIn) {
        super(worldIn);
        setSize(0.5f, 0.5f);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 0.3));
        this.tasks.addTask(2, new EntityAIPanic(this, 0.4D));
        this.tasks.addTask(3, new EntityAIWander(this, 0.2));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.35D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.4D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(VARIANT, 0);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        setVariant(rand.nextInt(3));
        return livingdata;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() instanceof ItemFood && ((ItemFood) stack.getItem()).isWolfsFavoriteMeat();
    }

    public int getVariant() {
        return dataManager.get(VARIANT);
    }

    private void setVariant(int value) {
        dataManager.set(VARIANT, value);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("variant", getVariant());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setVariant(compound.getInteger("variant"));
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        if (ageable instanceof EntityAnemoneCrawler) {
            EntityAnemoneCrawler child = new EntityAnemoneCrawler(world);
            EntityAnemoneCrawler dropper = rand.nextBoolean() ? (EntityAnemoneCrawler) ageable : this;
            if (rand.nextBoolean()) dropper.dropItem(BeastsItems.MEAT_SCRAPES, 1);
            child.setVariant(dropper.getVariant());
            if(this.getRNG().nextInt(10) == 0)
                this.setVariant(3);
            return child;
        }
        return null;
    }
}
