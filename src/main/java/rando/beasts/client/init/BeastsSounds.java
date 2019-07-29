package rando.beasts.client.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import rando.beasts.common.utils.BeastsReference;

import java.util.ArrayList;
import java.util.List;

public class BeastsSounds {

    public static final List<SoundEvent> LIST = new ArrayList<>();

    public static final SoundEvent MONKEY_HURT = create("monkey.hurt");
    public static final SoundEvent MONKEY_IDLE = create("monkey.idle");

    private static SoundEvent create(String s) {
        ResourceLocation name = new ResourceLocation(BeastsReference.ID + ":" + s);
        SoundEvent sound = new SoundEvent(name);
        sound.setRegistryName(name);
        LIST.add(sound);
        return sound;
    }
}
