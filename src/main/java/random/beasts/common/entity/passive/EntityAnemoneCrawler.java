package random.beasts.common.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
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
        this.tasks.addTask(0, new EntityAIWander(this, 0.2));
        this.tasks.addTask(0, new EntityAIMate(this, 0.2));
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
        if(rand.nextBoolean()) {
            EntityAgeable dropper = rand.nextBoolean() ? ageable : this;
            dropper.dropItem(BeastsItems.MEAT_SCRAPES, 1);
        }
        return new EntityAnemoneCrawler(world);
    }
}
