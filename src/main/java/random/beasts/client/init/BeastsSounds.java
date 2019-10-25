package random.beasts.client.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import random.beasts.common.main.BeastsReference;

public class BeastsSounds {

    public static final List<SoundEvent> LIST = new ArrayList<>();

    public static final SoundEvent BRANCHIE_SCREAM = create("entity.branchie.scream");
    public static final SoundEvent LANDWHALE_AMBIENT = create("entity.landwhale.ambient");
    public static final SoundEvent PUFFERFISH_BLOW_OUT = create("entity.pufferfish.blowout");
    public static final SoundEvent PUFFERFISH_BLOW_UP = create("entity.pufferfish.blowup");
    public static final SoundEvent VILE_EEL_AMBIENT = create("entity.vileeel.ambient");

    private static SoundEvent create(String s) {
        ResourceLocation name = new ResourceLocation(BeastsReference.ID, s);
        SoundEvent sound = new SoundEvent(name);
        sound.setRegistryName(name);
        LIST.add(sound);
        return sound;
    }
}
