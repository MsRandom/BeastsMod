package rando.beasts.common.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import rando.beasts.common.item.BeastsFood;
import rando.beasts.common.item.BeastsItem;
import rando.beasts.common.item.ItemDiamondCarrot;

import java.util.ArrayList;
import java.util.List;

public class BeastsItems {
	
	public static final List<Item> LIST = new ArrayList<>();
	
	public static final Item LEAFY_BONE = new BeastsItem("leafy_bone");
	public static final Item CARROT_COIN = new BeastsItem("carrot_coin");
	public static final ItemFood CRAB_LEG = new BeastsFood("crab_leg", 2, 0.1F);
	public static final ItemFood COOKED_CRAB_LEG = new BeastsFood("cooked_crab_leg", 5, 0.6F);
	public static final ItemFood COCONUT = new BeastsFood("coconut", 2, 0.4F);
	public static final ItemSword DIAMOND_CARROT = new ItemDiamondCarrot();
}
