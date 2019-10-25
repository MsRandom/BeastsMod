package random.beasts.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import random.beasts.common.advancements.BeastsTrigger;

public class BeastsTriggers {

    public static final List<ICriterionTrigger<? extends ICriterionInstance>> LIST = new ArrayList<>();
    public static final BeastsTrigger DISCOVER_PUFFERFISH_DOG = new BeastsTrigger("pufferfish_discover");

    public static void init() {
        for(ICriterionTrigger<? extends ICriterionInstance> trigger : LIST) CriteriaTriggers.register(trigger);
    }
}
