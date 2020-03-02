package random.beasts.common.entity.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import random.beasts.common.init.BeastsEntities;

import java.util.List;

public class EntityChorusBranchie extends EntityBranchieBase {
    public EntityChorusBranchie(EntityType<? extends EntityChorusBranchie> type, World worldIn) {
        super(type, worldIn);
    }

    public static EntityChorusBranchie create(BlockEvent.BreakEvent event) {
        if (event.getWorld() instanceof World) {
            EntityChorusBranchie entity = BeastsEntities.CHORUS_BRANCHIE.create((World) event.getWorld());
            BlockPos pos = event.getPos();
            entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            entity.onInitialSpawn(event.getWorld(), event.getWorld().getDifficultyForLocation(pos), SpawnReason.TRIGGERED, null, null);
            List<EntityChorusBranchie> branchies = event.getWorld().getEntitiesWithinAABB(EntityChorusBranchie.class, new AxisAlignedBB(event.getPos()).grow(10), branchie -> branchie != entity);
            branchies.forEach(branchie -> branchie.setRevengeTarget(event.getPlayer()));
            return entity;
        }
        return null;
    }

    /*@Nullable
    @Override
    protected Item getDropItem() {
        return Items.CHORUS_FRUIT;
    }*/
}
