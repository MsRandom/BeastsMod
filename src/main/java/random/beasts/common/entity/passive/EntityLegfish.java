package random.beasts.common.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IMobEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;

public class EntityLegfish extends EntityAnimal {
    public static final HashMap<Integer, Integer> VARIANTS = new HashMap<>();
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityLegfish.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityLegfish.class, DataSerializers.VARINT);

    static {
        VARIANTS.put(0, 2);
        VARIANTS.put(1, 5);
        VARIANTS.put(2, 4);
    }

    public EntityLegfish(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.goalSelector.addGoal(0, new EntityAIWanderAvoidWater(this, 0.2D));
        this.goalSelector.addGoal(0, new EntityAISwimming(this));
        this.goalSelector.addGoal(1, new EntityAILookIdle(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(TYPE, 0);
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
        int type = rand.nextInt(3);
        setType(type);
        setVariant(rand.nextInt(VARIANTS.get(type)));
        return livingdata;
    }

    public int getType() {
        return dataManager.get(TYPE);
    }

    private void setType(int value) {
        dataManager.set(TYPE, value);
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
        compound.putInt("type", getType());
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
        setType(compound.getInt("type"));
        setVariant(compound.getInt("variant"));
    }
}
