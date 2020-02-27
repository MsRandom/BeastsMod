package random.beasts.common.entity.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import random.beasts.common.block.BlockCoralPlant;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsEntities;

import javax.annotation.Nullable;
import java.util.List;

public class EntityCoralBranchie extends EntityBranchieBase {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityCoralBranchie.class, DataSerializers.VARINT);

    public EntityCoralBranchie(EntityType<? extends EntityCoralBranchie> type, World worldIn) {
        super(type, worldIn);
    }

    public static EntityCoralBranchie create(BlockEvent.BreakEvent event) {
        if (event.getWorld() instanceof World) {
            EntityCoralBranchie entity = BeastsEntities.CORAL_BRANCHIE.create((World) event.getWorld());
            BlockPos pos = event.getPos();
            CoralColor color = ((BlockCoralPlant) event.getState().getBlock()).color;
            PlayerEntity player = event.getPlayer();
            entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            entity.onInitialSpawn(event.getWorld(), event.getWorld().getDifficultyForLocation(pos), SpawnReason.TRIGGERED, null, null);
            entity.setVariant(color);
            List<EntityCoralBranchie> branchies = event.getWorld().getEntitiesWithinAABB(EntityCoralBranchie.class, new AxisAlignedBB(event.getPos()).grow(10), branchie -> branchie != null && branchie != entity && branchie.getVariant() == color);
            branchies.forEach(branchie -> branchie.setRevengeTarget(player));
            return entity;
        }
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setVariant(CoralColor.getRandom(rand));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("variant", this.getVariant().ordinal());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setVariant(CoralColor.values()[compound.getInt("variant")]);
    }
}
