package rando.beasts.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rando.beasts.client.init.BeastsSounds;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

//TODO currently disabled.
public class EntityMonkeyGremlin extends EntityTameable {

	/**
	 * This is done to use {@link java.util.Optional} instead of {@link com.google.common.base.Optional} as per JDK 8 standard
	 */
	private static final DataSerializer<Optional<UUID>> OPTIONAL_UNIQUE_ID = new DataSerializer<Optional<UUID>>() {
		public void write(PacketBuffer buf, Optional<UUID> value)
		{
			buf.writeBoolean(value.isPresent());
			value.ifPresent(buf::writeUniqueId);
		}
		@Nonnull
		public Optional<UUID> read(PacketBuffer buf) {
			return buf.readBoolean() ? Optional.of(buf.readUniqueId()) : Optional.empty();
		}
		public DataParameter<Optional<UUID>> createKey(int id)
		{
			return new DataParameter<>(id, this);
		}
		@Nonnull
		public Optional<UUID> copyValue(@Nonnull Optional<UUID> value) {
			return value;
		}
	};

	private static final DataParameter<Boolean> CLIMBING = EntityDataManager.createKey(EntityMonkeyGremlin.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> RIDING = EntityDataManager.createKey(EntityMonkeyGremlin.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Optional<UUID>> PLAYER = EntityDataManager.createKey(EntityMonkeyGremlin.class, OPTIONAL_UNIQUE_ID);
	private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityMonkeyGremlin.class, DataSerializers.ITEM_STACK);
	private int climbTimeWithoutValid = 0;
	private int rideTime;

	public EntityMonkeyGremlin(World worldIn) {
		super(worldIn);
		this.setTamed(false);
		this.setSize(0.6F, 0.85F);
	}

    protected void initEntityAI() {
		this.aiSit = new EntityAISit(this);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(10, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}

    @Override
    protected SoundEvent getAmbientSound() {
    	return BeastsSounds.MONKEY_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    	return BeastsSounds.MONKEY_HURT;
    }

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ITEM, ItemStack.EMPTY);
		this.dataManager.register(CLIMBING, false);
		this.dataManager.register(RIDING, false);
		this.dataManager.register(PLAYER, Optional.empty());
	}

	public void onUpdate() {
	    if(!this.world.isRemote) {
	        boolean valid = false;
			if(getRiding() && (getPlayer() == null || world.getPlayerEntityByUUID(getPlayer()) == null)){
				this.setPlayer(null);
				this.setRiding(false);
			}
	        for (EnumFacing facing : EnumFacing.values()) {
	            BlockPos pos = this.getPosition().offset(facing);
	            Block block = this.world.getBlockState(pos).getBlock();
	            if(isValidBlock(block)) {
	                valid = true;
	            }
	        }
	        this.setBesideClimbableBlock((this.collidedHorizontally && valid) || (this.collidedHorizontally && this.climbTimeWithoutValid < 15));
            if (this.collidedHorizontally && !valid) {
                this.climbTimeWithoutValid++;
            } else if (this.climbTimeWithoutValid > 0 || (this.collidedHorizontally)) {
                this.climbTimeWithoutValid = 0;
            }
	    }
		super.onUpdate();
	}

	private boolean isValidBlock(Block b) {
		return b instanceof BlockLog || b instanceof BlockLeaves || b instanceof BlockPlanks;
	}

	private boolean isBesideClimbableBlock() {
	    return this.dataManager.get(CLIMBING);
	}

	private void setBesideClimbableBlock(boolean climbing) {
	    this.dataManager.set(CLIMBING, climbing);
	}

	private boolean getRiding() {
		return this.dataManager.get(RIDING);
	}

	private void setRiding(boolean riding) {
		this.dataManager.set(RIDING, riding);
	}

	@Nullable
	private UUID getPlayer() {
		return this.dataManager.get(PLAYER).orElse(null);
	}

