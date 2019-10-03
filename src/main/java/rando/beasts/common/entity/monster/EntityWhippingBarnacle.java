package rando.beasts.common.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import rando.beasts.common.entity.IDriedAquatic;
import rando.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class EntityWhippingBarnacle extends EntityMob implements IDriedAquatic {

    private static final DataParameter<Integer> FACING = EntityDataManager.createKey(EntityWhippingBarnacle.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityWhippingBarnacle.class, DataSerializers.VARINT);
    private static final Iterable<ItemStack> EMPTY = new ArrayList<>();
    public boolean impaling;
    public boolean ready;

    public EntityWhippingBarnacle(World worldIn) {
        super(worldIn);
        setSize(0.5f, 1.5f);
        setNoGravity(true);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        setColor(rand.nextInt(2));
        for(int i = EnumFacing.values().length - 1; i >= 0; --i) {
            EnumFacing facing = EnumFacing.values()[i];
            boolean flag = world.getBlockState(getPosition().offset(facing.getOpposite())).getBlock() == Blocks.STONE;
            for(int j = 1; j <= 3; j++) flag &= world.getBlockState(getPosition().offset(facing, i)).getBlock() == Blocks.AIR;
            if(flag) {
                setFacing(facing);
                break;
            }
        }
        if(getFacing().getHorizontalIndex() != -1) {
            rotationYaw = getFacing().getHorizontalAngle();
            setSize(1.5f, 0.5f);
        }
        return livingdata;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FACING, 0);
        this.dataManager.register(COLOR, 0);
    }

    @Override
    public void onUpdate() {
        this.world.profiler.startSection("entityBaseTick");
        EntityLivingBase[] entities = world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(5)).stream().filter(e -> !(e instanceof IDriedAquatic)).toArray(EntityLivingBase[]::new);
        if(entities.length == 0) {
            ready = false;
            impaling = false;
        } else for (EntityLivingBase entity : entities) {
            if(!ready) ready = true;
            if(getDistanceSq(entity) < 2) {
                EnumFacing facing = getFacing();
                EnumFacing.Axis axis = facing.getAxis();
                double relativePos;
                if (axis == EnumFacing.Axis.Z) relativePos = entity.posZ - this.posZ;
                else relativePos = entity.posX - this.posX;
                if (Math.abs(relativePos) < 0.5f) impaling = true;
                else if (MathHelper.cos(limbSwing) < relativePos) {
                    impaling = false;
                    attackEntityAsMob(entity);
                }
            }
        }
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;

        this.spawnRunningParticles();
        this.handleWaterMovement();

        if (this.isInLava()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5F;
        }

        if (this.posY < -64.0D) this.outOfWorld();
        this.firstUpdate = false;
        this.world.profiler.endSection();
        super.onUpdate();
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {}

    @Override
    public void applyEntityCollision(Entity entityIn) {}

    @Override
    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {}

    @Override
    public boolean getCanSpawnHere() {
        boolean flag = false;
        for(EnumFacing facing : EnumFacing.values()) if(world.getBlockState(getPosition().offset(facing)).getBlock() == Blocks.STONE) {
            flag = true;
            break;
        }
        return flag;
    }

    public void setFacing(EnumFacing facing) {
        this.dataManager.set(FACING, facing.getIndex());
    }

    public EnumFacing getFacing() {
        return EnumFacing.values()[this.dataManager.get(FACING)];
    }

    public void setColor(int color) {
        this.dataManager.set(COLOR, color);
    }

    public int getColor() {
        return this.dataManager.get(COLOR);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return EMPTY;
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {}

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.LEFT;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        dropItem(isBurning() ? BeastsItems.COOKED_BARNACLE_TONGUE : BeastsItems.BARNACLE_TONGUE, 1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setFacing(EnumFacing.values()[compound.getInteger("facing")]);
        this.setColor(compound.getInteger("color"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("facing", this.getFacing().getIndex());
        compound.setInteger("color", this.getColor());
    }
}
