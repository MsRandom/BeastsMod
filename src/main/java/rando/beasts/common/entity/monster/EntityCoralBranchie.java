package rando.beasts.common.entity.monster;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import rando.beasts.common.block.BlockCoralPlant;
import rando.beasts.common.block.CoralColor;
import rando.beasts.common.init.BeastsBlocks;

import javax.annotation.Nullable;

public class EntityCoralBranchie extends EntityBranchieBase {

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityCoralBranchie.class, DataSerializers.VARINT);

    public EntityCoralBranchie(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(CoralColor.getRandom(rand));
        return livingdata;
    }

    private void setVariant(CoralColor variant) {
        this.dataManager.set(VARIANT, variant.ordinal());
    }

    public CoralColor getVariant() {
        return CoralColor.values()[this.dataManager.get(VARIANT)];
    }

    @Nullable
    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(BeastsBlocks.CORAL_BLOCK);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        Item item = this.getDropItem();
        if (item != null) {
            int i = this.rand.nextInt(3);
            if (lootingModifier > 0) i += this.rand.nextInt(lootingModifier + 1);
            for (int j = 0; j < i; ++j) this.entityDropItem(new ItemStack(item, 1, getVariant().ordinal()), 0);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("variant", this.getVariant().ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(CoralColor.values()[compound.getInteger("variant")]);
    }

    public static EntityCoralBranchie create(BlockEvent.BreakEvent event) {
        EntityCoralBranchie entity = new EntityCoralBranchie(event.getWorld());
        BlockPos pos = event.getPos();
        entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        entity.onInitialSpawn(event.getWorld().getDifficultyForLocation(pos), null);
        entity.setVariant(((BlockCoralPlant)event.getState().getBlock()).color);
        return entity;
    }
}
