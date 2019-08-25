package rando.beasts.common.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class EntityCoconutCrab extends EntityMob {

    private static final DataParameter<Boolean> OUT = EntityDataManager.createKey(EntityCoconutCrab.class, DataSerializers.BOOLEAN);

    public EntityCoconutCrab(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.4f);
        this.tasks.addTask(0, new EntityAIWander(this, 0.5, 50) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(OUT, false);
    }

    public boolean isOut() {
        return this.dataManager.get(OUT);
    }

    private void setOut() {
        this.dataManager.set(OUT, true);
        setSize(width, height + 0.2f);
    }

    @Override
    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
        if(isOut()) super.knockBack(entityIn, strength, xRatio, zRatio);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        return (isOut() || (source != DamageSource.OUT_OF_WORLD && !source.isCreativePlayer())) && super.attackEntityFrom(source, amount);
    }

    @Override
    protected Item getDropItem() {
        return BeastsItems.COCONUT;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        Item coconut = this.getDropItem();
        Item leg = BeastsItems.CRAB_LEG;
        int i = this.rand.nextInt(2);
        if (lootingModifier > 0) i += this.rand.nextInt(lootingModifier + 1);
        for (int j = 0; j < i; ++j) this.dropItem(leg, 1);
        this.dropItem(Objects.requireNonNull(coconut), 1);
        if(this.getHeldItem(EnumHand.MAIN_HAND) != null && this.getHeldItem(EnumHand.MAIN_HAND) != ItemStack.EMPTY){
            this.entityDropItem(this.getHeldItem(EnumHand.MAIN_HAND), 0);
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        if(rand.nextInt(4) == 0) return this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F && world.getBlockState(getPosition()).canEntitySpawn(this) && this.world.getDifficulty() != EnumDifficulty.PEACEFUL ;
        world.setBlockState(getPosition(), BeastsBlocks.COCONUT.getDefaultState());
        return false;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if(!isOut()) {
            setOut();
            if(!player.isCreative()) {
                setRevengeTarget(player);
                attackEntityAsMob(player);
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Override
    public void onUpdate() {
        if(isOut()) {
            super.onUpdate();
            if(this.getHeldItem(EnumHand.MAIN_HAND) == null || (this.getHeldItem(EnumHand.MAIN_HAND) != null && this.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY)) {
                List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(8.0D));
                double d0 = Double.MAX_VALUE;
                EntityItem item = null;

                for (EntityItem itm : list) {
                    if (itm.getItem().getItem() instanceof ItemSword) {
                        if (this.getDistanceSq(itm) < d0) {
                            item = itm;
                            d0 = this.getDistanceSq(itm);
                        }
                    }
                }

                if (item != null) {
                    if (!item.isDead) {
                        this.setHeldItem(EnumHand.MAIN_HAND, item.getItem());
                        item.setDead();
                    }
                }
            }
        }
        else {
            if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) this.setDead();
            if (this.newPosRotationIncrements > 0 && !this.canPassengerSteer()) this.setPosition(posX, this.posY + (this.interpTargetY - this.posY), posZ);
            this.moveStrafing *= 0.98F;
            this.moveForward *= 0.98F;
            this.travel(this.moveStrafing, this.moveVertical, this.moveForward);
        }
    }
}
