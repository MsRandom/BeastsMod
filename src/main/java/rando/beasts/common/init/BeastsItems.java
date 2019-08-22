package rando.beasts.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import rando.beasts.common.block.BeastsBlock;
import rando.beasts.common.item.*;

public class BeastsItems {
	
	public static final List<Item> LIST = new ArrayList<>();
	public static final Item.ToolMaterial SWORDFISH_MAT = EnumHelper.addToolMaterial("daggerfish", 0, 3, 8.0F, 4.0F, 0);
	public static final ItemArmor.ArmorMaterial SPARTAPOD = EnumHelper.addArmorMaterial("spartapod", "beasts:spartapod", 15, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
    public static final ItemArmor.ArmorMaterial SPARTAPODA = EnumHelper.addArmorMaterial("spartapoda", "beasts:spartapoda", 15, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);

	public static final Item GLOW_ROOT = new BeastsItem("glow_root"){
		@Override
		public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			if(facing != EnumFacing.DOWN || worldIn.getBlockState(pos).getBlock() == BeastsBlocks.GLOW_ROOT_BOTTOM || worldIn.getBlockState(pos).getBlock() == BeastsBlocks.GLOW_ROOT_TOP)
				return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
			System.out.println(worldIn.getBlockState(pos).getBlock().getUnlocalizedName());

			if(worldIn.getBlockState(pos.down()) == Blocks.AIR.getDefaultState() && worldIn.getBlockState(pos.down().down()) == Blocks.AIR.getDefaultState() ) {
				worldIn.setBlockState(pos.down(), BeastsBlocks.GLOW_ROOT_TOP.getDefaultState());
				worldIn.setBlockState(pos.down().down(), BeastsBlocks.GLOW_ROOT_BOTTOM.getDefaultState());

				SoundType soundtype = worldIn.getBlockState(pos.down()).getBlock().getSoundType(worldIn.getBlockState(pos.down()), worldIn, pos, player);
				worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				if (!player.capabilities.isCreativeMode) {
					player.getHeldItem(hand).shrink(1);
				}
				return EnumActionResult.SUCCESS;
			}
			return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}
	};
    public static final Item SPARTAPOD_BOOTS = new ItemCustomArmor("spartapod_boots", SPARTAPODA, 1, EntityEquipmentSlot.FEET);
    public static final Item SPARTAPOD_LEGS = new ItemCustomArmor("spartapod_legs", SPARTAPODA, 1, EntityEquipmentSlot.LEGS);
    public static final Item SPARTAPOD_CHEST = new ItemCustomArmor("spartapod_chest", SPARTAPODA, 0, EntityEquipmentSlot.CHEST);
	public static final Item SPARTAPOD_HELMET = new ItemCustomArmor("spartapod_helmet", SPARTAPOD, 0, EntityEquipmentSlot.HEAD);
	public static final Item ICON = new BeastsIconItem("icon");
	public static final Item LEAFY_BONE = new BeastsItem("leafy_bone");
	public static final Item CARROT_COIN = new BeastsItem("carrot_coin");
	public static final Item COCONADE = new ItemCoconade("coconade");
	public static final Item CORAL_ESSENCE_BLUE = new BeastsItem("coral_essence_blue");
	public static final Item CORAL_ESSENCE_PINK = new BeastsItem("coral_essence_pink");
	public static final Item CORAL_ESSENCE_PURPLE = new BeastsItem("coral_essence_purple");
	public static final Item CORAL_ESSENCE_RED = new BeastsItem("coral_essence_red");
	public static final Item CORAL_ESSENCE_YELLOW = new BeastsItem("coral_essence_yellow"); 
	public static final Item COCONUT_BOWL = new BeastsItem("coconut_bowl").setMaxStackSize(2);
	public static final Item DAGGERFISH = new BeastsItem("daggerfish"); 
	public static final Item FISHSTAR = new BeastsItem("fishstar"); 
	public static final Item ATHAPOD_CHITIN = new BeastsItem("athapod_chitin"); 
	public static final Item SPARTAPOD_CHITIN = new BeastsItem("spartapod_chitin"); 
	public static final Item SPARTAPOD_CREST = new BeastsItem("spartapod_crest"); 
	public static final Item ICE_CRAB_CHITIN = new BeastsItem("ice_crab_chitin"); 
	public static final Item WORM_TOOTH = new BeastsItem("worm_tooth"); 
	public static final ItemSword COOKED_DAGGERFISH = new ItemCookedDaggerFish(); 
	public static final ItemSword DIAMOND_CARROT = new ItemDiamondCarrot();
	public static final ItemFood CRAB_LEG = new BeastsFood("crab_leg", 2, 0.1F);
	public static final ItemFood COOKED_CRAB_LEG = new BeastsFood("cooked_crab_leg", 6, 0.6F);
	public static final ItemFood COCONUT = new BeastsFood("coconut", 2, 0.4F);
	public static final ItemFood BARNACLE_TONGUE = new BeastsFood("barnacle_tongue", 2, 0.1F){
		@Override
		protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 0));
			super.onFoodEaten(stack, worldIn, player);
		}
	};
	public static final ItemFood COOKED_BARNACLE_TONGUE = new BeastsFood("cooked_barnacle_tongue", 5, 0.6F){
		@Override
		protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
			super.onFoodEaten(stack, worldIn, player);
		}
	};
	public static final ItemFood SHRIMP = new BeastsFood("shrimp", 2, 0.2F){
		@Override
		protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
			if(player.getRNG().nextFloat() <= 0.3F) {
				player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0));
			}
			super.onFoodEaten(stack, worldIn, player);
		}
	};
	public static final ItemFood COOKED_SHRIMP = new BeastsFood("cooked_shrimp", 6, 0.5F);
	public static final ItemFood RAW_KEBAB = new BeastsFood("raw_kebab", 4, 0.3F){
		@Override
		protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
			if(player.getRNG().nextFloat() <= 0.3F) {
				player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0));
			}
			super.onFoodEaten(stack, worldIn, player);
		}
	};
	public static final ItemFood COOKED_KEBAB = new BeastsFood("cooked_kebab", 8, 0.7F); 
	public static final ItemFood EEL_CHOP = new BeastsFood("eel_chop", 3, 0.3F); 
	public static final ItemFood COOKED_EEL_CHOP = new BeastsFood("cooked_eel_chop", 8, 0.5F); 

	public static final ItemFood COCONUT_MUSHROOM = new BeastsFood("coconut_mushroom", 6, 0.7F){
		@Override
		protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
			player.inventory.add(1, new ItemStack(COCONUT_BOWL));
			super.onFoodEaten(stack, worldIn, player);
		}
	};
	public static final ItemFood COCONUT_JUICE = new BeastsFood("coconut_juice", 4, 0.5F){
		@Override
		protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
			player.inventory.add(1, new ItemStack(COCONUT_BOWL));
			super.onFoodEaten(stack, worldIn, player);
		}
	};
	public static final ItemFood COCONUT_RABBIT_STEW = new BeastsFood("coconut_rabbit_stew", 8, 0.7F){
		@Override
		protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
			player.inventory.add(1, new ItemStack(COCONUT_BOWL));
			super.onFoodEaten(stack, worldIn, player);
		}
	};
	public static final ItemFood REEF_MIXTURE = new BeastsFood("reef_mixture", 10, 0.9F){
		@Override
		protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
			player.inventory.add(1, new ItemStack(COCONUT_BOWL));
			super.onFoodEaten(stack, worldIn, player);
		}
	};
}
