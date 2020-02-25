package random.beasts.common.entity.monster;

import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IMobEntityData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import random.beasts.common.init.BeastsEntities;

import javax.annotation.Nullable;
import java.util.List;

public class EntityWoodBranchie extends EntityBranchieBase {

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityWoodBranchie.class, DataSerializers.VARINT);

    public EntityWoodBranchie(World worldIn) {
        super(worldIn);
    }

    public static EntityWoodBranchie create(BlockEvent.BreakEvent event) {
        EntityWoodBranchie entity = BeastsEntities.WOOD_BRANCHIE.create(event.getWorld());
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();
        BlockPlanks.EnumType variant = state.getBlock() == Blocks.LOG ? state.getValue(BlockOldLog.VARIANT) : state.getValue(BlockNewLog.VARIANT);
        entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        entity.onInitialSpawn(event.getWorld().getDifficultyForLocation(pos), null);
        entity.setVariant(variant);
        List<EntityWoodBranchie> branchies = event.getWorld().getEntitiesWithinAABB(EntityWoodBranchie.class, new AxisAlignedBB(event.getPos()).grow(10), branchie -> branchie != null && branchie != entity && branchie.getVariant() == variant);
        branchies.forEach(branchie -> branchie.setRevengeTarget(event.getPlayer()));
        return entity;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    @Nullable
    @Override
    public IMobEntityData onInitialSpawn(DifficultyInstance difficulty, @Nullable IMobEntityData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(BlockPlanks.EnumType.values()[rand.nextInt(BlockPlanks.EnumType.values().length)]);
        return livingdata;
    }

    public BlockPlanks.EnumType getVariant() {
        return BlockPlanks.EnumType.values()[this.dataManager.get(VARIANT)];
    }

    private void setVariant(BlockPlanks.EnumType variant) {
        this.dataManager.set(VARIANT, variant.ordinal());
    }

    @Nullable
    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.SAPLING);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        Item item = this.getDropItem();
        if (item != null) {
            int i = this.rand.nextInt(3) + 2;
            if (lootingModifier > 0) i += this.rand.nextInt(lootingModifier + 1);
            for (int j = 0; j < i; ++j) this.entityDropItem(new ItemStack(Items.STICK), 0);
            for (int j = 0; j < Math.floor(i / 2f); ++j)
                this.entityDropItem(new ItemStack(item, 1, getVariant().ordinal()), 0);
        }
    }

    @Override
    public void writeEntityToNBT(CompoundNBT compound) {
        super.writeEntityToNBT(compound);
        compound.putInt("variant", this.getVariant().ordinal());
    }

    @Override
    public void readEntityFromNBT(CompoundNBT compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(BlockPlanks.EnumType.values()[compound.getInt("variant")]);
    }
}
