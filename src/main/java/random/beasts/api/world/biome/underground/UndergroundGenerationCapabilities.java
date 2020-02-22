package random.beasts.api.world.biome.underground;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Consumer;

@SuppressWarnings("ConstantConditions")
public class UndergroundGenerationCapabilities implements ICapabilityProvider {
    @CapabilityInject(UndergroundBiomes.class)
    public static final Capability<UndergroundBiomes> CAPABILITY = null;

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? CAPABILITY.cast(CAPABILITY.getDefaultInstance()) : null;
    }

    public static class UndergroundBiomes {
        BiomeList biomeList = new BiomeList();
    }

    public static class UndergroundBiomeStorage implements Capability.IStorage<UndergroundBiomes> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, EnumFacing side) {
            NBTTagList nbt = new NBTTagList();
            Consumer<UndergroundBiomeBounds> consumer = bounds -> {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("biome", (byte) (Biome.getIdForBiome(bounds.biome) & 255));
                compound.setInteger("minX", bounds.minX);
                compound.setByte("minY", bounds.minY);
                compound.setInteger("minZ", bounds.minZ);
                compound.setInteger("maxX", bounds.maxX);
                compound.setByte("maxY", bounds.maxY);
                compound.setInteger("maxZ", bounds.maxZ);
                nbt.appendTag(compound);
            };
            instance.biomeList.forEach(consumer);
            return nbt;
        }

        @Override
        public void readNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, EnumFacing side, NBTBase nbt) {
            NBTTagList list = (NBTTagList) nbt;
            Consumer<NBTBase> consumer = tag -> {
                if (tag instanceof NBTTagCompound) {
                    NBTTagCompound compound = (NBTTagCompound) tag;
                    UndergroundBiomeBounds bounds = new UndergroundBiomeBounds((UndergroundBiome) Biome.getBiome(compound.getByte("biome") & 255), compound.getInteger("minX"), compound.getByte("minY"), compound.getInteger("minZ"), compound.getInteger("maxX"), compound.getByte("maxY"), compound.getInteger("maxZ"));
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
