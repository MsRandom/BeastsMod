package random.beasts.common.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class EntityScallop extends EntityMob implements EntityFlying {

    private Vec3d target;

    public EntityScallop(World worldIn) {
        super(worldIn);
        setSize(0.5f, 0.5f);
        setNoGravity(true);
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateFlying(this, worldIn);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.motionY += 0.5;
        return livingdata;
    }

    @Override
    protected void jump() {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (getAttackTarget() == null) {
            Supplier<Integer> distanceGetter = () -> rand.nextInt(41) - 20;
            double height = posY - world.getHeight((int) (posX + 0.5), (int) (posZ + 0.5));
            Runnable targetSetter = () -> target = new Vec3d(posX + distanceGetter.get(), height < 30 ? height + 30 : posY + distanceGetter.get() / 20.0, distanceGetter.get());
            if (target == null) targetSetter.run();
            this.motionX += Math.signum(target.x - posX) / 24;
            this.motionY += Math.signum(target.y - posY) / 24;
            this.motionZ += Math.signum(target.z - posZ) / 24;
            getLookHelper().setLookPosition(-target.x, -target.y, -target.z, 0, 0);
            if (getDistanceSq(target.x, target.y, target.z) < 4) target = null;
            else {
                double x = posX - prevPosX;
                double y = posY - prevPosY;
                double z = posZ - prevPosZ;
                double speed = (x * x + y * y + z * z);
                if (speed < 2) targetSetter.run();
            }

            Optional<EntityPlayer> player = world.playerEntities.stream().filter(p -> !p.capabilities.isCreativeMode && !p.isSpectator()).reduce((p1, p2) -> {
                if (getDistanceSq(p1) > getDistanceSq(p2)) return p2;
                return p1;
            });

            player.ifPresent(this::setAttackTarget);
        } else {
            if (getAttackTarget() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) getAttackTarget();
                if (player.capabilities.isCreativeMode || player.isSpectator()) {
                    setAttackTarget(null);
                    return;
                }
            }
            if (getDistanceSq(getAttackTarget()) < 2) {
                attackEntityAsMob(getAttackTarget());
                getLookHelper().setLookPosition(-posX, -(posY + getEyeHeight()), -posZ, 0, 0);
                this.motionX = -motionX;
                this.motionY = Math.abs(motionY) + 0.1;
                this.motionZ = -motionZ;
            } else {
                getLookHelper().setLookPositionWithEntity(getAttackTarget(), 0, 0);
                this.motionX += Math.signum(getAttackTarget().posX - posX) / 20;
                this.motionY += Math.signum(getAttackTarget().posY - posY) / 20;
                this.motionZ += Math.signum(getAttackTarget().posZ - posZ) / 20;
            }
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        playSound(BeastsSounds.SCALLOP_CRACK, getSoundVolume(), getSoundPitch());
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        dropItem(Item.getItemFromBlock(BeastsBlocks.SHELL_BLOCKS[rand.nextInt(BeastsBlocks.SHELL_BLOCKS.length)]), 1);
        if (rand.nextInt(10) == 0)
            dropItem(isBurning() ? BeastsItems.COOKED_SCALLOP_TONGUE : BeastsItems.SCALLOP_TONGUE, 1);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }
}
