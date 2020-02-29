package random.beasts.common.world.types;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import random.beasts.common.init.BeastsBiomes;

import java.util.function.LongFunction;

public class WorldTypeBeasts extends WorldType {
    //why is this a thing?
    public WorldTypeBeasts() {
        super("beasts");
    }

    @Override
    public <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> getBiomeLayer(IAreaFactory<T> parentLayer, OverworldGenSettings chunkSettings, LongFunction<C> contextFactory) {
        IC0Transformer transformer = (context, value) -> Registry.BIOME.getId(BeastsBiomes.DRIED_REEF);
        return transformer.apply(contextFactory.apply(200L), parentLayer);
    }
}
