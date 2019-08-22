package rando.beasts.common.item;

import net.minecraft.item.Item;
import rando.beasts.common.utils.BeastsUtil;

public class BeastsItem extends Item{
	
	public BeastsItem(String name) {
		BeastsUtil.addToRegistry(this, name, true);
	}
}
