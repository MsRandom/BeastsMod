package rando.beasts.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import rando.beasts.common.item.BeastsFood;
import rando.beasts.common.item.BeastsItem;
import rando.beasts.common.item.ItemCookedDaggerFish;
import rando.beasts.common.item.ItemDiamondCarrot;

public class BeastsItems {
	
	public static final List<Item> LIST = new ArrayList<>();
	
	public static final Item ICON = new BeastsItem("icon");
	public static final Item LEAFY_BONE = new BeastsItem("leafy_bone");
	public static final Item CARROT_COIN = new BeastsItem("carrot_coin");
	public static final Item COCONADE = new BeastsItem("coconade");
	public static final Item CORAL_ESSENCE_BLUE = new BeastsItem("coral_essence_blue");
	public static final Item CORAL_ESSENCE_PINK = new BeastsItem("coral_essence_pink");
	public static final Item CORAL_ESSENCE_PURPLE = new BeastsItem("coral_essence_purple");
	public static final Item CORAL_ESSENCE_RED = new BeastsItem("coral_essence_red");
	public static final Item CORAL_ESSENCE_YELLOW = new BeastsItem("coral_essence_yellow"); 
	public static final Item COCONUT_BOWl = new BeastsItem("coconut_bowl"); 
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
	public static final ItemFood BARNACLE_TONGUE = new BeastsFood("barnacle_tongue", 2, 0.1F); 
	public static final ItemFood COOKED_BARNACLE_TONGUE = new BeastsFood("cooked_barnacle_tongue", 5, 0.6F); 
	public static final ItemFood SHRIMP = new BeastsFood("shrimp", 2, 0.2F); 
	public static final ItemFood COOKED_SHRIMP = new BeastsFood("cooked_shrimp", 6, 0.5F);
	public static final ItemFood RAW_KEBAB = new BeastsFood("raw_kebab", 4, 0.3F);
	public static final ItemFood COOKED_KEBAB = new BeastsFood("cooked_kebab", 8, 0.7F); 
	public static final ItemFood EEL_CHOP = new BeastsFood("eel_chop", 3, 0.3F); 
	public static final ItemFood COOKED_EEL_CHOP = new BeastsFood("cooked_eel_chop", 8, 0.5F); 






}
