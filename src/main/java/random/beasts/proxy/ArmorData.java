package random.beasts.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import random.beasts.client.model.ModelHermitHelm;
import random.beasts.client.model.ModelPufferArmor;
import random.beasts.client.model.ModelSpartapodArmor;
import random.beasts.common.init.BeastsItems;

class ArmorData {
    static final Map<EntityEquipmentSlot, Map<Item, ModelBiped>> MODELS = new HashMap<>();
    static final Map<EntityEquipmentSlot, Map<Item, String>> TEXTURES = new HashMap<>();

    static {
        MODELS.put(EntityEquipmentSlot.FEET, new HashMap<>());
        MODELS.put(EntityEquipmentSlot.LEGS, new HashMap<>());
        MODELS.put(EntityEquipmentSlot.CHEST, new HashMap<>());
        MODELS.put(EntityEquipmentSlot.HEAD, new HashMap<>());
        TEXTURES.put(EntityEquipmentSlot.FEET, new HashMap<>());
        TEXTURES.put(EntityEquipmentSlot.LEGS, new HashMap<>());
        TEXTURES.put(EntityEquipmentSlot.CHEST, new HashMap<>());
        TEXTURES.put(EntityEquipmentSlot.HEAD, new HashMap<>());

        //Setup all armor models and textures here

        MODELS.get(EntityEquipmentSlot.HEAD).put(BeastsItems.SPARTAPOD_HELMET, new ModelSpartapodArmor());
        MODELS.get(EntityEquipmentSlot.HEAD).put(BeastsItems.HERMIT_HELM, new ModelHermitHelm());

        ModelBiped pufferModel = new ModelPufferArmor();

        MODELS.get(EntityEquipmentSlot.FEET).put(BeastsItems.PUFFER_BOOTS, pufferModel);
        MODELS.get(EntityEquipmentSlot.LEGS).put(BeastsItems.PUFFER_LEGS, pufferModel);
        MODELS.get(EntityEquipmentSlot.CHEST).put(BeastsItems.PUFFER_CHEST, pufferModel);
        MODELS.get(EntityEquipmentSlot.HEAD).put(BeastsItems.PUFFER_HELMET, pufferModel);

        TEXTURES.get(EntityEquipmentSlot.HEAD).put(BeastsItems.PUFFER_BOOTS, "puffer_boots");
        TEXTURES.get(EntityEquipmentSlot.HEAD).put(BeastsItems.PUFFER_LEGS, "puffer_legs");
        TEXTURES.get(EntityEquipmentSlot.HEAD).put(BeastsItems.PUFFER_CHEST, "puffer_chest");
        TEXTURES.get(EntityEquipmentSlot.HEAD).put(BeastsItems.PUFFER_HELMET, "puffer_helmet");
    }
}
