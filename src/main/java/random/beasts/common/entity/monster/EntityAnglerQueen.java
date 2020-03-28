package random.beasts.common.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.entity.passive.EntityAnglerPup;

public class EntityAnglerQueen extends EntityMob {
    private static final DataParameter<Boolean> CHARGING_BEAM = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> USING_BEAM = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> LASER_PITCH = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> LASER_YAW = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> PREV_LASER_PITCH = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> PREV_LASER_YAW = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> IS_BITING = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BITE_TICK = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_REARING = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> REAR_TICK = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.VARINT);

    private final BossInfoServer bossInfo = (BossInfoServer) new BossInfoServer(getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);

    public EntityAnglerQueen(World worldIn) {
        super(worldIn);
        this.experienceValue = 30;
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAnglerQueen.AIStompAttack(this));
        this.tasks.addTask(3, new EntityAnglerQueen.AIMinionAttack(this));
        this.tasks.addTask(4, new EntityAnglerQueen.AIBeamAttack(this));
        this.tasks.addTask(5, new EntityAnglerQueen.EntityAIBiteAttack(this, 1.05d, true));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.tasks.addTask(8, new EntityAIWander(this, 1.05d));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
    }
	
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CHARGING_BEAM, false);
        this.dataManager.register(USING_BEAM, false);
        this.dataManager.register(LASER_PITCH, 0f);
        this.dataManager.register(LASER_YAW, 0f);
        this.dataManager.register(PREV_LASER_PITCH, 0f);
        this.dataManager.register(PREV_LASER_YAW, 0f);
        this.dataManager.register(IS_BITING, false);
        this.dataManager.register(BITE_TICK, 0);
        this.dataManager.register(IS_REARING, false);
        this.dataManager.register(REAR_TICK, 0);
    }

    public float getLaserPitch() {
        return this.dataManager.get(LASER_PITCH);
    }

    private void setLaserPitch(float laserPitch) {
        this.dataManager.set(LASER_PITCH, laserPitch);
    }

    public float getLaserYaw() {
        return this.dataManager.get(LASER_YAW);
    }

    private void setLaserYaw(float laserYaw) {
        this.dataManager.set(LASER_YAW, laserYaw);
    }

    public float getPrevLaserPitch() {
        return this.dataManager.get(PREV_LASER_PITCH);
    }

    private void setPrevLaserPitch(float prevLaserPitch) {
        this.dataManager.set(PREV_LASER_PITCH, prevLaserPitch);
    }

    public float getPrevLaserYaw() {
        return this.dataManager.get(PREV_LASER_YAW);
    }

    private void setPrevLaserYaw(float prevLaserYaw) {
        this.dataManager.set(PREV_LASER_YAW, prevLaserYaw);
    }

    public boolean isUsingBeam() {
        return this.dataManager.get(USING_BEAM);
    }

    private void setUsingBeam(boolean isUsingBeam) {
        this.dataManager.set(USING_BEAM, isUsingBeam);
    }

    public boolean isChargingBeam() {
        return this.dataManager.get(CHARGING_BEAM);
    }

    private void setChargingBeam(boolean isChargingBeam) {
        this.dataManager.set(CHARGING_BEAM, isChargingBeam);
    }

    public boolean isBiting() {
        return this.dataManager.get(IS_BITING);
    }

    private void setBiting(boolean isBiting) {
        this.dataManager.set(IS_BITING, isBiting);
    }

    public boolean isRearing() {
        return this.dataManager.get(IS_REARING);
    }

    private void setRearing(boolean isRearing) {
        this.dataManager.set(IS_REARING, isRearing);
    }

    public int getBiteTick() {
        return this.dataManager.get(BITE_TICK);
    }

    public void setBiteTick(int biteTick) {
        this.dataManager.set(BITE_TICK, biteTick);
    }

    public int getRearTick() {
        return this.dataManager.get(REAR_TICK);
    }

    public void setRearTick(int rearTick) {
        this.dataManager.set(REAR_TICK, rearTick);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (isUsingBeam() || isChargingBeam()) {
            setBiteTick(0);
            setRearTick(0);
            setBiting(false);
            setRearing(false);
        } else if (isRearing()) {
            setBiting(false);
            setBiteTick(0);
        }
        bossInfo.setPercent(getHealth() / getMaxHealth());
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    static class AIBeamAttack extends EntityAIBase {
        private final EntityAnglerQueen queen;
        private EntityLivingBase target;
        private int tickCounter;
        private int cooldown;
        private int attackTick = 300;

        public AIBeamAttack(EntityAnglerQueen queen) {
            this.queen = queen;
            cooldown = queen.ticksExisted + 600;
            this.setMutexBits(3);
        }

        public static boolean canSeeEntity(Vec3d observer, Entity subject) {
            if (observer == null || subject == null)
                return false;

            AxisAlignedBB axisalignedbb = subject.getEntityBoundingBox().grow(0.30000001192092896D);
            Vec3d subjectLocation = new Vec3d(subject.posX, subject.posY + subject.getEyeHeight(), subject.posZ);
            RayTraceResult traceToBlocks = subject.world.rayTraceBlocks(observer, subjectLocation, false, true, false);
            if (traceToBlocks != null)
                subjectLocation = traceToBlocks.hitVec;
            RayTraceResult traceToEntity = axisalignedbb.calculateIntercept(observer, subjectLocation);
            return traceToEntity != null;
        }

        public boolean shouldExecute() {
            target = this.queen.getAttackTarget();
            return target != null && target.isEntityAlive() && queen.ticksExisted > cooldown;
        }

        public boolean shouldContinueExecuting() {
            if (tickCounter > this.attackTick || !super.shouldContinueExecuting()) {
                cooldown = queen.ticksExisted + 1200;
                this.queen.setChargingBeam(false);
                this.queen.setUsingBeam(false);
                return false;
            }
            return true;
        }

        public void startExecuting() {
            this.tickCounter = -45;
            this.queen.setChargingBeam(true);
            this.queen.playSound(BeastsSounds.ANGLER_QUEEN_BEAM_CHARGE, 1f, 1f);
            this.queen.getNavigator().clearPath();
            this.queen.getLookHelper().setLookPositionWithEntity(target, 90.0F, 90.0F);
            this.queen.isAirBorne = false;
        }

        private float updateRotation(float currentAngle, float targetAngle, float maxChange) {
            float change = MathHelper.wrapDegrees(targetAngle - currentAngle);
            return MathHelper.wrapDegrees(currentAngle + queen.rand.nextFloat() * 1.5f - 0.75f + change / maxChange);
        }
        
        private void updateLaser() {
            if (this.queen.world != null) {
                if (queen.getAttackTarget() != null) {
                    queen.setPrevLaserPitch(queen.getLaserPitch());
                    queen.setPrevLaserYaw(queen.getLaserYaw());
                    double targetX = queen.getAttackTarget().posX;
                    double targetY = queen.getAttackTarget().posY + queen.getAttackTarget().height / 2d;
                    double targetZ = queen.getAttackTarget().posZ;
                    double rot = queen.renderYawOffset * 0.01745329238474369D + (Math.PI / 2D);
                    double lureX = Math.cos(rot) * (double) (queen.width + 1f) + queen.posX;
                    double lureY = 2.75f + queen.posY;
                    double lureZ = Math.sin(rot) * (double) (queen.width + 1f) + queen.posZ;
                    Vec3d lureVec = new Vec3d(lureX, lureY, lureZ);

                    double d0 = targetX - lureX;
                    double d1 = targetY - lureY;
                    double d2 = targetZ - lureZ;
                    double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                    float targetYaw = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                    float targetPitch = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
                    queen.setLaserPitch(this.updateRotation(queen.getLaserPitch(), targetPitch, 35f - this.queen.world.getDifficulty().getDifficultyId() * 2f));
                    queen.setLaserYaw(this.updateRotation(queen.getLaserYaw(), targetYaw, 20f - this.queen.world.getDifficulty().getDifficultyId() * 2f));

                    Vec3d laserAngle = Vec3d.fromPitchYaw(queen.getLaserPitch(), queen.getLaserYaw());
                    double range = 30d;
                    Vec3d hitVec = lureVec.add(laserAngle.scale(range));

                    RayTraceResult trace = queen.world.rayTraceBlocks(lureVec, hitVec);
                    if (trace != null && trace.hitVec != null)
                        hitVec = trace.hitVec;

                    float f = 1.0F;
                    if (this.queen.world.getDifficulty() == EnumDifficulty.HARD) {
                        f += 2.0F;
                    }

                    EntityLivingBase base = null;
                    for (EntityLivingBase entity : queen.world.getEntitiesWithinAABB(EntityLivingBase.class, queen.getEntityBoundingBox().grow(30))) {
                        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(0.30000001192092896D);
                        RayTraceResult traceToEntity = axisalignedbb.calculateIntercept(lureVec, hitVec);
                        if (traceToEntity != null && canSeeEntity(lureVec, entity) && entity != queen && (base == null || queen.getDistance(entity) < queen.getDistance(base)))
                            base = entity;
                    }

                    if (base != null) {
                        base.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.queen, this.queen), f);
                        base.attackEntityFrom(DamageSource.causeMobDamage(this.queen), (float) this.queen.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    }

                    if (this.queen.ticksExisted % 15 == 0)
                        this.queen.playSound(BeastsSounds.ANGLER_QUEEN_BEAM, 1f, 1f);
                }
        	}
        }

        public void updateTask() {
            this.queen.getNavigator().clearPath();
            this.queen.getLookHelper().setLookPositionWithEntity(target, 90.0F, 90.0F);

            if (this.tickCounter == 0) {
                this.queen.setChargingBeam(false);
                this.queen.setUsingBeam(true);
            } else if (this.tickCounter < attackTick) {
                this.updateLaser();
            }
            
            ++this.tickCounter;
            super.updateTask();
        }
    }

    static class AIMinionAttack extends EntityAIBase {
        private final EntityAnglerQueen queen;
        private EntityLivingBase target;
        private int tickCounter;
        private int cooldown;
        private int attackTick = 60;

        public AIMinionAttack(EntityAnglerQueen queen) {
            this.queen = queen;
            cooldown = queen.ticksExisted + 100;
            this.setMutexBits(3);
        }

        public boolean shouldExecute() {
            target = this.queen.getAttackTarget();
            return target != null && target.isEntityAlive() && queen.ticksExisted > cooldown;
        }

        public boolean shouldContinueExecuting() {
            if (tickCounter > this.attackTick) {
                cooldown = queen.ticksExisted + 600;
                queen.setRearing(false);
                return false;
            }
            return super.shouldContinueExecuting();
        }

        public void startExecuting() {
            queen.setRearing(true);
            this.tickCounter = 0;
            this.queen.getNavigator().clearPath();
            this.queen.getLookHelper().setLookPositionWithEntity(target, 90.0F, 90.0F);
            this.queen.isAirBorne = true;
        }

        public void updateTask() {
            ++tickCounter;
            if(tickCounter == this.attackTick) {
                for (int i = 0; i < 3; ++i) {
                    BlockPos blockpos = (new BlockPos(queen)).add(-2 + queen.getRNG().nextInt(5), 1, -2 + queen.getRNG().nextInt(5));
                    while (!queen.getEntityWorld().isAirBlock(blockpos))
                        blockpos = (new BlockPos(queen)).add(-2 + queen.getRNG().nextInt(5), 1, -2 + queen.getRNG().nextInt(5));
                    EntityAnglerPup entity = new EntityAnglerPup(queen.world);
                    entity.setPosition(blockpos.getX() + 0.5d, blockpos.getY(), blockpos.getZ() + 0.5d);
                    entity.setAttackTarget(queen.getAttackTarget());
                    if (!queen.world.isRemote) queen.world.spawnEntity(entity);
                }
            }
        }
    }

    static class AIStompAttack extends EntityAIBase {
        private final EntityAnglerQueen queen;
        private int tickCounter;
        private int cooldown;
        private int attackTick = 26;

        public AIStompAttack(EntityAnglerQueen queen) {
            this.queen = queen;
            cooldown = queen.ticksExisted + 200;
            this.setMutexBits(3);
        }

        public boolean shouldExecute() {
            return queen.getAttackTarget() != null && queen.getAttackTarget().isEntityAlive() && queen.ticksExisted > cooldown && this.queen.getDistance(queen.getAttackTarget()) > 3.0D && this.queen.getDistance(queen.getAttackTarget()) < 12.0D;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            queen.setRearing(false);
        }

        public boolean shouldContinueExecuting() {
            if (tickCounter > this.attackTick) {
                cooldown = queen.ticksExisted + 400;
                queen.setRearing(false);
                return false;
            }
            return super.shouldContinueExecuting();
        }

        public void startExecuting() {
            this.tickCounter = 0;
            queen.setRearing(true);
            this.queen.getNavigator().clearPath();
            this.queen.getLookHelper().setLookPositionWithEntity(queen.getAttackTarget(), 90.0F, 90.0F);
            this.queen.isAirBorne = true;
        }

        public void updateTask() {
            ++tickCounter;
            queen.setRearTick(tickCounter);
            this.queen.getNavigator().clearPath();
            if (tickCounter == this.attackTick) {
                for (EntityLivingBase base : queen.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, queen.getEntityBoundingBox().grow(12d, 0d, 12d))) {
                    if (base != queen) {
                        double x = queen.posX - base.posX;
                        double z = queen.posZ - base.posZ;
                        base.knockBack(queen, 12f / base.getDistance(queen), x, z);
                        base.attackEntityFrom(DamageSource.causeMobDamage(queen), base.getDistance(queen) / 4f);
                    }
                }
                queen.setRearTick(0);
                queen.setRearing(false);
            }
        }
    }

    static class EntityAIBiteAttack extends EntityAIBase {
        protected EntityAnglerQueen queen;
        /**
         * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
         */
        protected int attackTick;
        World world;
        /**
         * The speed with which the mob will approach the target
         */
        double speedTowardsTarget;
        /**
         * When true, the mob will continue chasing its target, even if it can't find a path to them right now.
         */
        boolean longMemory;
        /**
         * The PathEntity of our entity.
         */
        Path path;
        private int delayCounter;
        private double targetX;
        private double targetY;
        private double targetZ;

        public EntityAIBiteAttack(EntityAnglerQueen queen, double speedIn, boolean useLongMemory) {
            this.queen = queen;
            this.world = queen.world;
            this.speedTowardsTarget = speedIn;
            this.longMemory = useLongMemory;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.queen.getAttackTarget();

            if (queen.isChargingBeam() || queen.isUsingBeam() || queen.isRearing())
                return false;
            if (entitylivingbase == null) {
                return false;
            } else if (!entitylivingbase.isEntityAlive()) {
                return false;
            } else {
                this.path = this.queen.getNavigator().getPathToEntityLiving(entitylivingbase);

                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(entitylivingbase) >= this.queen.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
                }
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            EntityLivingBase entitylivingbase = this.queen.getAttackTarget();

            if (entitylivingbase == null) {
                return false;
            } else if (!entitylivingbase.isEntityAlive()) {
                return false;
            } else if (!this.longMemory) {
                return !this.queen.getNavigator().noPath();
            } else if (!this.queen.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase))) {
                return false;
            } else {
                return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).isSpectator() && !((EntityPlayer) entitylivingbase).isCreative();
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.queen.getNavigator().setPath(this.path, this.speedTowardsTarget);
            this.delayCounter = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            EntityLivingBase entitylivingbase = this.queen.getAttackTarget();

            if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer) entitylivingbase).isSpectator() || ((EntityPlayer) entitylivingbase).isCreative())) {
                this.queen.setAttackTarget(null);
            }

            this.queen.getNavigator().clearPath();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            EntityLivingBase entitylivingbase = this.queen.getAttackTarget();
            this.queen.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
            double d0 = this.queen.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            --this.delayCounter;

            if ((this.longMemory || this.queen.getEntitySenses().canSee(entitylivingbase)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.queen.getRNG().nextFloat() < 0.05F)) {
                this.targetX = entitylivingbase.posX;
                this.targetY = entitylivingbase.getEntityBoundingBox().minY;
                this.targetZ = entitylivingbase.posZ;
                this.delayCounter = 4 + this.queen.getRNG().nextInt(7);

                if (d0 > 1024.0D) {
                    this.delayCounter += 10;
                } else if (d0 > 256.0D) {
                    this.delayCounter += 5;
                }

                if (!this.queen.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget)) {
                    this.delayCounter += 15;
                }
            }

            this.attackTick = Math.max(this.attackTick - 1, 0);
            if (this.attackTick <= 17) {
                queen.setBiting(true);
                queen.setBiteTick(17 - this.attackTick);
            }
            this.checkAndPerformAttack(entitylivingbase, d0);
        }

        protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
            double d0 = this.getAttackReachSqr(p_190102_1_);

            if (this.attackTick <= 0) {
                this.attackTick = 20;
                if (p_190102_2_ <= d0)
                    this.queen.attackEntityAsMob(p_190102_1_);
                queen.setBiting(false);
                queen.setBiteTick(0);
            }
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return this.queen.width * 2.0d * this.queen.width * 2.0d + attackTarget.width;
        }
    }
}
