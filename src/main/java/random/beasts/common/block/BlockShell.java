package random.beasts.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockShell extends BeastsBlock
{
	public BlockShell(String name)
	{
		super(Material.ROCK, name);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.STONE);
		this.isToolEffective("pickaxe", getDefaultState());
	}
}
