package net.msrandom.beasts.common.init;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.msrandom.beasts.api.advancements.BeastsTrigger;
import net.msrandom.beasts.api.main.BeastsRegistries;

public class BeastsTriggers {

    //public static final BeastsTrigger DISCOVER_PUFFERFISH_DOG = new BeastsTrigger("pufferfish_discover");
    public static final BeastsTrigger HAMMERTIME = new BeastsTrigger("hammertime");

    public static void init() {
        for (ICriterionTrigger<? extends ICriterionInstance> trigger : BeastsRegistries.ADVANCEMENTS.get())
            CriteriaTriggers.register(trigger);
    }
}
