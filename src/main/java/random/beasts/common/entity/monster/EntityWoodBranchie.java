package random.beasts.common.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;
import random.beasts.common.init.BeastsEntities;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class EntityWoodBranchie extends EntityBranchieBase {
    public static final List<Block> BLOCKS = Arrays.asList(Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG);
    private static final DataParameter<String> VARIANT = EntityDataManager.createKey(EntityWoodBranchie.class, DataSerializers.STRING);
    private Block block;

    public EntityWoodBranchie(EntityType<? extends EntityWoodBranchie> type, World worldIn) {
        super(type, worldIn);
    }

    public static EntityWoodBranchie create(BlockEvent.BreakEvent event) {
        if (event.getWorld() instanceof World) {
            EntityWoodBranchie entity = BeastsEntities.WOOD_BRANCHIE.create((World) event.getWorld());
            BlockPos pos = event.getPos();
            String variant = ForgeRegistries.BLOCKS.getKey(entity.block).getPath().replace("_log", "");
            entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            entity.onInitialSpawn(event.getWorld(), event.getWorld().getDifficultyForLocation(pos), SpawnReason.TRIGGERED, null, null);
            entity.block = event.getState().getBlock();
            entity.setVariant(variant);
            List<EntityWoodBranchie> branchies = event.getWorld().getEntitiesWithinAABB(EntityWoodBranchie.class, new AxisAlignedBB(event.getPos()).grow(10), branchie -> branchie != null && branchie != entity && branchie.getVariant().equals(variant));
            branchies.forEach(branchie -> branchie.setRevengeTarget(event.getPlayer()));
            return entity;
        }
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, "0");
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (reason == SpawnReason.SPAWN_EGG) {
            block = BLOCKS.get(rand.nextInt(BLOCKS.size()));
            this.setVariant(ForgeRegistries.BLOCKS.getKey(block).getPath().replace("_log", ""));
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public String getVariant() {
        return this.dataManager.get(VARIANT);
    }

    private void setVariant(String variant) {
        this.dataManager.set(VARIANT, variant);
    }

    /*@Nullable
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
    }*/

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("variant", this.getVariant());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setVariant(compound.getString("variant"));
    }
}
