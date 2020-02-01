package random.beasts.api.world.biome.underground;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import random.beasts.api.main.BeastsReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class UndergroundGenerationCapabilities implements ICapabilityProvider {

    @CapabilityInject(UndergroundBiome.class)
    public static final Capability<UndergroundBiomes> CAPABILITY = null;
    public static final ResourceLocation KEY = new ResourceLocation(BeastsReference.ID, "underground_biomes");

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability != null && hasCapability(capability, facing) ? CAPABILITY.cast(CAPABILITY.getDefaultInstance()) : null;
    }

    public static class UndergroundBiomes {
        byte[][] blockBiomeArray = new byte[16][255];
    }

    static class UndergroundBiomeStorage implements Capability.IStorage<UndergroundBiomes> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, EnumFacing side) {
            NBTTagList list = new NBTTagList();
            for (int i = 0; i < 16; ++i) {
                list.set(i, new NBTTagByteArray(instance.blockBiomeArray[i]));
            }
            return list;
        }

        @Override
        public void readNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, EnumFacing side, NBTBase nbt) {
            NBTTagList list = (NBTTagList) nbt;
            for (int i = 0; i < 16; ++i) {
                instance.blockBiomeArray[i] = ((NBTTagByteArray) list.get(i)).getByteArray();
            }
        }
    }
}