	private void setPlayer(@Nullable UUID id) {
		this.dataManager.set(PLAYER, Optional.ofNullable(id));
	}

	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}

	@Override
	public void fall(float distance, float damageMultiplier){}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.31D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.isTamed() ? 20.0D : 10.0D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}

	public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
		super.setAttackTarget(entitylivingbaseIn);

		if (entitylivingbaseIn == null) {
			this.setAngry(false);
		} else if (!this.isTamed()) {
			this.setAngry(true);
		}
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("Angry", this.isAngry());
		compound.setTag("Stolen", Objects.requireNonNull(this.getStolenItem().getTagCompound()));
		compound.setBoolean("riding", this.getRiding());
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setAngry(compound.getBoolean("Angry"));
		this.setRiding(compound.getBoolean("riding"));
	}

	private void setStolenItem(ItemStack stack) {
		this.dataManager.set(ITEM, stack);
	}

	public ItemStack getStolenItem() {
		ItemStack item = dataManager.get(ITEM);
		if(item.getTagCompound() == null){
			item.setTagCompound(new NBTTagCompound());
		}
		return item;
	}

	private void sitOnPlayerShoulder(EntityPlayer player){
		if(!player.getEntityData().getBoolean("shoulderMob")) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("entityId", Objects.requireNonNull(this.getEntityString()));
			this.writeToNBT(compound);
			this.world.removeEntity(this);
			this.world.onEntityRemoved(this);
			Entity entity = EntityList.createEntityFromNBT(compound, world);
			if (entity instanceof EntityMonkeyGremlin) {
				EntityMonkeyGremlin monkey = (EntityMonkeyGremlin) entity;
				player.getEntityData().setBoolean("shoulderMob", true);
				monkey.setOwnerId(this.getOwnerId());
				monkey.setPlayer(player.getUniqueID());
				monkey.setRiding(true);
				monkey.setPosition(this.posX, this.posY + 0.699999988079071D, this.posZ);
				this.world.spawnEntity(monkey);
			}
		}
	}

	private void dismountFromPlayer(){
		if(getRiding() && getPlayer() != null) {
			EntityPlayer player = world.getPlayerEntityByUUID(getPlayer());
			if(player != null) {
				this.setPlayer(null);
				this.setRiding(false);
				this.setSize(0.6F, 0.85F);
				player.getEntityData().setBoolean("shoulderMob", false);
			}
		}
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();

		if(!this.world.isRemote) {
			if(getRiding() && getPlayer() != null) {
				EntityPlayer entity = world.getPlayerEntityByUUID(getPlayer());
				if(entity != null) {
					if (entity.isDead) {
						rideTime = 0;
						this.dismountFromPlayer();
					} else {
						this.motionX = 0.0D;
						this.motionY = 0.0D;
						this.motionZ = 0.0D;
						this.rotationYaw = entity.rotationYawHead;
						this.rotationYawHead = entity.rotationYawHead;
						this.prevRotationYaw = entity.rotationYawHead;
						this.setPosition(entity.posX + 0.5F * 1, entity.posY + 1.4D, entity.posZ + 0.5F);
						rideTime++;
						if (rideTime > 50 && entity.onGround && entity.isSneaking()) {
							rideTime = 0;
							this.dismountFromPlayer();
						}
					}
				}
			}
			if (this.getAttackTarget() == null && this.isAngry()) {
				this.setAngry(false);
			}
			if (!this.isTamed() && this.getStolenItem() == ItemStack.EMPTY && getRNG().nextFloat() < 0.3F) {
				List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(2));
				if (!list.isEmpty()) {
					EntityPlayer player = list.get(list.size() == 1 ? 0 : getRNG().nextInt(list.size()));
					int i = this.getRNG().nextInt(player.inventory.getSizeInventory());
					if (player.inventory.getStackInSlot(i) != ItemStack.EMPTY) {
						this.setStolenItem(player.inventory.getStackInSlot(i));
						ItemStack a = player.inventory.getStackInSlot(i);
						a.shrink(1);
						player.inventory.setInventorySlotContents(i, a);
					}
				}
			}
		}
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		super.dropFewItems(wasRecentlyHit, lootingModifier);

		if(this.getStolenItem() != ItemStack.EMPTY)
			if(!this.world.isRemote)
				this.entityDropItem(this.getStolenItem(), 1);
	}

	public float getEyeHeight() {
		return this.height * 0.8F;
	}

	public int getVerticalFaceSpeed() {
		return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
	}

	public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			Entity entity = source.getTrueSource();

			if (this.aiSit != null) {
				this.aiSit.setSitting(false);
			}

			if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
				amount = (amount + 1.0F) / 2.0F;
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this),
				(float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
	}

	public void setTamed(boolean tamed) {
		super.setTamed(tamed);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(tamed ? 20.0D : 10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}

	@Override
	public void updateRidden() {
		super.updateRidden();
	}

	public boolean processInteract(EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (this.isTamed()) {
			if(this.isOwner(player)) {
				if(!this.world.isRemote && player.isSneaking() && !getRiding()) {
					sitOnPlayerShoulder(player);
					return true;
				}
			}
			if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.navigator.clearPath();
				this.setAttackTarget(null);
			}
		} else if ((itemstack.getItem() == Items.APPLE || itemstack.getItem() == Items.MELON) && !this.isAngry()) {
			if (!player.capabilities.isCreativeMode) {
				itemstack.shrink(1);
			}

			if (!this.world.isRemote) {
				if (this.rand.nextInt(3) == 0) {
					this.setTamedBy(player);
					this.navigator.clearPath();
					this.setAttackTarget(null);
					this.aiSit.setSitting(true);
					this.setHealth(20.0F);
					this.playTameEffect(true);
					this.world.setEntityState(this, (byte) 7);
				} else {
					this.playTameEffect(false);
					this.world.setEntityState(this, (byte) 6);
				}
			}

			return true;
		}

		return super.processInteract(player, hand);
	}

	private boolean isAngry() {
		return (this.dataManager.get(TAMED) & 2) != 0;
	}

	private void setAngry(boolean angry) {
		byte b0 = this.dataManager.get(TAMED);
		this.dataManager.set(TAMED, angry ? (byte) (b0 | 2) : (byte) (b0 & -3));
	}

	public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner) {
		if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast)) {
			if (target instanceof EntityMonkeyGremlin) {
				EntityMonkeyGremlin monkey = (EntityMonkeyGremlin) target;

				if (monkey.isTamed() && monkey.getOwner() == owner) {
					return false;
				}
			}

			if (target instanceof EntityPlayer && owner instanceof EntityPlayer
					&& !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target)) {
				return false;
			} else {
				return !(target instanceof AbstractHorse) || !((AbstractHorse) target).isTame();
			}
		} else {
			return false;
		}
	}

	public boolean canBeLeashedTo(EntityPlayer player) {
		return !this.isAngry() && super.canBeLeashedTo(player);
	}

	@Override
	public EntityAgeable createChild(@Nonnull EntityAgeable ageable) {
		return null;
	}

	static {
		DataSerializers.registerSerializer(OPTIONAL_UNIQUE_ID);
	}
}