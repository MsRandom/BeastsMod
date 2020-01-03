package random.beasts.common.entity.monster;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.Nullable;
import java.util.List;

public class EntityChorusBranchie extends EntityBranchieBase {
    public EntityChorusBranchie(World worldIn) {
        super(worldIn);
    }

    @Nullable
    @Override
    protected Item getDropItem() {
        return Items.CHORUS_FRUIT;
    }

    public static EntityChorusBranchie create(BlockEvent.BreakEvent event) {
        EntityChorusBranchie entity = new EntityChorusBranchie(event.getWorld());
        BlockPos pos = event.getPos();
        entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        entity.onInitialSpawn(event.getWorld().getDifficultyForLocation(pos), null);
        List<EntityChorusBranchie> branchies = event.getWorld().getEntitiesWithinAABB(EntityChorusBranchie.class, new AxisAlignedBB(event.getPos()).grow(10), branchie -> branchie != entity);
        branchies.forEach(branchie -> branchie.setRevengeTarget(event.getPlayer()));
        return entity;
    }
}
