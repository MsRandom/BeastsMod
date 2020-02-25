package random.beasts.api.world.biome.underground;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Consumer;

@SuppressWarnings("ConstantConditions")
public class UndergroundGenerationCapabilities implements ICapabilityProvider {
    @CapabilityInject(UndergroundBiomes.class)
    public static final Capability<UndergroundBiomes> CAPABILITY = null;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return CAPABILITY == capability ? LazyOptional.of(capability::getDefaultInstance) : LazyOptional.empty();
    }

    public static class UndergroundBiomes {
        BiomeList biomeList = new BiomeList();
    }

    public static class UndergroundBiomeStorage implements Capability.IStorage<UndergroundBiomes> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, Direction side) {
            ListNBT nbt = new ListNBT();
            Consumer<UndergroundBiomeBounds> consumer = bounds -> {
                CompoundNBT compound = new CompoundNBT();
                compound.putString("biome", bounds.biome.getRegistryName().toString());
                compound.putInt("minX", bounds.minX);
                compound.putByte("minY", bounds.minY);
                compound.putInt("minZ", bounds.minZ);
                compound.putInt("maxX", bounds.maxX);
                compound.putByte("maxY", bounds.maxY);
                compound.putInt("maxZ", bounds.maxZ);
                nbt.add(compound);
            };
            instance.biomeList.forEach(consumer);
            return nbt;
        }

        @Override
        public void readNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, Direction side, INBT nbt) {
            ListNBT list = (ListNBT) nbt;
            Consumer<INBT> consumer = tag -> {
                if (tag instanceof CompoundNBT) {
                    CompoundNBT compound = (CompoundNBT) tag;
                    UndergroundBiomeBounds bounds = new UndergroundBiomeBounds((UndergroundBiome) ForgeRegistries.BIOMES.getValue(new ResourceLocation(compound.getString("biome"))), compound.getInt("minX"), compound.getByte("minY"), compound.getInt("minZ"), compound.getInt("maxX"), compound.getByte("maxY"), compound.getInt("maxZ"));
                    instance.biomeList.add(bounds);
                }
            };
            list.forEach(consumer);
        }
    }

    public static final class BiomeList extends ArrayList<UndergroundBiomeBounds> {
        @Override
        public boolean add(UndergroundBiomeBounds bounds) {
            for (int x = bounds.minX; x < bounds.maxX; x++) {
                for (int z = bounds.minZ; z < bounds.maxZ; z++) {
                    ChunkPos pos = new ChunkPos(x, z);
                    UndergroundBiome[] biomes;
                    if (UndergroundBiome.GENERATED.containsKey(pos)) biomes = UndergroundBiome.GENERATED.get(pos);
                    else {
                        biomes = new UndergroundBiome[8];
                        UndergroundBiome.GENERATED.put(pos, biomes);
                    }
                    for (int y = bounds.minY; y < bounds.maxY; y++) biomes[y] = bounds.biome;
                }
            }
            return super.add(bounds);
        }
    }
}
