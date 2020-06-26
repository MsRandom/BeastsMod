package net.msrandom.beasts.common.entity.monster;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;
import net.msrandom.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class EntityIsopod extends EntityMob {
	private static final DataParameter<Boolean> IS_SPARTAPOD = EntityDataManager.createKey(EntityIsopod.class, DataSerializers.BOOLEAN);

	public EntityIsopod(World worldIn) {
		super(worldIn);
	}

	public boolean isSpartapod() {
		return this.dataManager.get(IS_SPARTAPOD);
	}

	public void setSpartapod(boolean spartapod) {
		if (spartapod) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.225D);
		}
		this.dataManager.set(IS_SPARTAPOD, spartapod);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1f, true));
		this.tasks.addTask(2, new EntityAIFollowSpartapod(this, 1, 4f, 30f));
		this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityLivingBase.class, input -> {
			boolean flag = false;
			for (EntityIsopod isopod : world.getEntitiesWithinAABB(EntityIsopod.class, getEntityBoundingBox().grow(20))) {
				if (isopod.isSpartapod()) {
					flag = true;
					break;
				}
			}
			return !isSpartapod() && !flag && !(input instanceof EntityIsopod);
		}, 5f, 1, 1.1));
		this.tasks.addTask(3, new EntityAIWander(this, 0.8f));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, true, true, input -> {
			if (input == getAttackTarget()) {
				boolean flag = false;
				for (EntityIsopod isopod : world.getEntitiesWithinAABB(EntityIsopod.class, getEntityBoundingBox().grow(20))) {
					if (isopod.isSpartapod()) {
						flag = true;
						break;
					}
				}
				return (isSpartapod() || flag) && !(input instanceof EntityIsopod);
			}
			return false;
		}));
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!this.isSpartapod() && this.getAttackTarget() != null) {
			boolean flag = false;
			for (EntityIsopod isopod : world.getEntitiesWithinAABB(EntityIsopod.class, getEntityBoundingBox().grow(20d))) {
				if (isopod.isSpartapod()) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				this.setAttackTarget(null);
				this.setRevengeTarget(null);
			}
		}
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		super.dropFewItems(wasRecentlyHit, lootingModifier);
		int chitin = 1;
		for (int i = 0; i <= lootingModifier; ++i) {
			chitin += rand.nextInt(2);
		}
		if (this.isSpartapod()) {
			this.entityDropItem(new ItemStack(BeastsItems.SPARTAPOD_CHITIN, chitin), 0);
			if (rand.nextInt(20) == 0)
				this.entityDropItem(new ItemStack(BeastsItems.SPARTAPOD_CREST, 1), 0);
		} else
			this.entityDropItem(new ItemStack(BeastsItems.ATHAPOD_CHITIN, chitin), 0);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("spartapod", this.isSpartapod());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setSpartapod(compound.getBoolean("spartapod"));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(IS_SPARTAPOD, rand.nextInt(5) == 0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15d);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	public void setRevengeTarget(@Nullable EntityLivingBase livingBase) {
		super.setRevengeTarget(livingBase);
		for (EntityIsopod isopod : world.getEntitiesWithinAABB(EntityIsopod.class, getEntityBoundingBox().grow(20))) {
			isopod.setAttackTarget(livingBase);
		}
	}

	public static class EntityAIFollowSpartapod extends EntityAIBase {
		private final EntityIsopod entity;
		private final Predicate<EntityIsopod> followPredicate;
		private final double speedModifier;
		private final PathNavigate navigation;
		private final float stopDistance;
		private final float areaSize;
		private EntityLiving followingEntity;
		private int timeToRecalcPath;
		private float oldWaterCost;

		public EntityAIFollowSpartapod(final EntityIsopod entity, double speed, float distance, float area) {
			this.entity = entity;
			this.followPredicate = isopod -> isopod != null && isopod.isSpartapod();
			this.speedModifier = speed;
			this.navigation = entity.getNavigator();
			this.stopDistance = distance;
			this.areaSize = area;
			this.setMutexBits(3);

			if (!(entity.getNavigator() instanceof PathNavigateGround) && !(entity.getNavigator() instanceof PathNavigateFlying)) {
				throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
			}
		}

		public boolean shouldExecute() {
			if (entity.isSpartapod())
				return false;

			List<EntityIsopod> list = this.entity.world.getEntitiesWithinAABB(EntityIsopod.class, this.entity.getEntityBoundingBox().grow(this.areaSize), this.followPredicate::test);

			if (!list.isEmpty()) {
				for (EntityIsopod entityliving : list) {
					if (!entityliving.isInvisible()) {
						this.followingEntity = entityliving;
						return true;
					}
				}
			}

			return false;
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			return this.followingEntity != null && !this.navigation.noPath() && this.entity.getDistanceSq(this.followingEntity) > (double) (this.stopDistance * this.stopDistance);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.timeToRecalcPath = 0;
			this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);
			this.entity.setPathPriority(PathNodeType.WATER, 0.0F);
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void resetTask() {
			this.followingEntity = null;
			this.navigation.clearPath();
			this.entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {
			if (this.followingEntity != null && !this.entity.getLeashed()) {
				this.entity.getLookHelper().setLookPositionWithEntity(this.followingEntity, 10.0F, (float) this.entity.getVerticalFaceSpeed());

				if (--this.timeToRecalcPath <= 0) {
					this.timeToRecalcPath = 10;
					double d0 = this.entity.posX - this.followingEntity.posX;
					double d1 = this.entity.posY - this.followingEntity.posY;
					double d2 = this.entity.posZ - this.followingEntity.posZ;
					double d3 = d0 * d0 + d1 * d1 + d2 * d2;

					if (d3 > (double) (this.stopDistance * this.stopDistance)) {
						this.navigation.tryMoveToEntityLiving(this.followingEntity, this.speedModifier);
					} else {
						this.navigation.clearPath();
						EntityLookHelper entitylookhelper = this.followingEntity.getLookHelper();

						if (d3 <= (double) this.stopDistance || entitylookhelper.getLookPosX() == this.entity.posX && entitylookhelper.getLookPosY() == this.entity.posY && entitylookhelper.getLookPosZ() == this.entity.posZ) {
							double d4 = this.followingEntity.posX - this.entity.posX;
							double d5 = this.followingEntity.posZ - this.entity.posZ;
							this.navigation.tryMoveToXYZ(this.entity.posX - d4, this.entity.posY, this.entity.posZ - d5, this.speedModifier);
						}
					}
				}
			}
		}
	}

}
