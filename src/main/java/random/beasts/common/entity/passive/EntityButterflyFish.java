package random.beasts.common.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IMobEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import random.beasts.common.init.BeastsItems;

public class EntityButterflyFish extends EntityAnimal {
	private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityButterflyFish.class, DataSerializers.VARINT);
	private BlockPos spawnPosition;

	public EntityButterflyFish(World worldIn) {
		super(worldIn);
	}

    @Override
    public IMobEntityData onInitialSpawn(DifficultyInstance difficulty, IMobEntityData livingdata) {
        this.setVariant(this.getRNG().nextInt(4));
        return super.onInitialSpawn(difficulty, livingdata);
    }

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(VARIANT, 0);
	}

	public boolean canBePushed() {
		return false;
	}

	protected void collideWithEntity(Entity entityIn) {
	}

	protected void collideWithNearbyEntities() {
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
	}

	public int getVariant() {
		return this.dataManager.get(VARIANT);
	}

	public void setVariant(int nm) {
		this.dataManager.set(VARIANT, nm);
	}

	public void onUpdate() {
		super.onUpdate();
		this.motionY *= 0.6000000238418579D;
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		super.dropFewItems(wasRecentlyHit, lootingModifier);
		this.entityDropItem(new ItemStack(BeastsItems.BUTTERFLYFISH_WING, 1), 0);
	}

	protected void updateAITasks() {
		super.updateAITasks();

		if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
			this.spawnPosition = null;
		}

		if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0D) {
			this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
					(int) this.posY + this.rand.nextInt(6) - 2,
					(int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}

		double d0 = (double) this.spawnPosition.getX() + 0.5D - this.posX;
		double d1 = (double) this.spawnPosition.getY() + 0.1D - this.posY;
		double d2 = (double) this.spawnPosition.getZ() + 0.5D - this.posZ;
		this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
		this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
		this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
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

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public void readEntityFromNBT(CompoundNBT compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(VARIANT, compound.getInt("Variant"));
    }

    public void writeEntityToNBT(CompoundNBT compound) {
        super.writeEntityToNBT(compound);
        compound.putInt("Variant", this.dataManager.get(VARIANT));
    }

    public float getEyeHeight() {
        return this.height / 2.0F;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}