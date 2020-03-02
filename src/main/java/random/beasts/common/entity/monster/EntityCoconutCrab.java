package random.beasts.common.entity.monster;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import random.beasts.api.entity.IShellEntity;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.init.BeastsBlocks;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityCoconutCrab extends MonsterEntity implements IShellEntity {
    private static final DataParameter<Boolean> OUT = EntityDataManager.createKey(EntityCoconutCrab.class, DataSerializers.BOOLEAN);
    private static final EntitySize outSize = EntitySize.flexible(0.5f, 0.6f);
    private BlockPos newPos;
    private boolean hasTarget = false;
    private int ticksSinceHit = 0;

    public EntityCoconutCrab(EntityType<? extends EntityCoconutCrab> type, World worldIn) {
        super(type, worldIn);
        this.setNoAI(true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new RandomWalkingGoal(this, 0.5, 50) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, true) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(OUT, false);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.CRAB_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        playSound(BeastsSounds.CRAB_STEP, getSoundVolume(), getSoundPitch());
    }

    public boolean isOut() {
        return this.dataManager.get(OUT);
    }

    private void setOut(boolean out) {
        this.dataManager.set(OUT, out);
        this.setNoAI(!out);
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return isOut() ? outSize : super.getSize(poseIn);
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

    /*@Override
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
        if (!this.getHeldItem(Hand.MAIN_HAND).isEmpty())
            this.entityDropItem(this.getHeldItem(Hand.MAIN_HAND), 0);
    }*/

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        if (rand.nextInt(4) == 0)
            return this.getBlockPathWeight(new BlockPos(this.posX, this.getBoundingBox().minY, this.posZ)) >= 0.0F && world.getBlockState(getPosition()).canEntitySpawn(world, getPosition(), getType()) && this.world.getDifficulty() != Difficulty.PEACEFUL;
        world.setBlockState(getPosition(), BeastsBlocks.COCONUT.getDefaultState());
        return false;
    }

    @Override
    protected boolean processInteract(PlayerEntity player, Hand hand) {
        if (!isOut()) {
            setOut(true);
            if (!player.abilities.isCreativeMode) {
                setAttackTarget(player);
                attackEntityAsMob(player);
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("out", this.isOut());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setOut(compound.getBoolean("out"));
    }

    @Override
    public void tick() {
        if (isOut()) {
            if (world.getBlockState(getPosition().down()).getBlock() == Blocks.SAND && rand.nextInt(500) == 0) {
                setOut(false);
                setFire(0);
                for (int i = 0; i < 9; ++i) {
                    double d0 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
                    double d1 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
                    double d2 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
                    this.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.SAND.getDefaultState()), d0, d1, d2, this.rand.nextDouble() * 0.1, this.rand.nextDouble() * 0.1, this.rand.nextDouble() * 0.1);
                }
                return;
            }

            if (this.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
                List<ItemEntity> list = this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(8.0D));
                double d0 = Double.MAX_VALUE;
                ItemEntity item = null;

                for (ItemEntity itm : list)
                    if (itm.getItem().getItem() instanceof SwordItem) {
                        if (this.getDistanceSq(itm) < d0) {
                            item = itm;
                            d0 = this.getDistanceSq(itm);
                        }
                    }

                if (item != null && item.isAlive()) {
                    this.setHeldItem(Hand.MAIN_HAND, item.getItem());
                    item.remove();
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
                    if (!getAttackTarget().isAlive()) {
                        ticksSinceHit = 0;
                        hasTarget = false;
                        setAttackTarget(null);
                    } else ticksSinceHit = 1;
                } else if (!hasTarget || getDistance(getAttackTarget()) > 1.3) {
                    this.getLookController().setLookPositionWithEntity(getAttackTarget(), 0, 0);
                    this.navigator.tryMoveToXYZ(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1.2);
                    this.hasTarget = true;
                }
            }
        } else {
            if (this.isBurning()) this.extinguish();
            if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL) this.remove();
        }
        super.tick();
    }
}
