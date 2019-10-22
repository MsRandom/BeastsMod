package rando.beasts.common.advancements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import rando.beasts.common.init.BeastsTriggers;
import rando.beasts.common.main.BeastsReference;

public class BeastsTrigger implements ICriterionTrigger<BeastsTrigger.Instance> {
    private final ResourceLocation name;
    private final Map<PlayerAdvancements, Listeners> listeners = new HashMap<>();

    public BeastsTrigger(String parString) {
        name = new ResourceLocation(BeastsReference.ID, parString);
        BeastsTriggers.LIST.add(this);
    }

    @Override
    public ResourceLocation getId()
    {
        return name;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener listener) {
        BeastsTrigger.Listeners listeners = this.listeners.get(playerAdvancementsIn);

        if (listeners == null) {
            listeners = new BeastsTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, listeners);
        }

        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener listener) {
        BeastsTrigger.Listeners tameanimaltrigger$listeners = (Listeners) listeners.get(playerAdvancementsIn);

        if (tameanimaltrigger$listeners != null) {
            tameanimaltrigger$listeners.remove(listener);
            if (tameanimaltrigger$listeners.isEmpty()) listeners.remove(playerAdvancementsIn);
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        listeners.remove(playerAdvancementsIn);
    }

    @Override
    public BeastsTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new BeastsTrigger.Instance(getId());
    }

    public void trigger(EntityPlayerMP parPlayer) {
        BeastsTrigger.Listeners listeners = this.listeners.get(parPlayer.getAdvancements());
        if (listeners != null) listeners.trigger(parPlayer);
    }

    public static class Instance extends AbstractCriterionInstance {
        public Instance(ResourceLocation parRL)
        {
            super(parRL);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener listener)
        {
            listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener listener)
        {
            listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player) {
            ArrayList<Listener> list = null;
            for (ICriterionTrigger.Listener listener : listeners) {
                if (list == null) list = new ArrayList<>();
                list.add(listener);
            }

            if (list != null) for (ICriterionTrigger.Listener listener1 : list) listener1.grantCriterion(playerAdvancements);
        }
    }
}
