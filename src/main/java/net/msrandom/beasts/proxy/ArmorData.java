package net.msrandom.beasts.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.client.model.ModelHermitHelm;
import net.msrandom.beasts.client.model.ModelPufferArmor;
import net.msrandom.beasts.client.model.ModelSpartapodArmor;
import net.msrandom.beasts.common.init.BeastsItems;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ArmorData {
    public static final ArmorMap<ModelBiped> MODELS = new ArmorMap<>();
    public static final ArmorMap<String> TEXTURES = new ArmorMap<>();

    static {
        //this is where armor models and textures are defined
        MODELS.set(EntityEquipmentSlot.HEAD, BeastsItems.SPARTAPOD_HELMET, new ModelSpartapodArmor());
        MODELS.set(EntityEquipmentSlot.HEAD, BeastsItems.HERMIT_HELM, new ModelHermitHelm());

        ModelBiped pufferModel = new ModelPufferArmor();

        MODELS.set(EntityEquipmentSlot.FEET, BeastsItems.PUFFER_BOOTS, pufferModel);
        MODELS.set(EntityEquipmentSlot.LEGS, BeastsItems.PUFFER_LEGS, pufferModel);
        MODELS.set(EntityEquipmentSlot.CHEST, BeastsItems.PUFFER_CHEST, pufferModel);
        MODELS.set(EntityEquipmentSlot.HEAD, BeastsItems.PUFFER_HELMET, pufferModel);

        TEXTURES.set(EntityEquipmentSlot.HEAD, BeastsItems.HERMIT_HELM, "hermit_helm");

        TEXTURES.set(EntityEquipmentSlot.FEET, BeastsItems.PUFFER_BOOTS, "puffer_boots");
        TEXTURES.set(EntityEquipmentSlot.LEGS, BeastsItems.PUFFER_LEGS, "puffer_legs");
        TEXTURES.set(EntityEquipmentSlot.CHEST, BeastsItems.PUFFER_CHEST, "puffer_chest");
        TEXTURES.set(EntityEquipmentSlot.HEAD, BeastsItems.PUFFER_HELMET, "puffer_helmet");
    }

    public static class ArmorMap<T> extends HashMap<EntityEquipmentSlot, Map<Item, T>> {
        public T getFrom(Item item, EntityEquipmentSlot slot) {
            return computeIfAbsent(slot, k -> new HashMap<>()).get(item);
        }

        private void set(EntityEquipmentSlot slot, Item item, T object) {
            computeIfAbsent(slot, k -> new HashMap<>()).put(item, object);
        }
    }
}
