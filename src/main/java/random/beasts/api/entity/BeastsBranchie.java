package random.beasts.api.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class BeastsBranchie extends MonsterEntity {

    public static final Map<Collection<? extends Block>, Tuple<Integer, Function<BlockEvent.BreakEvent, ? extends BeastsBranchie>>> TYPES = new HashMap<>();
    private boolean hasScreamed = false;

    protected BeastsBranchie(World worldIn) {
        super(worldIn);
    }

    protected void initEntityAI() {
        this.goalSelector.addGoal(1, new EntityAIAttackMelee(this, 1.0D, true));
        this.goalSelector.addGoal(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(0, new EntityAISwimming(this));
        this.goalSelector.addGoal(1, new EntityAIWanderAvoidWater(this, 0.5D));
        this.goalSelector.addGoal(2, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(3, new EntityAIAvoidEntity<>(this, PlayerEntity.class, entity -> getAttackTarget() == null, 6.0F, 2, 2));
        this.goalSelector.addGoal(4, new EntityAILookIdle(this));
        this.goalSelector.addGoal(1, new EntityAIPanic(this, 1.0D));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    protected abstract SoundEvent getHurtSound(DamageSource damageSourceIn);

    protected abstract SoundEvent getScreamSound();

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        scream();
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        setAttackTarget(null);
        return super.attackEntityFrom(source, amount);
    }

    public void scream() {
        if (!hasScreamed) {
            hasScreamed = true;
            playSound(getScreamSound(), getSoundVolume(), getSoundPitch());
        }
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public float getEyeHeight() {
        return this.isChild() ? this.height : 0.8F;
    }

}
