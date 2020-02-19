package random.beasts.api.world.biome.underground;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
        byte[] blockBiomeArray = new byte[8];
    }

    public static class UndergroundBiomeStorage implements Capability.IStorage<UndergroundBiomes> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, EnumFacing side) {
            return new NBTTagByteArray(instance.blockBiomeArray);
        }

        @Override
        public void readNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, EnumFacing side, NBTBase nbt) {
            instance.blockBiomeArray = ((NBTTagByteArray) nbt).getByteArray();
        }
    }
}
