package random.beasts.common.entity.monster;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import random.beasts.common.block.BlockCoralPlant;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsEntities;

import javax.annotation.Nullable;
import java.util.List;

public class EntityCoralBranchie extends EntityBranchieBase {

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityCoralBranchie.class, DataSerializers.VARINT);

    public EntityCoralBranchie(World worldIn) {
        super(worldIn);
    }

    public static EntityCoralBranchie create(BlockEvent.BreakEvent event) {
        EntityCoralBranchie entity = BeastsEntities.CORAL_BRANCHIE.create(event.getWorld());
        BlockPos pos = event.getPos();
        CoralColor color = ((BlockCoralPlant) event.getState().getBlock()).color;
        EntityPlayer player = event.getPlayer();
        entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        entity.onInitialSpawn(event.getWorld().getDifficultyForLocation(pos), null);
        entity.setVariant(color);
        List<EntityCoralBranchie> branchies = event.getWorld().getEntitiesWithinAABB(EntityCoralBranchie.class, new AxisAlignedBB(event.getPos()).grow(10), branchie -> branchie != null && branchie != entity && branchie.getVariant() == color);
        branchies.forEach(branchie -> branchie.setRevengeTarget(player));
        return entity;
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

    public CoralColor getVariant() {
        return CoralColor.values()[this.dataManager.get(VARIANT)];
    }

    private void setVariant(CoralColor variant) {
        this.dataManager.set(VARIANT, variant.ordinal());
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
}
