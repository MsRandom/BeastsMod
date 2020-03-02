package random.beasts.common.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityButterflyFish extends AnimalEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityButterflyFish.class, DataSerializers.VARINT);
    private BlockPos spawnPosition;

    public EntityButterflyFish(EntityType<? extends EntityButterflyFish> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT dataTag) {
        this.setVariant(this.getRNG().nextInt(4));
        return super.onInitialSpawn(world, difficulty, reason, livingdata, dataTag);
    }

    protected void registerData() {
        super.registerData();
		this.dataManager.register(VARIANT, 0);
	}

	public boolean canBePushed() {
		return false;
	}

	protected void collideWithEntity(Entity entityIn) {
	}

	protected void collideWithNearbyEntities() {
	}

	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    public void setVariant(int nm) {
        this.dataManager.set(VARIANT, nm);
    }

    public void tick() {
        super.tick();
        this.setMotion(getMotion().mul(0, 0.6, 0));
    }

    /*@Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        super.dropFewItems(wasRecentlyHit, lootingModifier);
        this.entityDropItem(new ItemStack(BeastsItems.BUTTERFLYFISH_WING, 1), 0);
    }*/

    protected void updateAITasks() {
        super.updateAITasks();

        if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
            this.spawnPosition = null;
        }

        if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int) this.posX, (int) this.posY, (int) this.posZ, false) < 4.0D) {
            this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
                    (int) this.posY + this.rand.nextInt(6) - 2,
                    (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
        }

        double d0 = (double) this.spawnPosition.getX() + 0.5D - this.posX;
        double d1 = (double) this.spawnPosition.getY() + 0.1D - this.posY;
        double d2 = (double) this.spawnPosition.getZ() + 0.5D - this.posZ;
        this.setMotion(getMotion().add((Math.signum(d0) * 0.5 - this.getMotion().x) * 0.1, (Math.signum(d1) * 0.7 - this.getMotion().y) * 0.1, (Math.signum(d2) * 0.5 - this.getMotion().z) * 0.1));
        float f = (float) (MathHelper.atan2(this.getMotion().z, this.getMotion().x) * (180D / Math.PI)) - 90.0F;
        float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += f1;

        if (this.handleFluidAcceleration(FluidTags.WATER)) {
            setMotion(getMotion().x, 0.1, getMotion().z);
        }
    }

	protected boolean canTriggerWalking() {
		return false;
	}

	public void fall(float distance, float damageMultiplier) {
	}

	protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(VARIANT, compound.getInt("Variant"));
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", this.dataManager.get(VARIANT));
    }

    public float getEyeHeight(Pose pose) {
        return this.getHeight() / 2.0F;
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }
}