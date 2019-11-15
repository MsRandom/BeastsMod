package random.beasts.common.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;

public class EntityLegfish extends EntityAnimal {
    public static final HashMap<Integer, Integer> VARIANTS = new HashMap<>();
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityAnemoneCrawler.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityAnemoneCrawler.class, DataSerializers.VARINT);

    public EntityLegfish(World worldIn) {
        super(worldIn);
        setSize(0.5f, 0.5f);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAIWander(this, 1.1D));
        this.tasks.addTask(1, new EntityAILookIdle(this));
    }

    static {
        VARIANTS.put(0, 2);
        VARIANTS.put(1, 5);
        VARIANTS.put(2, 4);
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
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int type = rand.nextInt(3);
        setType(type);
        setVariant(rand.nextInt(VARIANTS.get(type)));
        return livingdata;
    }

    public int getType() {
        return dataManager.get(TYPE);
    }

    public int getVariant() {
        return dataManager.get(VARIANT);
    }

    private void setVariant(int value) {
        dataManager.set(VARIANT, value);
    }

    private void setType(int value) {
        dataManager.set(TYPE, value);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("type", getType());
        compound.setInteger("variant", getVariant());
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setType(compound.getInteger("type"));
        setVariant(compound.getInteger("variant"));
    }
}
