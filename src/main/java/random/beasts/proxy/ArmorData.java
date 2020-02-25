package random.beasts.proxy;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import random.beasts.client.model.ModelHermitHelm;
import random.beasts.client.model.ModelPufferArmor;
import random.beasts.client.model.ModelSpartapodArmor;
import random.beasts.common.init.BeastsItems;

import java.util.HashMap;
import java.util.Map;

class ArmorData {
    static final ModelMap MODELS = new ModelMap();
    static final TextureMap TEXTURES = new TextureMap();

    static {
        //this is where armor models and textures are defined
        MODELS.set(EquipmentSlotType.HEAD, BeastsItems.SPARTAPOD_HELMET, new ModelSpartapodArmor());
        MODELS.set(EquipmentSlotType.HEAD, BeastsItems.HERMIT_HELM, new ModelHermitHelm());

        BipedModel<LivingEntity> pufferModel = new ModelPufferArmor<>();

        MODELS.set(EquipmentSlotType.FEET, BeastsItems.PUFFER_BOOTS, pufferModel);
        MODELS.set(EquipmentSlotType.LEGS, BeastsItems.PUFFER_LEGS, pufferModel);
        MODELS.set(EquipmentSlotType.CHEST, BeastsItems.PUFFER_CHEST, pufferModel);
        MODELS.set(EquipmentSlotType.HEAD, BeastsItems.PUFFER_HELMET, pufferModel);

        TEXTURES.set(EquipmentSlotType.HEAD, BeastsItems.HERMIT_HELM, "hermit_helm");

        TEXTURES.set(EquipmentSlotType.FEET, BeastsItems.PUFFER_BOOTS, "puffer_boots");
        TEXTURES.set(EquipmentSlotType.LEGS, BeastsItems.PUFFER_LEGS, "puffer_legs");
        TEXTURES.set(EquipmentSlotType.CHEST, BeastsItems.PUFFER_CHEST, "puffer_chest");
        TEXTURES.set(EquipmentSlotType.HEAD, BeastsItems.PUFFER_HELMET, "puffer_helmet");
    }

    //unlike 1.12, these were split into 2, since generics can sometimes be pretty limiting in cases like these
    static class TextureMap extends HashMap<EquipmentSlotType, Map<Item, String>> {
        String getFrom(Item item, EquipmentSlotType slot) {
            return computeIfAbsent(slot, k -> new HashMap<>()).get(item);
        }

        private void set(EquipmentSlotType slot, Item item, String object) {
            computeIfAbsent(slot, k -> new HashMap<>()).put(item, object);
        }
    }

    static class ModelMap extends HashMap<EquipmentSlotType, Map<Item, BipedModel<?>>> {
        @SuppressWarnings("unchecked")
        <A extends BipedModel<?>> A getFrom(Item item, EquipmentSlotType slot) {
            return (A) computeIfAbsent(slot, k -> new HashMap<>()).get(item);
        }

        private <A extends BipedModel<?>> void set(EquipmentSlotType slot, Item item, A object) {
            computeIfAbsent(slot, k -> new HashMap<>()).put(item, object);
        }
    }
}
