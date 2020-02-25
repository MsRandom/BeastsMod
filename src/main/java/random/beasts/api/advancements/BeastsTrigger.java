package random.beasts.api.advancements;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.common.BeastsMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeastsTrigger implements ICriterionTrigger<BeastsTrigger.Instance> {
    private final ResourceLocation name;
    private final Map<PlayerAdvancements, Listeners> listeners = new HashMap<>();

    public BeastsTrigger(String parString) {
        name = new ResourceLocation(BeastsMod.MOD_ID, parString);
        BeastsRegistries.ADVANCEMENTS.add(this);
    }

    @Override
    public ResourceLocation getId() {
        return name;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<BeastsTrigger.Instance> listener) {
        Listeners listeners = this.listeners.get(playerAdvancementsIn);

        if (listeners == null) {
            listeners = new Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, listeners);
        }

        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<BeastsTrigger.Instance> listener) {
        Listeners tameanimaltrigger$listeners = listeners.get(playerAdvancementsIn);

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
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new Instance(getId());
    }

    public void trigger(ServerPlayerEntity parPlayer) {
        Listeners listeners = this.listeners.get(parPlayer.getAdvancements());
        if (listeners != null) listeners.triggerAll(parPlayer);
    }

    public static class Instance extends CriterionInstance {
        public Instance(ResourceLocation parRL) {
            super(parRL);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<BeastsTrigger.Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return listeners.isEmpty();
        }

        public void add(Listener<BeastsTrigger.Instance> listener) {
            listeners.add(listener);
        }

        public void remove(Listener<BeastsTrigger.Instance> listener) {
            listeners.remove(listener);
        }

        public void triggerAll(ServerPlayerEntity player) {
            for (Listener<BeastsTrigger.Instance> listener : listeners) trigger(listener);
        }

        public void trigger(Listener<BeastsTrigger.Instance> listener) {
            listener.grantCriterion(playerAdvancements);
        }
    }
}
