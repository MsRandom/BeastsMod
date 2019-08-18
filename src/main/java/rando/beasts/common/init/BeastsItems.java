package rando.beasts.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import rando.beasts.common.item.BeastsFood;
import rando.beasts.common.item.BeastsItem;
import rando.beasts.common.item.ItemDiamondCarrot;

public class BeastsItems {
	
	public static final List<Item> LIST = new ArrayList<>();
	
	public static final Item ICON = new BeastsItem("icon");//needs to render twice as big when in game/in a gui
	public static final Item LEAFY_BONE = new BeastsItem("leafy_bone");
	public static final Item CARROT_COIN = new BeastsItem("carrot_coin");
	public static final Item COCONADE = new BeastsItem("coconade");//needs crafting recipe
	public static final Item CORAL_ESSENCE_BLUE = new BeastsItem("coral_essence_blue");//needs crafting recipe/drop from coral
	public static final Item CORAL_ESSENCE_PINK = new BeastsItem("coral_essence_pink");//needs crafting recipe/drop from coral
	public static final Item CORAL_ESSENCE_PURPLE = new BeastsItem("coral_essence_purple");//needs crafting recipe/drop from coral
	public static final Item CORAL_ESSENCE_RED = new BeastsItem("coral_essence_red");//needs crafting recipe/drop from coral 
	public static final Item CORAL_ESSENCE_YELLOW = new BeastsItem("coral_essence_yellow"); //needs crafting recipe/drop from coral
	public static final ItemSword DIAMOND_CARROT = new ItemDiamondCarrot();
	public static final ItemFood CRAB_LEG = new BeastsFood("crab_leg", 2, 0.1F);
	public static final ItemFood COOKED_CRAB_LEG = new BeastsFood("cooked_crab_leg", 6, 0.6F);
	public static final ItemFood COCONUT = new BeastsFood("coconut", 2, 0.4F);
	public static final ItemFood BARNACLE_TONGUE = new BeastsFood("barnacle_tongue", 2, 0.1F); //needs to give 5 seconds of regen and 10 seconds of weakness when eaten
	public static final ItemFood COOKED_BARNACLE_TONGUE = new BeastsFood("cooked_barnacle_tongue", 5, 0.6F); //needs to give 10 seconds of regen and 5 seconds of weakness when eaten
	public static final ItemFood SHRIMP = new BeastsFood("shrimp", 2, 0.2F); //needs to be added as a drop, has a chance to give hunger
	public static final ItemFood COOKED_SHRIMP = new BeastsFood("cooked_shrimp", 6, 0.5F); //needs smelting recipe
	public static final ItemFood RAW_KEBAB = new BeastsFood("raw_kebab", 4, 0.3F); //needs crafting recipe
	public static final ItemFood COOKED_KEBAB = new BeastsFood("cooked_kebab", 7, 0.6F); //needs smelting recipe
	public static final ItemFood EEL_CHOP = new BeastsFood("eel_chop", 3, 0.3F); //needs drop
	public static final ItemFood COOKED_EEL_CHOP = new BeastsFood("cooked_eel_chop", 8, 0.5F); //needs smelting recipe
	



}
