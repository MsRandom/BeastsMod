package random.beasts.common.entity.passive;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;

public class EntityLegfish extends AnimalEntity {
    public static final HashMap<Integer, Integer> VARIANTS = new HashMap<>();
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityLegfish.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityLegfish.class, DataSerializers.VARINT);

    static {
        VARIANTS.put(0, 2);
        VARIANTS.put(1, 5);
        VARIANTS.put(2, 4);
    }

    public EntityLegfish(EntityType<? extends EntityLegfish> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 0.2D));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(TYPE, 0);
        dataManager.register(VARIANT, 0);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT dataTag) {
        livingdata = super.onInitialSpawn(world, difficulty, reason, livingdata, dataTag);
        int type = rand.nextInt(3);
        setType(type);
        setVariant(rand.nextInt(VARIANTS.get(type)));
        return livingdata;
    }

    public int getLegfishType() {
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
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("type", getLegfishType());
        compound.putInt("variant", getVariant());
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setType(compound.getInt("type"));
        setVariant(compound.getInt("variant"));
    }
}
