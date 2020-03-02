package random.beasts.common.init;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import random.beasts.api.item.*;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.block.CoralColor;
import random.beasts.common.block.OreType;
import random.beasts.common.item.*;

import java.util.HashMap;
import java.util.Map;

public class BeastsItems {
    public static final Item ICON = new BeastsItem("icon", false);
    public static final Item BEASTS_PAINTING = new ItemBeastsPainting();
    public static final Item GLOW_ROOT = new ItemGlowRoot();
    public static final Item LEAFY_BONE = new BeastsItem("leafy_bone");
    public static final Item CARROT_COIN = new BeastsItem("carrot_coin");
    public static final Item COCONADE = new ItemCoconade("coconade");
    public static final Item COCONUT_BOWL = new BeastsItem(new Item.Properties().group(BeastsUtils.getRegistryTab()).maxStackSize(2), "coconut_bowl");
    public static final Item FISHSTAR = new BeastsItem("fishstar");
    public static final Item ATHAPOD_CHITIN = new BeastsItem("athapod_chitin");
    public static final Item SPARTAPOD_CHITIN = new BeastsItem("spartapod_chitin");
    public static final Item SPARTAPOD_CREST = new BeastsItem("spartapod_crest");
    public static final Item ICE_CRAB_CHITIN = new BeastsItem("ice_crab_chitin");
    public static final Item WORM_TOOTH = new BeastsItem("worm_tooth");
    public static final Item PUFFER_SCALE = new BeastsItem("puffer_scale");
    public static final Item HERMIT_HELM = new BeastsArmor(new Item.Properties().group(BeastsUtils.getRegistryTab()).defaultMaxDamage(0), "hermit_helm", ArmorMaterial.IRON, EquipmentSlotType.HEAD);
    public static final Item MEAT_SCRAPES = new BeastsItem("meat_scrapes");
    public static final ShieldItem SHELL_SHIELD = new ItemShellShield();
    public static final Item DAGGERFISH = new BeastsFood("daggerfish", 2, 0.1F);
    public static final Item COCONUT_MUSHROOM = new BeastsCoconutBowl("coconut_mushroom", 6, 0.7F);
    public static final Item COCONUT_RABBIT_STEW = new BeastsCoconutBowl("coconut_rabbit_stew", 8, 0.7F);
    public static final Item REEF_MIXTURE = new BeastsCoconutBowl("reef_mixture", 10, 0.9F, new EffectInstance(Effects.REGENERATION, 100, 0), new EffectInstance(Effects.WEAKNESS, 100, 0));
    public static final Item CRAB_LEG = new BeastsFood("crab_leg", 2, 0.1F);
    public static final Item COOKED_CRAB_LEG = new BeastsFood("cooked_crab_leg", 6, 0.6F);
    public static final Item COCONUT = new ItemCoconut();
    public static final Item SHRIMP = new BeastsFood("shrimp", new Item.Properties().group(BeastsUtils.getRegistryTab()).food(new Food.Builder().hunger(2).saturation(0.2f).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3f).build()));
    public static final Item COOKED_SHRIMP = new BeastsFood("cooked_shrimp", 6, 0.5F);
    public static final Item RAW_KEBAB = new BeastsFood("raw_kebab", new Item.Properties().group(BeastsUtils.getRegistryTab()).food(new Food.Builder().hunger(4).saturation(0.3f).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3f).build()));
    public static final Item SCALLOP_TONGUE = new ItemScallopTongue(false, 2, 0.1F, 200);
    public static final Item COOKED_SCALLOP_TONGUE = new ItemScallopTongue(true, 5, 0.6F, 100);
    public static final Item COOKED_KEBAB = new BeastsFood("cooked_kebab", 8, 0.7F);
    public static final Item EEL_CHOP = new BeastsFood("eel_chop", 3, 0.3F);
    public static final Item COOKED_EEL_CHOP = new BeastsFood("cooked_eel_chop", 8, 0.5F);
    public static final Item FAIRY_BREAD = new BeastsFood("fairy_bread", 2, 0.2F) {
        @Override
        protected void onFoodEaten(ItemStack stack, World worldIn, PlayerEntity player) {
            super.onFoodEaten(stack, worldIn, player);
            player.heal(5.0F);
        }
    };
    public static final Item BUTTERFLYFISH_WING = new BeastsFood("butterflyfish_wing", 0, 0.0F) {
        @Override
        protected void onFoodEaten(ItemStack stack, World worldIn, PlayerEntity player) {
            super.onFoodEaten(stack, worldIn, player);
            player.heal(3.0F);
        }
    };
    public static final Item COCONUT_JUICE = new BeastsCoconutBowl("coconut_juice", 4, 0.5F);
    public static final ArmorItem PUFFER_HELMET = new ItemPufferArmor("helmet", EquipmentSlotType.HEAD);
    public static final ArmorItem PUFFER_CHEST = new ItemPufferArmor("chest", EquipmentSlotType.CHEST);
    public static final ArmorItem PUFFER_LEGS = new ItemPufferArmor("legs", EquipmentSlotType.LEGS);
    public static final ArmorItem PUFFER_BOOTS = new ItemPufferArmor("boots", EquipmentSlotType.FEET);
    public static final SwordItem DIAMOND_CARROT = new BeastsSword(ItemTier.DIAMOND, "diamond_carrot");
    public static final BeastsToolSet JELLY_TOOLS = new ToolSetJellyWood();
    private static final IItemTier SWORDFISH_MAT = new BeastsItemTier(0, 3, 8.0F, 4.0F, 0);
    public static final SwordItem COOKED_DAGGERFISH = new ItemDaggerfish(SWORDFISH_MAT, "cooked_daggerfish");
    private static final IArmorMaterial SPARTAPOD = new BeastsArmorMaterial("beasts:spartapod", 15, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
    //todo do armor sets like with the tool set with the jelly tools
    public static final ArmorItem SPARTAPOD_HELMET = new BeastsArmor("spartapod_helmet", SPARTAPOD, EquipmentSlotType.HEAD);
    private static final IArmorMaterial SPARTAPODA = new BeastsArmorMaterial("beasts:spartapoda", 15, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
    public static final ArmorItem SPARTAPOD_CHEST = new BeastsArmor("spartapod_chest", SPARTAPODA, EquipmentSlotType.CHEST);
    public static final ArmorItem SPARTAPOD_LEGS = new BeastsArmor("spartapod_legs", SPARTAPODA, EquipmentSlotType.LEGS);
    public static final ArmorItem SPARTAPOD_BOOTS = new BeastsArmor("spartapod_boots", SPARTAPODA, EquipmentSlotType.FEET);

    public static final Map<CoralColor, ItemCoralBlock> CORAL_BLOCKS = new HashMap<>();
    public static final Map<CoralColor, ItemCoralBlock> CORAL_SAPLINGS = new HashMap<>();
    public static final Map<CoralColor, ItemCoralEssence> CORAL_ESSENCE = new HashMap<>();
    public static final Map<OreType, ItemOreBlock> ORE_ITEMS = new HashMap<>();

    static {
        for (CoralColor color : CoralColor.values()) {
            CORAL_BLOCKS.put(color, new ItemCoralBlock(BeastsBlocks.CORAL_BLOCK, color));
            CORAL_SAPLINGS.put(color, new ItemCoralBlock(BeastsBlocks.CORAL_SAPLING, color));
            CORAL_ESSENCE.put(color, new ItemCoralEssence(color));
        }
        for (OreType ore : OreType.values())
            ORE_ITEMS.put(ore, new ItemOreBlock(ore));
    }
}
