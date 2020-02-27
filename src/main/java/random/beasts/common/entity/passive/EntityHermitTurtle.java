package random.beasts.common.entity.passive;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import random.beasts.api.entity.IShellEntity;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.entity.monster.EntityVileEel;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsEntities;

import javax.annotation.Nullable;
import java.util.List;

public class EntityHermitTurtle extends AnimalEntity implements IShellEntity {
    private static final DataParameter<Boolean> OUT = EntityDataManager.createKey(EntityHermitTurtle.class, DataSerializers.BOOLEAN);
    public int exitTicks = 25;

    public EntityHermitTurtle(EntityType<? extends EntityHermitTurtle> type, World worldIn) {
        super(type, worldIn);
        this.goalSelector.addGoal(0, new RandomWalkingGoal(this, 0.2, 200) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isOut()) {
            return super.attackEntityFrom(source, amount);
        } else {
            if (source.getImmediateSource() != null) {
                this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                return false;
            } else {
                return super.attackEntityFrom(source, amount);
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return BeastsSounds.HERMIT_TURTLE_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.HERMIT_TURTLE_HURT;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Item.getItemFromBlock(BeastsBlocks.PALM_LEAVES);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return BeastsEntities.HERMIT_TURTLE.create(this.world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(OUT, false);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AIHide(this, false));
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        if (rand.nextInt(16) == 0) this.dropItem(Item.getItemFromBlock(BeastsBlocks.HERMIT_SHELL), 1);
    }

    public boolean isOut() {
        return this.dataManager.get(OUT);
    }

    private void setOut(boolean out) {
        this.exitTicks = out ? 24 : 1;
        this.dataManager.set(OUT, out);
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isRemote) {
            boolean out = isOut();
            if (exitTicks != (out ? 0 : 25))
                exitTicks += (out ? -1 : 1);
        }
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12);
    }

    static class AIHide extends TargetGoal {
        AIHide(EntityHermitTurtle creature, boolean checkSight) {
            super(creature, checkSight, true);
        }

        @Override
        public boolean shouldExecute() {
            List<PlayerEntity> players = this.goalOwner.world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(this.goalOwner.getPosition()).grow(5));
            List<EntityVileEel> eels = this.goalOwner.world.getEntitiesWithinAABB(EntityVileEel.class, new AxisAlignedBB(this.goalOwner.getPosition()).grow(10));
            boolean plBool = false;
            if (!players.isEmpty()) {
                for (PlayerEntity player : players) {
                    if (!player.isSneaking()) {
                        plBool = true;
                    }
                }
            }
            return plBool || !eels.isEmpty();
        }

        public void startExecuting() {
            if (((EntityHermitTurtle) this.goalOwner).isOut()) ((EntityHermitTurtle) this.goalOwner).setOut(false);
        }

        public boolean shouldContinueExecuting() {
            return shouldExecute();
        }

        public void resetTask() {
            if (!((EntityHermitTurtle) this.goalOwner).isOut()) ((EntityHermitTurtle) this.goalOwner).setOut(true);
        }
    }
}
