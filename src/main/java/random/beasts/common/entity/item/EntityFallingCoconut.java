package random.beasts.common.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsEntities;

import java.util.List;

public class EntityFallingCoconut extends Entity {
    private BlockState fallTile;
    private int fallTime;
    private boolean shouldDropItem = true;

    public EntityFallingCoconut(EntityType<? extends EntityFallingCoconut> type, World worldIn) {
        super(type, worldIn);
        this.fallTile = BeastsBlocks.COCONUT.getDefaultState();
        this.preventEntitySpawning = true;
    }

    public EntityFallingCoconut(World worldIn, BlockPos pos) {
        this(BeastsEntities.FALLING_COCONUT, worldIn);
        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;
        this.setPosition(x, y + (double) ((1.0F - this.getHeight()) / 2.0F), z);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void registerData() {
    }

    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    public void tick() {
        if (this.fallTile.getMaterial() == Material.AIR) this.remove();
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            Block block = this.fallTile.getBlock();
            if (this.fallTime++ == 0) {
                BlockPos blockpos = new BlockPos(this);
                if (this.world.getBlockState(blockpos).getBlock() == block) this.world.removeBlock(blockpos, false);
                else if (!this.world.isRemote) {
                    this.remove();
                    return;
                }
            }

            if (!this.hasNoGravity()) this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
            this.move(MoverType.SELF, this.getMotion());

            if (!this.world.isRemote) {
                BlockPos blockpos1 = new BlockPos(this);
                if (!this.onGround) {
                    if (this.fallTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600) {
                        if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
                            this.entityDropItem(block);
                        this.remove();
                    }
                } else {
                    BlockState iblockstate = this.world.getBlockState(blockpos1);

                    if (this.world.isAirBlock(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ))) {
                        if (FallingBlock.canFallThrough(this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ)))) {
                            this.onGround = false;
                            return;
                        }
                    }

                    this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
                    if (iblockstate.getBlock() != Blocks.MOVING_PISTON) {
                        this.remove();
                        if (iblockstate.isReplaceable(new DirectionalPlaceContext(this.world, blockpos1, Direction.DOWN, ItemStack.EMPTY, Direction.UP)) && !this.world.setBlockState(blockpos1, fallTile) && !FallingBlock.canFallThrough(this.world.getBlockState(blockpos1.down())) && this.world.setBlockState(blockpos1, this.fallTile, 3) && this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
                            this.entityDropItem(block);
                    }
                }
            }

            this.setMotion(this.getMotion().scale(0.98));
        }
    }

    public void fall(float distance, float damageMultiplier) {

        int i = MathHelper.ceil(distance - 1.0F);

        if (i > 0) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox());
            DamageSource damagesource = DamageSource.FALLING_BLOCK;
            for (Entity entity : list) entity.attackEntityFrom(damagesource, 6.0F);
        }
    }

    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Time", this.fallTime);
        compound.putBoolean("DropItem", this.shouldDropItem);
    }

    protected void readAdditional(CompoundNBT compound) {
        this.fallTime = compound.getInt("Time");
        if (compound.contains("DropItem", 99)) this.shouldDropItem = compound.getBoolean("DropItem");
    }

    public void fillCrashReport(CrashReportCategory category) {
        super.fillCrashReport(category);
        if (this.fallTile != null) {
            category.addDetail("Immitating BlockState", this.fallTile.toString());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }

    public boolean ignoreItemEntityData() {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, Block.getStateId(fallTile));
    }
}
