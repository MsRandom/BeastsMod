package net.msrandom.beasts.common.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.msrandom.beasts.client.init.BeastsSounds;
import net.msrandom.beasts.common.init.BeastsBiomes;
import net.msrandom.beasts.common.init.BeastsBlocks;
import net.msrandom.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;

public class EntityScallop extends EntityMob implements EntityFlying {

    private double preferredRotation = -1;
    private int preferredAltitude = -1;
    private int targetBlocks;
    private double blocksFlew;

    public EntityScallop(World worldIn) {
        super(worldIn);
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
        this.motionY += 0.2;
        return livingdata;
    }

    @Override
    protected void jump() {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        int speed = MathHelper.floor(4 / getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue());
        int worldHeight = world.getHeight((int) (posX + 0.5), (int) (posZ + 0.5));
        if (getAttackTarget() == null) {
            Runnable targetSetter = () -> {
                if (needsTarget()) {
                    this.preferredRotation = this.rand.nextDouble() * 360;
                    this.preferredAltitude = worldHeight + this.rand.nextInt(20) + 10;
                    this.targetBlocks = this.rand.nextInt(20) + 30;
                } else {
                    this.preferredRotation += this.rand.nextInt(31) - 15;
                    this.preferredAltitude += this.rand.nextInt(21) - 10;
                    this.targetBlocks += this.rand.nextInt(11) - 5;
                }
            };
            if (needsTarget()) targetSetter.run();
            double angle = Math.toRadians(preferredRotation);
            this.motionX += Math.cos(angle) / 32;
            this.motionZ += Math.sin(-angle) / 32;
            this.blocksFlew += (posX - prevPosX) + (posZ - prevPosZ);
            if (Math.abs(targetBlocks - blocksFlew) < 3) targetSetter.run();
            this.getLookHelper().setLookPosition(posX + (posX - prevPosX), 0, posZ + (posZ - prevPosZ), 0, 0);
            this.world.playerEntities.stream().filter(p -> !p.capabilities.isCreativeMode && !p.isSpectator() && world.getBiome(p.getPosition()) == BeastsBiomes.DRIED_REEF && getDistanceSq(p) <= 1280)
                    .reduce((p1, p2) -> getDistanceSq(p1) > getDistanceSq(p2) ? p2 : p1)
                    .ifPresent(this::setAttackTarget);
        } else {
            if (getAttackTarget() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) getAttackTarget();
                boolean dead = player.isDead;
                if (player.capabilities.isCreativeMode || player.isSpectator() || dead) {
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
                getLookHelper().setLookPosition(-posX, -(posY + getEyeHeight()), -posZ, 0, 0);
                this.motionX = -motionX;
                this.motionY = Math.abs(motionY) + 0.1;
                this.motionZ = -motionZ;
            } else {
                getLookHelper().setLookPositionWithEntity(getAttackTarget(), 0, 0);
                this.motionX += Math.signum(getAttackTarget().posX - posX) / speed;
                this.motionZ += Math.signum(getAttackTarget().posZ - posZ) / speed;
                if (stableFlying()) this.motionY += Math.signum(getAttackTarget().posY - posY) / speed;
            }
        }
        if (!stableFlying()) this.motionY += Math.signum(preferredAltitude - posY) / (speed * 2);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.16);
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

    private boolean needsTarget() {
        return preferredRotation == -1 || preferredAltitude == -1;
    }

    private boolean stableFlying() {
        return Math.abs(preferredAltitude - posY) <= 12;
    }
}
