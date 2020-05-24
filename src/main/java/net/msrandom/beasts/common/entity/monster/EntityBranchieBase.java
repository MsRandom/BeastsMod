package net.msrandom.beasts.common.entity.monster;

import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.msrandom.beasts.api.entity.BeastsBranchie;
import net.msrandom.beasts.client.init.BeastsSounds;

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
