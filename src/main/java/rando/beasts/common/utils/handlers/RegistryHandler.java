package rando.beasts.common.utils.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import rando.beasts.client.init.BeastsSounds;
import rando.beasts.common.init.BeastsBiomes;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsEntities;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.item.IHandleMeta;
import rando.beasts.common.utils.BeastsReference;

import java.util.Objects;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class RegistryHandler {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BeastsBlocks.LIST.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(BeastsItems.LIST.toArray(new Item[0]));
        FurnaceRecipes.instance().addSmelting(BeastsItems.CRAB_LEG, new ItemStack(BeastsItems.COOKED_CRAB_LEG), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.EEL_CHOP, new ItemStack(BeastsItems.COOKED_EEL_CHOP), 0.50f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.BARNACLE_TONGUE, new ItemStack(BeastsItems.COOKED_BARNACLE_TONGUE), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.SHRIMP, new ItemStack(BeastsItems.COOKED_SHRIMP), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.RAW_KEBAB, new ItemStack(BeastsItems.COOKED_KEBAB), 0.35f);
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(BeastsBiomes.LIST.toArray(new Biome[0]));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BeastsBiomes.DRIED_REEF, 10));
        BiomeManager.addSpawnBiome(BeastsBiomes.DRIED_REEF);
        BiomeManager.addStrongholdBiome(BeastsBiomes.DRIED_REEF);
        BiomeDictionary.addTypes(BeastsBiomes.DRIED_REEF, BiomeDictionary.Type.BEACH, HOT, DRY, SANDY );
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(BeastsSounds.LIST.toArray(new SoundEvent[0]));
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(BeastsEntities.LIST.toArray(new EntityEntry[0]));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        for (Item item : BeastsItems.LIST) {
            if(item instanceof IHandleMeta){
                IHandleMeta metaItem = (IHandleMeta)item;
                for (int i = 0; i < metaItem.getDamage(); i++) ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(BeastsReference.ID, Objects.requireNonNull(item.getRegistryName()).getResourcePath() + "_" + metaItem.handleMeta(i)), "inventory"));
            }
            else ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
        }
        for (Block block : BeastsBlocks.LIST) {
            Item item = Item.getItemFromBlock(block);
            if(item != Items.AIR) ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(block.getRegistryName()), "inventory"));
        }
    }
}
