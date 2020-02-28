package random.beasts.client.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import random.beasts.common.BeastsMod;

import java.util.ArrayList;
import java.util.List;

public class BeastsSounds {

    public static final List<SoundEvent> LIST = new ArrayList<>();

    public static final SoundEvent ANGLER_QUEEN_BEAM = create("entity.angler_queen_beam");
    public static final SoundEvent ANGLER_QUEEN_BEAM_CHARGE = create("entity.angler_queen_beam_charge");
    public static final SoundEvent BRANCHIE_HURT = create("entity.branchie.hurt");
    public static final SoundEvent BRANCHIE_SCREAM = create("entity.branchie.scream");
    public static final SoundEvent CRAB_ATTACK = create("entity.crab.attack");
    public static final SoundEvent CRAB_HURT = create("entity.crab.hurt");
    public static final SoundEvent CRAB_STEP = create("entity.crab.step");
    public static final SoundEvent GARDEN_EEL_HURT = create("entity.garden_eel.hurt");
    public static final SoundEvent HERMIT_TURTLE_AMBIENT = create("entity.hermit_turtle.ambient");
    public static final SoundEvent HERMIT_TURTLE_HURT = create("entity.hermit_turtle.hurt");
    public static final SoundEvent LANDWHALE_AMBIENT = create("entity.landwhale.ambient");
    public static final SoundEvent LANDWHALE_HURT = create("entity.landwhale.hurt");
    public static final SoundEvent PUFFERFISH_BLOW_OUT = create("entity.pufferfish.blowout");
    public static final SoundEvent PUFFERFISH_BLOW_UP = create("entity.pufferfish.blowup");
    public static final SoundEvent SKEWER_SHRIMP_HURT = create("entity.skewer_shrimp.hurt");
    public static final SoundEvent VILE_EEL_AMBIENT = create("entity.vile_eel.ambient");
    public static final SoundEvent VILE_EEL_HURT = create("entity.vile_eel.hurt");
    public static final SoundEvent SCALLOP_CRACK = create("entity.scallop.crack");

    private static SoundEvent create(String s) {
        ResourceLocation name = new ResourceLocation(BeastsMod.MOD_ID, s);
        SoundEvent sound = new SoundEvent(name).setRegistryName(name);
        LIST.add(sound);
        return sound;
    }
}
