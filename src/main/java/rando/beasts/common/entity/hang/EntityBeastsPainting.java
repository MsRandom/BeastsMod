package rando.beasts.common.entity.hang;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.common.init.BeastsItems;

import java.util.ArrayList;

public class EntityBeastsPainting extends EntityHanging implements IEntityAdditionalSpawnData
{
    public EntityBeastsPainting.EnumArt art;

    public EntityBeastsPainting(World worldIn)
    {
        super(worldIn);
    }

    public EntityBeastsPainting(World worldIn, BlockPos pos, EnumFacing side)
    {
        super(worldIn, pos);
        ArrayList arraylist = Lists.newArrayList();
        EntityBeastsPainting.EnumArt[] aenumart = EntityBeastsPainting.EnumArt.values();
        int i = aenumart.length;

        for (int j = 0; j < i; ++j)
        {
            EntityBeastsPainting.EnumArt enumart = aenumart[j];
            this.art = enumart;
            this.updateFacingWithBoundingBox(side);

            if (this.onValidSurface())
            {
                arraylist.add(enumart);
            }
        }

        if (!arraylist.isEmpty())
        {
            this.art = (EntityBeastsPainting.EnumArt)arraylist.get(this.rand.nextInt(arraylist.size()));
        }

        this.updateFacingWithBoundingBox(side);
    }

    public EntityBeastsPainting(World worldIn, BlockPos pos, EnumFacing side, EntityBeastsPainting.EnumArt art)
    {
        this(worldIn, pos, side);

        this.art = art;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(BeastsItems.BEASTS_PAINTING, 1);
    }

    @SideOnly(Side.CLIENT)
    public EntityBeastsPainting(World worldIn, BlockPos pos, EnumFacing side, String name)
    {
        this(worldIn, pos, side);
        EntityBeastsPainting.EnumArt[] aenumart = EntityBeastsPainting.EnumArt.values();
        int i = aenumart.length;

        for (int j = 0; j < i; ++j)
        {
            EntityBeastsPainting.EnumArt enumart = aenumart[j];

            if (enumart.title.equals(name))
            {
                this.art = enumart;
                break;
            }
        }

        this.updateFacingWithBoundingBox(side);
    }

    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString("Motive", this.art.title);
        super.writeEntityToNBT(tagCompound);
    }

    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        String s = tagCompund.getString("Motive");
        EntityBeastsPainting.EnumArt[] aenumart = EntityBeastsPainting.EnumArt.values();
        int i = aenumart.length;

        for (int j = 0; j < i; ++j)
        {
            EntityBeastsPainting.EnumArt enumart = aenumart[j];

            if (enumart.title.equals(s))
            {
                this.art = enumart;
            }
        }

        if (this.art == null)
        {
            this.art = EntityBeastsPainting.EnumArt.WHALE;
        }

        super.readEntityFromNBT(tagCompund);
    }

    public int getWidthPixels()
    {
        return this.art.sizeX;
    }

    public int getHeightPixels()
    {
        return this.art.sizeY;
    }

    public void onBroken(Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entity;

            if (entityplayer.capabilities.isCreativeMode)
            {
                return;
            }
        }

        this.entityDropItem(new ItemStack(BeastsItems.BEASTS_PAINTING), 0.0F);

    }

    @SideOnly(Side.CLIENT)
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch)
    {
        BlockPos blockpos = new BlockPos(x - this.posX, y - this.posY, z - this.posZ);
        BlockPos blockpos1 = this.hangingPosition.add(blockpos);
        this.setPosition((double)blockpos1.getX(), (double)blockpos1.getY(), (double)blockpos1.getZ());
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
        this.setPosition((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
    }

    public static enum EnumArt
    {
        WHALE("Whale", 32, 16, 0, 0),
        CRAB("Crab", 16, 16, 32, 0),
        TURTLE("Turtle", 16, 16, 0, 16),
        PUFFER("Puffer", 16, 16, 16, 16),
        SHRIMP("Shrimp", 16, 16, 32, 16),
        GARDEN_EEL("Garden Eel", 16, 32, 48, 0);


        public final String title;
        public final int sizeX;
        public final int sizeY;
        public final int offsetX;
        public final int offsetY;

        private EnumArt(String name, int par1, int par2, int par3, int par4)
        {
            this.title = name;
            this.sizeX = par1;
            this.sizeY = par2;
            this.offsetX = par3;
            this.offsetY = par4;
        }
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.art.ordinal());

        buffer.writeInt(this.hangingPosition.getX());
        buffer.writeInt(this.hangingPosition.getY());
        buffer.writeInt(this.hangingPosition.getZ());
        buffer.writeByte(this.getHorizontalFacing().getIndex());
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        EntityBeastsPainting.EnumArt[] aenumart = EntityBeastsPainting.EnumArt.values();
        this.art = aenumart[buffer.readInt()];

        int x = buffer.readInt();
        int y = buffer.readInt();
        int z = buffer.readInt();

        BlockPos pos = new BlockPos(x, y, z);
        this.hangingPosition = pos;
        this.updateFacingWithBoundingBox(EnumFacing.getFront((buffer.readByte())));
    }

    @Override
    public void playPlaceSound() {

    }
}