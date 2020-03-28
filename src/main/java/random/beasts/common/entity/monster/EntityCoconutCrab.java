package random.beasts.common.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import random.beasts.api.entity.IShellEntity;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.entity.IHiding;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class EntityCoconutCrab extends EntityMob implements IShellEntity, IHiding {

    private static final DataParameter<Boolean> OUT = EntityDataManager.createKey(EntityCoconutCrab.class, DataSerializers.BOOLEAN);
    private static final float defaultHeight = 0.4f;
    private BlockPos newPos;
    private boolean hasTarget = false;
    private int ticksSinceHit = 0;

    public EntityCoconutCrab(World worldIn) {
        super(worldIn);
        this.setNoAI(true);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAIWander(this, 0.5, 50) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(OUT, false);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.CRAB_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        playSound(BeastsSounds.CRAB_STEP, getSoundVolume(), getSoundPitch());
    }

    public boolean isOut() {
        return this.dataManager.get(OUT);
    }

    public boolean isHiding() {
        return !this.isOut();
    }

    private void setOut(boolean out) {
        this.dataManager.set(OUT, out);
        setSize(width, out ? defaultHeight + 0.2f : defaultHeight);
        this.setNoAI(!out);
    }

    @Override
    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
        if (isOut()) super.knockBack(entityIn, strength, xRatio, zRatio);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean ret = super.attackEntityAsMob(entityIn);
        if (ret) playSound(BeastsSounds.CRAB_ATTACK, getSoundVolume(), getSoundPitch());
        return ret;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if ((isOut() || source == DamageSource.OUT_OF_WORLD)) {
            if (source.getImmediateSource() != null) attackEntityAsMob(source.getImmediateSource());
            return super.attackEntityFrom(source, amount);
        }
        return false;
    }

    @Override
    protected Item getDropItem() {
        return BeastsItems.COCONUT;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        Item coconut = this.getDropItem();
        Item leg = isBurning() ? BeastsItems.COOKED_CRAB_LEG : BeastsItems.CRAB_LEG;
        int i = this.rand.nextInt(2);
        if (lootingModifier > 0) i += this.rand.nextInt(lootingModifier + 1);
        for (int j = 0; j < i; ++j) this.dropItem(leg, 1);
        this.dropItem(Objects.requireNonNull(coconut), 1);
        if (!this.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
            this.entityDropItem(this.getHeldItem(EnumHand.MAIN_HAND), 0);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (rand.nextInt(4) == 0)
            return this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F && world.getBlockState(getPosition()).canEntitySpawn(this) && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
        world.setBlockState(getPosition(), BeastsBlocks.COCONUT.getDefaultState());
        return false;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!isOut()) {
            setOut(true);
            if (!player.capabilities.isCreativeMode) {
                setAttackTarget(player);
                attackEntityAsMob(player);
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("out", this.isOut());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setOut(compound.getBoolean("out"));
    }

    @Override
    public void onUpdate() {
        if (isOut()) {
            if (world.getBlockState(getPosition().down()).getBlock() == Blocks.SAND && rand.nextInt(500) == 0) {
                setOut(false);
                setFire(0);
                for (int i = 0; i < 9; ++i) {
                    double d0 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
                    double d1 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
                    double d2 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
                    this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, d0, d1, d2, this.rand.nextDouble() * 0.1, this.rand.nextDouble() * 0.1, this.rand.nextDouble() * 0.1, Block.getIdFromBlock(Blocks.SAND));
                }
                return;
            }

            if (this.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
                List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(8.0D));
                double d0 = Double.MAX_VALUE;
                EntityItem item = null;

                for (EntityItem itm : list)
                    if (itm.getItem().getItem() instanceof ItemSword) {
                        if (this.getDistanceSq(itm) < d0) {
                            item = itm;
                            d0 = this.getDistanceSq(itm);
                        }
                    }

                if (item != null && !item.isDead) {
                    this.setHeldItem(EnumHand.MAIN_HAND, item.getItem());
                    item.setDead();
                }
            }

            if (this.getAttackTarget() == null) {
                if (newPos == null) {
                    int distance = rand.nextInt(7) + 10;
                    int dir = rand.nextBoolean() ? 1 : -1;
                    newPos = new BlockPos(posX + dir * distance, posY, posZ + dir * distance);
                } else this.navigator.tryMoveToXYZ(newPos.getX(), newPos.getY(), newPos.getZ(), 1);
            } else {
                if (getDistance(getAttackTarget()) < 1.2 && ticksSinceHit == 0) {
                    attackEntityAsMob(getAttackTarget());
                    if (!getAttackTarget().isEntityAlive()) {
                        ticksSinceHit = 0;
                        hasTarget = false;
                        setAttackTarget(null);
                    } else ticksSinceHit = 1;
                } else if (!hasTarget || getDistance(getAttackTarget()) > 1.3) {
                    this.getLookHelper().setLookPositionWithEntity(getAttackTarget(), 0, 0);
                    this.navigator.tryMoveToXYZ(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1.2);
                    this.hasTarget = true;
                }
            }
        } else {
            if (this.isBurning()) this.extinguish();
            if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) this.setDead();
        }
        super.onUpdate();
    }
}
