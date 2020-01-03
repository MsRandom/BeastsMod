package random.beasts.common.entity.monster;

import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import random.beasts.client.init.BeastsSounds;
import random.beasts.entity.BeastsBranchie;

public class EntityBranchieBase extends BeastsBranchie {
    EntityBranchieBase(World worldIn) {
        super(worldIn);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.BRANCHIE_HURT;
    }

    @Override
    protected SoundEvent getScreamSound() {
        return BeastsSounds.BRANCHIE_SCREAM;
    }
}
