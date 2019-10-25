package random.beasts.common.entity.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.common.init.BeastsBlocks;

public class EntityFallingCoconut extends Entity {

    private IBlockState fallTile;
    private int fallTime;
    private boolean shouldDropItem = true;

    public EntityFallingCoconut(World worldIn) {
        super(worldIn);
        this.fallTile = BeastsBlocks.COCONUT.getDefaultState();
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
    }

    public EntityFallingCoconut(World worldIn, BlockPos pos) {
        this(worldIn);
        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;
        this.setPosition(x, y + (double)((1.0F - this.height) / 2.0F), z);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public boolean canBeAttackedWithItem()
    {
        return false;
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit() {}

    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    public void onUpdate() {
        Block block = this.fallTile.getBlock();
        if (this.fallTile.getMaterial() == Material.AIR) this.setDead();
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;

            if (this.fallTime++ == 0) {
                BlockPos blockpos = new BlockPos(this);
                if (this.world.getBlockState(blockpos).getBlock() == block) this.world.setBlockToAir(blockpos);
                else if (!this.world.isRemote) {
                    this.setDead();
                    return;
                }
            }

            if (!this.hasNoGravity()) this.motionY -= 0.03999999910593033D;
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            if (!this.world.isRemote) {
                BlockPos blockpos1 = new BlockPos(this);
                if (!this.onGround) {
                    if (this.fallTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600) {
                        if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops")) this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
                        this.setDead();
                    }
                } else {
                    IBlockState iblockstate = this.world.getBlockState(blockpos1);

                    if (this.world.isAirBlock(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ))) //Forge: Don't indent below.
                        if (BlockFalling.canFallThrough(this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ)))) {
                            this.onGround = false;
                            return;
                        }

                    this.motionX *= 0.699999988079071D;
                    this.motionZ *= 0.699999988079071D;
                    this.motionY *= -0.5D;

                    if (iblockstate.getBlock() != Blocks.PISTON_EXTENSION) {
                        this.setDead();
                        if (!(this.world.mayPlace(block, blockpos1, true, EnumFacing.UP, null) && !BlockFalling.canFallThrough(this.world.getBlockState(blockpos1.down())) && this.world.setBlockState(blockpos1, this.fallTile, 3)) && (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops"))) this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
                    }
                }
            }

            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;
        }
    }

    public void fall(float distance, float damageMultiplier) {

        int i = MathHelper.ceil(distance - 1.0F);

        if (i > 0) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
            DamageSource damagesource = DamageSource.FALLING_BLOCK;
            for (Entity entity : list) entity.attackEntityFrom(damagesource, 6.0F);
        }
    }

    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Time", this.fallTime);
        compound.setBoolean("DropItem", this.shouldDropItem);
    }

    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.fallTime = compound.getInteger("Time");
        if (compound.hasKey("DropItem", 99)) this.shouldDropItem = compound.getBoolean("DropItem");
    }

    public void addEntityCrashInfo(CrashReportCategory category) {
        super.addEntityCrashInfo(category);
        if (this.fallTile != null) {
            Block block = this.fallTile.getBlock();
            category.addCrashSection("Immitating block ID", Block.getIdFromBlock(block));
            category.addCrashSection("Immitating block data", block.getMetaFromState(this.fallTile));
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }

    public boolean ignoreItemEntityData() {
        return true;
    }
}
