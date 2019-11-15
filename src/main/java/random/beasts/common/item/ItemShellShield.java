package random.beasts.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import random.beasts.common.main.BeastsUtils;

@EventBusSubscriber
public class ItemShellShield extends ItemShield
{
    public ItemShellShield()
    {
        this.setMaxDamage(168);
        BeastsUtils.addToRegistry(this, "shell_shield");
    }
    
    @SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event)
    {
		if(!(event.getEntityLiving() instanceof EntityPlayer))
		{
			return;
		}
		
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		
		if(player.getActiveItemStack() == null)
		{
			return;
		}
		
		ItemStack stack = player.getActiveItemStack();
		float damage = event.getAmount();
		
		if(damage > 5.0F && stack != null && (stack.getItem() instanceof ItemShield && stack.getItem() != Items.SHIELD))
		{
			int i = Math.min(1 + (int) damage, stack.getMaxDamage() - stack.getItemDamage());
			
			stack.damageItem(i, player);
			
			if(stack.isEmpty() || stack.getItemDamage() >=stack.getMaxDamage())
			{
				if(stack.isEmpty())
				{
					EnumHand enumhand = player.getActiveHand();
					net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, stack,
							enumhand);
					
					if(enumhand == EnumHand.MAIN_HAND)
					{
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, (ItemStack) null);
					}
					else
					{
						player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, (ItemStack) null);
					}
					
					stack = null;
				}
				
				player.world.playSound(null, player.posX,  player.posY,  player.posZ,
						SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS,
						0.9f, 0.8F + player.world.rand.nextFloat() * 0.4F);
			}
		}
	}
}
