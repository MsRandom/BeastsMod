package rando.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.utils.BeastsUtil;

public class BeastsBlock extends Block{

	public BeastsBlock(Material materialIn, String name) {
		this(materialIn, materialIn.getMaterialMapColor(), name);
	}

	public BeastsBlock(Material blockMaterialIn, MapColor blockMapColorIn, String name) {
		super(blockMaterialIn, blockMapColorIn);
		BeastsUtil.addToRegistry(this, name, true);
	}
}
