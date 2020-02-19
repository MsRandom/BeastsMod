package random.beasts.common.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
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

    public EntityAnglerQueen(World worldIn) {
        super(worldIn);
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(3, new EntityAnglerQueen.AIStompAttack(this));
        this.tasks.addTask(4, new EntityAnglerQueen.AIMinionAttack(this));
        this.tasks.addTask(5, new EntityAnglerQueen.AIBeamAttack(this));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(9, new EntityAIWander(this, 1.05d));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }
	
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CHARGING_BEAM, false);
        this.dataManager.register(USING_BEAM, false);
        this.dataManager.register(LASER_PITCH, (float) 0);
        this.dataManager.register(LASER_YAW, (float) 0);
        this.dataManager.register(PREV_LASER_PITCH, (float) 0);
        this.dataManager.register(PREV_LASER_YAW, (float) 0);
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

    static class AIBeamAttack extends EntityAIBase {
        private final EntityAnglerQueen queen;
        private EntityLivingBase target;
        private int tickCounter;
        private int cooldown;
        private int attackTick = 300;

        public AIBeamAttack(EntityAnglerQueen queen) {
            this.queen = queen;
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
            return super.shouldContinueExecuting();
        }

        public void startExecuting() {
            this.tickCounter = -45;
            this.queen.setChargingBeam(true);
            this.queen.playSound(BeastsSounds.ANGLER_QUEEN_BEAM_CHARGE, 1f, 1f);
            this.queen.getNavigator().clearPath();
            this.queen.getLookHelper().setLookPositionWithEntity(target, 90.0F, 90.0F);
            this.queen.isAirBorne = true;
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
                    double lureY = queen.height + 1f + queen.posY;
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
                //this entity state update tries to play a guardian sound, so i removed it
                //this.queen.world.setEntityState(this.queen, (byte)21);
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
            this.setMutexBits(3);
        }

        public boolean shouldExecute() {
            target = this.queen.getAttackTarget();
            return target != null && target.isEntityAlive() && queen.ticksExisted > cooldown;
        }

        public boolean shouldContinueExecuting() {
            if (tickCounter > this.attackTick) {
                cooldown = queen.ticksExisted + 600;
                return false;
            }
            return super.shouldContinueExecuting() && this.queen.getDistanceSq(target) > 4.0D;
        }

        public void startExecuting() {
            this.tickCounter = 0;
            this.queen.getNavigator().clearPath();
            this.queen.getLookHelper().setLookPositionWithEntity(target, 90.0F, 90.0F);
            this.queen.isAirBorne = true;
        }

        public void updateTask() {
            ++tickCounter;
            if(tickCounter == this.attackTick) {
            	for(int i = 0; i < 3; ++i) {
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
}
