package rando.beasts.common.entity.item;

import java.util.ArrayList;

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

public class EntityBeastsPainting extends EntityHanging implements IEntityAdditionalSpawnData {
    public BeastsPainting art;

    public EntityBeastsPainting(World worldIn)
    {
        super(worldIn);
    }

    public EntityBeastsPainting(World worldIn, BlockPos pos, EnumFacing side) {
        super(worldIn, pos);
        ArrayList<BeastsPainting> arraylist = new ArrayList<>();
        BeastsPainting[] aenumart = BeastsPainting.values();

        for (BeastsPainting enumart : aenumart) {
            this.art = enumart;
            this.updateFacingWithBoundingBox(side);
            if (this.onValidSurface()) arraylist.add(enumart);
        }

        if (!arraylist.isEmpty()) this.art = arraylist.get(this.rand.nextInt(arraylist.size()));
        this.updateFacingWithBoundingBox(side);
    }

    public EntityBeastsPainting(World worldIn, BlockPos pos, EnumFacing side, BeastsPainting art) {
        this(worldIn, pos, side);
        this.art = art;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(BeastsItems.BEASTS_PAINTING, 1);
    }

    @SideOnly(Side.CLIENT)
    public EntityBeastsPainting(World worldIn, BlockPos pos, EnumFacing side, String name) {
        this(worldIn, pos, side);
        BeastsPainting[] aenumart = BeastsPainting.values();
        for (BeastsPainting enumart : aenumart) {
            if (enumart.title.equals(name)) {
                this.art = enumart;
                break;
            }
        }

        this.updateFacingWithBoundingBox(side);
    }

    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        tagCompound.setString("Motive", this.art.title);
        super.writeEntityToNBT(tagCompound);
    }

    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        String s = tagCompund.getString("Motive");
        BeastsPainting[] aenumart = BeastsPainting.values();
        for (BeastsPainting enumart : aenumart) if (enumart.title.equals(s)) this.art = enumart;
        if (this.art == null) this.art = BeastsPainting.WHALE;
        super.readEntityFromNBT(tagCompund);
    }

    public int getWidthPixels() {
        return this.art.sizeX;
    }

    public int getHeightPixels() {
        return this.art.sizeY;
    }

    public void onBroken(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (entityplayer.capabilities.isCreativeMode) return;
        }

        this.entityDropItem(new ItemStack(BeastsItems.BEASTS_PAINTING), 0.0F);
    }

    @SideOnly(Side.CLIENT)
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
        BlockPos pos = this.hangingPosition.add(new BlockPos(x - this.posX, y - this.posY, z - this.posZ));
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
        this.setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
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
        BeastsPainting[] aenumart = BeastsPainting.values();
        this.art = aenumart[buffer.readInt()];
        int x = buffer.readInt();
        int y = buffer.readInt();
        int z = buffer.readInt();
        this.hangingPosition = new BlockPos(x, y, z);
        this.updateFacingWithBoundingBox(EnumFacing.getFront((buffer.readByte())));
    }

    @Override
    public void playPlaceSound() {}

    public enum BeastsPainting {
        WHALE("Whale", 32, 16),
        CRAB("Crab"),
        TURTLE("Turtle"),
        PUFFER("Puffer"),
        SHRIMP("Shrimp"),
        GARDEN_EEL("Garden Eel", 16, 32);


        public final String title;
        public int sizeX;
        public int sizeY;

        BeastsPainting(String name, int x, int y) {
            this.title = name;
            this.sizeX = x;
            this.sizeY = y;
        }

        BeastsPainting(String name) {
            this(name, 16, 16);
        }
    }
}