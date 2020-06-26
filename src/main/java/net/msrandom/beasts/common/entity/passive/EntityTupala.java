package net.msrandom.beasts.common.entity.passive;

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
import net.msrandom.beasts.common.entity.IHiding;
import net.msrandom.beasts.common.entity.monster.EntityVileEel;
import net.msrandom.beasts.common.init.BeastsBlocks;
import net.msrandom.beasts.common.init.BeastsItems;

public class EntityTupala extends EntityAnimal implements IHiding {
    private static final DataParameter<Boolean> HIDING = EntityDataManager.createKey(EntityTupala.class, DataSerializers.BOOLEAN);
    private int hidingTicks = 0;

    public EntityTupala(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity<EntityLivingBase>(this, EntityLivingBase.class, entity -> entity instanceof EntityPlayer || entity instanceof EntityVileEel, 5f, 1d, 1.1d) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && !isHiding();
            }

            @Override
            public boolean shouldContinueExecuting() {
                return super.shouldContinueExecuting() && !isHiding();
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
        if (!world.isRemote) {
            if (hidingTicks > 100) setDead();

            boolean safe = world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().grow(5), entity -> entity instanceof EntityPlayer || entity instanceof EntityVileEel).isEmpty();
            if (this.isHiding()) {
                ++hidingTicks;
                navigator.clearPath();
                if ((safe && rand.nextInt(80) == 0) || world.getBlockState(getPosition()).getBlock() != BeastsBlocks.ABYSSAL_STONE) {
                    this.setHiding(false);
                    setPositionAndUpdate(posX, posY + 1, posZ);
                }
            } else {
                if (!safe && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == BeastsBlocks.ABYSSAL_STONE && rand.nextInt(20) == 0 && !isHiding()) {
                    setHiding(true);
                    setPositionAndUpdate(posX, posY - 1, posZ);
                    navigator.clearPath();
                }
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
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
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
