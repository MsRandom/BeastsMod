package rando.beasts.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import rando.beasts.common.advancements.BeastsTrigger;

public class BeastsTriggers {

    public static final List<ICriterionTrigger<? extends ICriterionInstance>> LIST = new ArrayList<>();
    public static final BeastsTrigger DISCOVER_PUFFERFISH_DOG = new BeastsTrigger("pufferfish_discover");
}
