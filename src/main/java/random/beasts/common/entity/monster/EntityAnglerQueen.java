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
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
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
        this.dataManager.register(LASER_PITCH, Float.valueOf(0));
        this.dataManager.register(LASER_YAW, Float.valueOf(0));
        this.dataManager.register(PREV_LASER_PITCH, Float.valueOf(0));
        this.dataManager.register(PREV_LASER_YAW, Float.valueOf(0));
    }

    private void setLaserPitch(float laserPitch) {
        this.dataManager.set(LASER_PITCH, Float.valueOf(laserPitch));
    }

    public float getLaserPitch() {
        return this.dataManager.get(LASER_PITCH).floatValue();
    }

    private void setLaserYaw(float laserYaw) {
        this.dataManager.set(LASER_YAW, Float.valueOf(laserYaw));
    }

    public float getLaserYaw() {
        return this.dataManager.get(LASER_YAW).floatValue();
    }
    
    private void setPrevLaserPitch(float prevLaserPitch) {
        this.dataManager.set(PREV_LASER_PITCH, Float.valueOf(prevLaserPitch));
    }

    public float getPrevLaserPitch() {
        return this.dataManager.get(PREV_LASER_PITCH).floatValue();
    }

    private void setPrevLaserYaw(float prevLaserYaw) {
        this.dataManager.set(PREV_LASER_YAW, Float.valueOf(prevLaserYaw));
    }

    public float getPrevLaserYaw() {
        return this.dataManager.get(PREV_LASER_YAW).floatValue();
    }
    
    private void setUsingBeam(boolean isUsingBeam) {
        this.dataManager.set(USING_BEAM, Boolean.valueOf(isUsingBeam));
    }

    public boolean isUsingBeam() {
        return this.dataManager.get(USING_BEAM).booleanValue();
    }
    
    private void setChargingBeam(boolean isChargingBeam) {
        this.dataManager.set(CHARGING_BEAM, Boolean.valueOf(isChargingBeam));
    }

    public boolean isChargingBeam() {
        return this.dataManager.get(CHARGING_BEAM).booleanValue();
    }
    
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);

    }
    
    static class AIBeamAttack extends EntityAIBase {
    	
        private final EntityAnglerQueen guardian;
        private int tickCounter;
        private int cooldown;
        private int attackTick = 300;

        public AIBeamAttack(EntityAnglerQueen guardian) {
            this.guardian = guardian;
            this.setMutexBits(3);
        }

        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive() && guardian.ticksExisted > cooldown;
        }

        public boolean shouldContinueExecuting() {
        	if(tickCounter > this.attackTick || !super.shouldContinueExecuting()) {
        		cooldown = guardian.ticksExisted + 1200;
                this.guardian.setChargingBeam(false);
        		this.guardian.setUsingBeam(false);
        		return false;
        	}
            return super.shouldContinueExecuting();
        }

        public void startExecuting() {
            this.tickCounter = -45;
            this.guardian.setChargingBeam(true);
            this.guardian.playSound(BeastsSounds.ANGLER_QUEEN_BEAM_CHARGE, 1f, 1f);
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookHelper().setLookPositionWithEntity(this.guardian.getAttackTarget(), 90.0F, 90.0F);
            this.guardian.isAirBorne = true;
        }
        
        public static boolean canSeeEntity(Vec3d observer, Entity subject)
        {
    		if(observer == null || subject == null)
    			return false;
    		
    		AxisAlignedBB axisalignedbb = subject.getEntityBoundingBox().grow(0.30000001192092896D);
    		Vec3d observerLocation = observer;
    		Vec3d subjectLocation = new Vec3d(subject.posX, subject.posY + subject.getEyeHeight(), subject.posZ);
            RayTraceResult traceToBlocks = subject.world.rayTraceBlocks(observerLocation, subjectLocation, false, true, false);
            if (traceToBlocks != null)
            {
            	subjectLocation = traceToBlocks.hitVec;
            }
            RayTraceResult traceToEntity = axisalignedbb.calculateIntercept(observerLocation, subjectLocation);
            return traceToEntity != null;
        }
        
        private float updateRotation(float currentAngle, float targetAngle, float maxChange, float jitterAmount) {
            float change = MathHelper.wrapDegrees(targetAngle - currentAngle);
            return MathHelper.wrapDegrees(currentAngle + (float)guardian.rand.nextDouble()*jitterAmount*2 - jitterAmount + change/maxChange);
        }
        
        private void updateLaser() {
        	if(this.guardian.world!=null) {
        		if(guardian.getAttackTarget()!=null) {
        			guardian.setPrevLaserPitch(guardian.getLaserPitch());
        			guardian.setPrevLaserYaw(guardian.getLaserYaw());
            		double targetX = guardian.getAttackTarget().posX;
            		double targetY = guardian.getAttackTarget().posY + guardian.getAttackTarget().height/2d;
            		double targetZ = guardian.getAttackTarget().posZ;
            		double rot = guardian.renderYawOffset * 0.01745329238474369D + (Math.PI / 2D);
                    double lureX = Math.cos(rot) * (double)(guardian.width+1f) + guardian.posX;
                    double lureY = guardian.height + 1f + guardian.posY;
                    double lureZ = Math.sin(rot) * (double)(guardian.width+1f) + guardian.posZ;
        			Vec3d lureVec = new Vec3d(lureX, lureY, lureZ);
                    
                    double d0 = targetX - lureX;
                    double d1 = targetY - lureY;
                    double d2 = targetZ - lureZ;
                    double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                    float targetYaw = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                    float targetPitch = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
                    guardian.setLaserPitch(this.updateRotation(guardian.getLaserPitch(), targetPitch, 35f - this.guardian.world.getDifficulty().getDifficultyId()*2f, 0.75f));
                    guardian.setLaserYaw(this.updateRotation(guardian.getLaserYaw(), targetYaw, 20f - this.guardian.world.getDifficulty().getDifficultyId()*2f, 0.75f));
                    
                    Vec3d laserAngle = Vec3d.fromPitchYaw(guardian.getLaserPitch(), guardian.getLaserYaw());
                    double range = 30d;
        			Vec3d hitVec = lureVec.add(laserAngle.scale(range));
                    
        			RayTraceResult trace = guardian.world.rayTraceBlocks(lureVec, hitVec);
        			if(trace != null && trace.hitVec != null) 
        				hitVec = trace.hitVec;

                    float f = 1.0F;
                    if(this.guardian.world.getDifficulty() == EnumDifficulty.HARD) {
                        f += 2.0F;
                    }
                    
        			EntityLivingBase base = null;
        			for(EntityLivingBase entity : guardian.world.getEntitiesWithinAABB(EntityLivingBase.class, guardian.getEntityBoundingBox().grow(30))) {
        				AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(0.30000001192092896D);
        		        RayTraceResult traceToEntity = axisalignedbb.calculateIntercept(lureVec, hitVec);
        		        if(traceToEntity!=null && canSeeEntity(lureVec, entity) && entity != guardian && (base == null || guardian.getDistance(entity) < guardian.getDistance(base))) 
        		        	base = entity;
        			}
                    
                    if(base != null) { 
                    	base.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.guardian, this.guardian), f);
                    	base.attackEntityFrom(DamageSource.causeMobDamage(this.guardian), (float)this.guardian.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    }
                    
                    if(this.guardian.ticksExisted%15==0)
                    	this.guardian.playSound(BeastsSounds.ANGLER_QUEEN_BEAM, 1f, 1f);
            	}
        	}
        }

        public void updateTask() {
            EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);

            if(this.tickCounter == 0) {
                this.guardian.setChargingBeam(false);
                this.guardian.setUsingBeam(true);
                this.guardian.world.setEntityState(this.guardian, (byte)21);
            }
            else if(this.tickCounter < attackTick) {
                this.updateLaser();
            }
            
            ++this.tickCounter;
            super.updateTask();
        }
    }
    
    static class AIMinionAttack extends EntityAIBase {
    	
        private final EntityAnglerQueen guardian;
        private int tickCounter;
        private int cooldown;
        private int attackTick = 60;

        public AIMinionAttack(EntityAnglerQueen guardian) {
            this.guardian = guardian;
            this.setMutexBits(3);
        }

        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive() && guardian.ticksExisted > cooldown;
        }

        public boolean shouldContinueExecuting() {
        	if(tickCounter > this.attackTick) {
        		cooldown = guardian.ticksExisted + 600;
        		return false;
        	}
            return super.shouldContinueExecuting() && this.guardian.getDistanceSq(this.guardian.getAttackTarget()) > 4.0D;
        }

        public void startExecuting() {
            this.tickCounter = 0;
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookHelper().setLookPositionWithEntity(this.guardian.getAttackTarget(), 90.0F, 90.0F);
            this.guardian.isAirBorne = true;
        }

        public void updateTask() {
            EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
            ++tickCounter;
            if(tickCounter == this.attackTick) {
            	for(int i = 0; i < 3; ++i) {
                	BlockPos blockpos = (new BlockPos(guardian)).add(-2 + guardian.getRNG().nextInt(5), 1, -2 + guardian.getRNG().nextInt(5));
                    while(!guardian.getEntityWorld().isAirBlock(blockpos))
                    	blockpos = (new BlockPos(guardian)).add(-2 + guardian.getRNG().nextInt(5), 1, -2 + guardian.getRNG().nextInt(5));
                    EntityAnglerPup entity = new EntityAnglerPup(guardian.world);
                    entity.setPosition(blockpos.getX()+0.5d, blockpos.getY(), blockpos.getZ()+0.5d);
                    entity.setAttackTarget(guardian.getAttackTarget());
                    if(!guardian.world.isRemote) guardian.world.spawnEntity(entity);
            	}
            }
        }
    }

}
