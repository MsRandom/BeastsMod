package random.beasts.common.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.init.BeastsBiomes;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;
import java.util.Optional;

public class EntityScallop extends MonsterEntity implements IFlyingAnimal {

    private double preferredRotation = -1;
    private int preferredAltitude = -1;
    private int targetBlocks;
    private double blocksFlew;

    public EntityScallop(EntityType<? extends EntityScallop> type, World worldIn) {
        super(type, worldIn);
        setNoGravity(true);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new FlyingPathNavigator(this, worldIn);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setMotion(getMotion().add(0, 0.5, 0));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void jump() {
    }

    @Override
    public void livingTick() {
        super.livingTick();
        int speed = MathHelper.floor(4 / getAttribute(SharedMonsterAttributes.FLYING_SPEED).get());
        int worldHeight = world.getHeight((int) (posX + 0.5), (int) (posZ + 0.5));
        if (getAttackTarget() == null) {
            Runnable targetSetter = () -> {
                if (needsTarget()) {
                    this.preferredRotation = this.rand.nextDouble() * 360;
                    this.preferredAltitude = worldHeight + this.rand.nextInt(30) + 30;
                    this.targetBlocks = this.rand.nextInt(20) + 30;
                } else {
                    this.preferredRotation += this.rand.nextInt(31) - 15;
                    this.preferredAltitude += this.rand.nextInt(21) - 10;
                    this.targetBlocks += this.rand.nextInt(11) - 5;
                }
            };
            if (needsTarget()) targetSetter.run();
            double angle = Math.toRadians(preferredRotation);
            this.setMotion(getMotion().add(Math.cos(angle) / 24, 0, Math.sin(-angle) / 24));
            this.blocksFlew += (posX - prevPosX) + (posZ - prevPosZ);
            if (Math.abs(targetBlocks - blocksFlew) < 3) targetSetter.run();
            this.getLookController().setLookPosition(posX + (posX - prevPosX), 0, posZ + (posZ - prevPosZ), 0, 0);

            Optional<? extends PlayerEntity> player = world.getPlayers().stream().filter(p -> !p.abilities.isCreativeMode && !p.isSpectator() && world.getBiome(p.getPosition()) == BeastsBiomes.DRIED_REEF && getDistanceSq(p) <= 1280).reduce((p1, p2) -> {
                if (getDistanceSq(p1) > getDistanceSq(p2)) return p2;
                return p1;
            });

            player.ifPresent(this::setAttackTarget);
        } else {
            if (getAttackTarget() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) getAttackTarget();
                boolean dead = !player.isAlive();
                if (player.abilities.isCreativeMode || player.isSpectator() || dead) {
                    if (dead) {
                        preferredAltitude = -1;
                        preferredRotation = -1;
                    }
                    setAttackTarget(null);
                    return;
                }
            }
            this.preferredAltitude = Math.round((float) getAttackTarget().posY + 8);
            if (getDistanceSq(getAttackTarget()) < 2) {
                attackEntityAsMob(getAttackTarget());
                getLookController().setLookPosition(-posX, -(posY + getEyeHeight()), -posZ, 0, 0);
                this.setMotion(-this.getMotion().x, Math.abs(this.getMotion().y) + 0.1, -this.getMotion().z);
            } else {
                getLookController().setLookPositionWithEntity(getAttackTarget(), 0, 0);
                this.setMotion(this.getMotion().add(Math.signum(getAttackTarget().posX - posX) / speed, stableFlying() ? Math.signum(getAttackTarget().posY - posY) / speed : 0, Math.signum(getAttackTarget().posZ - posZ) / speed));
            }
        }
        if (!stableFlying())
            this.setMotion(this.getMotion().add(0, Math.signum(preferredAltitude - posY) / (speed * 2), 0));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.16);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1);
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

    private boolean needsTarget() {
        return preferredRotation == -1 || preferredAltitude == -1;
    }

    private boolean stableFlying() {
        return Math.abs(preferredAltitude - posY) <= 12;
    }
}
