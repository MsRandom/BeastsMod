package random.beasts.common.entity.passive;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.common.entity.IHiding;
import random.beasts.common.entity.monster.EntityVileEel;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;

public class EntityTupala extends EntityAnimal implements IHiding {

    private static final DataParameter<Boolean> HIDING = EntityDataManager.createKey(EntityTupala.class, DataSerializers.BOOLEAN);

    public EntityTupala(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity<EntityLivingBase>(this, EntityLivingBase.class, new Predicate<EntityLivingBase>() {
            @Override
            public boolean apply(@Nullable EntityLivingBase input) {
                return input instanceof EntityPlayer || input instanceof EntityVileEel;
            }
        }, 5f, 1d, 1.1d) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && !isHiding();
            }

            @Override
            public boolean shouldContinueExecuting() {
                return super.shouldContinueExecuting() && !isHiding();
            }

            @Override
            public void updateTask() {
                if (world.getBlockState(getPosition().down()).getBlock() == BeastsBlocks.ABYSSAL_STONE && rand.nextInt(20) == 0 && !isHiding()) {
                    setHiding(true);
                    setPositionAndUpdate(posX, posY - 1, posZ);
                    navigator.clearPath();
                }
                super.updateTask();
            }
        });
        this.tasks.addTask(2, new EntityAIWander(this, 1d, 20) {
            @Override
            public boolean shouldExecute() {
                return !isHiding() && super.shouldExecute();
            }
        });
        this.tasks.addTask(3, new EntityAIMoveToBlock(this, 1d, 10) {
            @Override
            protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
                return !isHiding() && world.getBlockState(pos).getBlock() == BeastsBlocks.ABYSSAL_STONE;
            }
        });
        this.tasks.addTask(4, new EntityAILookIdle(this));
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return this.isHiding() && world.getBlockState(getPosition()).getBlock() == BeastsBlocks.ABYSSAL_STONE || super.getIsInvulnerable();
    }

    public boolean isHiding() {
        return this.dataManager.get(HIDING);
    }

    public void setHiding(boolean hiding) {
        this.dataManager.set(HIDING, hiding);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HIDING, false);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isHiding()) {
            navigator.clearPath();
            if ((world.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().grow(5d)).isEmpty() && rand.nextInt(80) == 0 &&
                    world.getEntitiesWithinAABB(EntityVileEel.class, getEntityBoundingBox().grow(5d)).isEmpty()) ||
                    world.getBlockState(getPosition()).getBlock() != BeastsBlocks.ABYSSAL_STONE) {
                this.setHiding(false);
                setPositionAndUpdate(posX, posY + 1, posZ);
            }
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("hiding", this.isHiding());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setHiding(compound.getBoolean("hiding"));
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        super.dropFewItems(wasRecentlyHit, lootingModifier);
        int stone = 1;
        int seeds = 1;
        for (int i = 0; i <= lootingModifier; ++i) {
            stone += rand.nextInt(2);
            seeds += rand.nextInt(2);
        }
        this.entityDropItem(new ItemStack(BeastsBlocks.ABYSSAL_STONE, stone), 0);
        this.entityDropItem(new ItemStack(BeastsItems.TUBEWORM_SEEDS, seeds), 0);
    }
}
