package random.beasts.common.world.types;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import random.beasts.common.init.BeastsBiomes;

public class WorldTypeBeasts extends WorldType {
	//why is this a thing?
	public WorldTypeBeasts(){
		super("beasts");
	}

	@Override
	public BiomeProvider getBiomeProvider(World world) {
		return new BiomeProviderSingle(BeastsBiomes.DRIED_REEF);
	}
}
