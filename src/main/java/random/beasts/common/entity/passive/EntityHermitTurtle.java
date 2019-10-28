package random.beasts.common.entity.passive;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import random.beasts.common.block.BeastsBlock;
import random.beasts.common.entity.monster.EntityVileEel;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.item.BeastsItem;

import javax.annotation.Nullable;

public class EntityHermitTurtle extends EntityAnimal {
    private static final DataParameter<Boolean> OUT = EntityDataManager.createKey(EntityHermitTurtle.class, DataSerializers.BOOLEAN);

    public EntityHermitTurtle(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 0.7F);
        this.tasks.addTask(0, new EntityAIWander(this, 0.2, 200) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0D));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(this.isOut()){
            return super.attackEntityFrom(source, amount);
        }
        else{
            if(source.getImmediateSource() != null){
                this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                return false;
            }
            else{
                return super.attackEntityFrom(source, amount);
            }
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Item.getItemFromBlock(BeastsBlocks.PALM_LEAVES);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityHermitTurtle(this.world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(OUT, false);
    }
    
    protected void initEntityAI() {
    	this.tasks.addTask(0, new AIHide(this, false));
    }
    
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        if(rand.nextInt(16) == 0) this.dropItem(Item.getItemFromBlock(BeastsBlocks.HERMIT_SHELL), 1);
    }

    public boolean isOut() {
        return this.dataManager.get(OUT);
    }

    private void setOut(boolean out) {
        this.dataManager.set(OUT, out);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12);
    }
    
    static class AIHide extends EntityAITarget{

		public AIHide(EntityHermitTurtle creature, boolean checkSight) {
			super(creature, checkSight, true);
		}
		
		@Override
		public boolean shouldExecute() {
			List<EntityPlayer> players = this.taskOwner.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.taskOwner.getPosition()).grow(10));
			List<EntityVileEel> eels = this.taskOwner.world.getEntitiesWithinAABB(EntityVileEel.class, new AxisAlignedBB(this.taskOwner.getPosition()).grow(10));
			boolean plBool = false;
			if(!players.isEmpty()){
                plBool = false;
                for (EntityPlayer player : players) {
                    if (!player.isSneaking()) {
                        plBool = true;
                    }
                }
            }
			return plBool || !eels.isEmpty();
		}

		public void startExecuting()
	    {
			if(((EntityHermitTurtle)this.taskOwner).isOut()) ((EntityHermitTurtle)this.taskOwner).setOut(false);
	    }
		
		public boolean shouldContinueExecuting()
	    {
			return shouldExecute();
	    }
		
		public void resetTask()
	    {
			if(!((EntityHermitTurtle)this.taskOwner).isOut()) ((EntityHermitTurtle)this.taskOwner).setOut(true);
	    }	
    }
}
