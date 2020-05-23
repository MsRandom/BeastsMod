package random.beasts.common.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import random.beasts.common.entity.projectile.EntityGlowShrimpShot;
import random.beasts.common.init.BeastsItems;

public class EntityGlowShrimp extends EntityMob implements IRangedAttackMob {

	public EntityGlowShrimp(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(2, new EntityAIAttackRanged(this, 0.6D, 40, 10));
		this.tasks.addTask(3, new EntityAIWander(this, 0.6F));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1F);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6F);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
	}

	@Override
	public int getBrightnessForRender() {
		return 255;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		EntityGlowShrimpShot glowShrimpShot = new EntityGlowShrimpShot(world, this);
		double d0 = target.posY + (double) target.getEyeHeight() - 1.100000023841858D;
		double d1 = target.posX + target.motionX - this.posX;
		double d2 = d0 - this.posY;
		double d3 = target.posZ + target.motionZ - this.posZ;
		float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
		glowShrimpShot.shoot(d1, d2 + (double) (f * 0.2F), d3, 0.75F, 8.0F);
		this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
		this.world.spawnEntity(glowShrimpShot);
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		super.dropFewItems(wasRecentlyHit, lootingModifier);
		int shrimp = 1;
		for (int i = 0; i <= lootingModifier; ++i) {
			shrimp += rand.nextInt(2);
		}
		this.entityDropItem(new ItemStack(BeastsItems.SHRIMP, shrimp), 0);
		System.out.println(lootingModifier);
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
	}
}
