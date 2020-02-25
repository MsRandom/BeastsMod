package random.beasts.common.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IMobEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntitySlimeSlug extends EntityAnimal {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntitySlimeSlug.class, DataSerializers.VARINT);

    public EntitySlimeSlug(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.goalSelector.addGoal(0, new EntityAIWanderAvoidWater(this, 0.2D));
        this.goalSelector.addGoal(0, new EntityAISwimming(this));
        this.goalSelector.addGoal(1, new EntityAIPanic(this, 0.4D));
        this.goalSelector.addGoal(2, new EntityAILookIdle(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(VARIANT, 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        
    }

    @Nullable
    @Override
    public IMobEntityData onInitialSpawn(DifficultyInstance difficulty, @Nullable IMobEntityData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(this.getRNG().nextInt(4));
        return livingdata;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        super.dropFewItems(wasRecentlyHit, lootingModifier);
        this.entityDropItem(new ItemStack(Items.SLIME_BALL, this.getRNG().nextInt(2) + 1), 0);
    }

    public int getVariant() {
        return dataManager.get(VARIANT);
    }

    private void setVariant(int value) {
        dataManager.set(VARIANT, value);
    }

    @Override
    public void writeEntityToNBT(CompoundNBT compound) {
        super.writeEntityToNBT(compound);
        compound.putInt("variant", getVariant());
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    public void readEntityFromNBT(CompoundNBT compound) {
        super.readEntityFromNBT(compound);
        setVariant(compound.getInt("variant"));
    }
}
