package random.beasts.common.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBiomes;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;
import java.util.Optional;

public class EntityFireflySquid extends EntityAnimal implements EntityFlying {

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityFireflySquid.class, DataSerializers.VARINT);
	private BlockPos spawnPosition;
	private int stopped;

    public EntityFireflySquid(World worldIn) {
        super(worldIn);
        setSize(0.5f, 0.5f);
        setNoGravity(true);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateFlying(this, worldIn);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(rand.nextInt(4));
        return livingdata;
    }

    public int getVariant() {
        return Integer.valueOf(this.dataManager.get(VARIANT));
    }

    private void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    @Override
    protected void jump() {
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8d);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3d);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.04d);
    }

	protected void updateAITasks() {
		super.updateAITasks();

		if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
			this.spawnPosition = null;
		}

		if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((double) ((int) this.posX), (double) ((int) this.posY), (double) ((int) this.posZ)) < 4.0D) {
			this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
					(int) this.posY + this.rand.nextInt(6) - 2,
					(int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}

		double d0 = (double) this.spawnPosition.getX() + 0.5D - this.posX;
		double d1 = (double) this.spawnPosition.getY() + 0.1D - this.posY;
		double d2 = (double) this.spawnPosition.getZ() + 0.5D - this.posZ;
		double speed = 0.02d;
		this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * speed;
		this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * speed;
		this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * speed;
		float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
		float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
		this.moveForward = 0.5F;
		this.rotationYaw += f1;

		if (this.world.handleMaterialAcceleration(
				this.getEntityBoundingBox().grow(0.0D, -4.0D, 0.0D).shrink(0.001D), Material.WATER, this)) {
			this.motionY = 0.1F;
		}
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
	}

	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}
    
	public boolean canBePushed() {
		return false;
	}

	protected void collideWithEntity(Entity entityIn) {
	}

	protected void collideWithNearbyEntities() {
	}

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("variant", this.getVariant());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("variant"));
    }
    
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
    	return new EntityFireflySquid(ageable.world);
    }
}
