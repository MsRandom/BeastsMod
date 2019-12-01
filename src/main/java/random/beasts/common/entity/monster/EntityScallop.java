package random.beasts.common.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import java.util.Optional;
import java.util.function.Consumer;

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

    @Override
    protected void jump() {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (getAttackTarget() == null) {
            Consumer<Vec3d> targetSetter = vec -> {
                double x = posX;
                double y = posY;
                double z = posZ;
                if (vec != null) {
                    x = (x + vec.x) / 2;
                    y = (y + vec.y) / 2;
                    z = (z + vec.z) / 2;
                }
                target = new Vec3d(x + ((rand.nextBoolean() ? -1 : 1) * rand.nextInt(10)), y + (y > 30 ? 0 : (y > 20 ? ((rand.nextBoolean() ? -1 : 1) * rand.nextInt(10)) : rand.nextInt(10))), z + ((rand.nextBoolean() ? -1 : 1) * rand.nextInt(10)));
            };
            if (target == null) targetSetter.accept(null);
            this.motionX += Math.signum(target.x - posX) / 24;
            this.motionY += Math.signum(target.y - posY) / 24;
            this.motionZ += Math.signum(target.z - posZ) / 24;
            if (getDistanceSq(target.x, target.y, target.z) < 4) target = null;
            else {
                double x = posX - prevPosX;
                double y = posY - prevPosY;
                double z = posZ - prevPosZ;
                double speed = (x * x + y * y + z * z);
                if (speed < 2) targetSetter.accept(target);
            }

            Optional<EntityPlayer> player = world.playerEntities.stream().filter(p -> !p.capabilities.isCreativeMode && !p.isSpectator()).reduce((p1, p2) -> {
                if (getDistanceSq(p1) < getDistanceSq(p2)) return p1;
                else return p2;
            });

            player.ifPresent(this::setAttackTarget);
        } else {
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
