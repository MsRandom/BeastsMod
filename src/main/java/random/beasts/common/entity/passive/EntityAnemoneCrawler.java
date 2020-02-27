package random.beasts.common.entity.passive;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import random.beasts.common.init.BeastsEntities;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;

public class EntityAnemoneCrawler extends AnimalEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityAnemoneCrawler.class, DataSerializers.VARINT);

    public EntityAnemoneCrawler(EntityType<? extends EntityAnemoneCrawler> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 0.3));
        this.goalSelector.addGoal(2, new PanicGoal(this, 0.4D));
        this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.2));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.35D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(VARIANT, 0);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(DifficultyInstance difficulty, @Nullable ILivingEntityData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        setVariant(rand.nextInt(3));
        return livingdata;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isFood() && stack.getItem().getFood().isMeat();
    }

    public int getVariant() {
        return dataManager.get(VARIANT);
    }

    private void setVariant(int value) {
        dataManager.set(VARIANT, value);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("variant", getVariant());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setVariant(compound.getInt("variant"));
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        if (ageable instanceof EntityAnemoneCrawler) {
            EntityAnemoneCrawler child = BeastsEntities.ANEMONE_CRAWLER.create(world);
            EntityAnemoneCrawler dropper = rand.nextBoolean() ? (EntityAnemoneCrawler) ageable : this;
            if (rand.nextBoolean()) dropper.dropItem(BeastsItems.MEAT_SCRAPES, 1);
            child.setVariant(dropper.getVariant());
            if (this.getRNG().nextInt(10) == 0)
                child.setVariant(3);
            return child;
        }
        return null;
    }
}
