package random.beasts.common.event;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import random.beasts.api.configuration.BeastsConfig;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.BeastsMod;
import random.beasts.common.init.*;

import static net.minecraftforge.common.BiomeDictionary.Type.*;
import static random.beasts.proxy.ClientProxy.TRIMOLA_ATTACK;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BeastsRegistries.BLOCKS.get(BeastsBlocks.COCONUT).toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        BeastsEntities.registerEggs();
        event.getRegistry().registerAll(BeastsRegistries.ITEMS.get(BeastsItems.ICON).toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(BeastsRegistries.BIOMES.get(BeastsBiomes.DRIED_REEF).toArray(new Biome[0]));
        BeastsBiomes.addTypes(BeastsBiomes.DRIED_REEF, BiomeManager.BiomeType.WARM, BeastsConfig.reefWeight, BEACH, HOT, DRY, SANDY);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(BeastsSounds.LIST.toArray(new SoundEvent[0]));
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(BeastsRegistries.ENTITIES.get().toArray(new EntityType[0]));
        BeastsEntities.SPAWNS.forEach((type, spawns) -> {
            for (BeastsEntities.SpawnEntry spawn : spawns) {
                Biome.SpawnListEntry entry = new Biome.SpawnListEntry(type, spawn.weight, spawn.min, spawn.max);
                for (Biome biome : spawn.biomes) {
                    biome.getSpawns(type.getClassification()).add(entry);
                }
            }
        });
    }

    @SubscribeEvent
    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(BeastsRegistries.TILE_ENTITIES.get(BeastsTileEntities.COCONUT).toArray(new TileEntityType[0]));
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(BeastsRegistries.CONTAINERS.get(BeastsContainers.LANDWHALE).toArray(new ContainerType[0]));
    }

    /*@SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().registerAll(BeastsRecipes.LIST.toArray(new IRecipe[0]));
    }*/

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        BeastsMod.proxy.registerEventRenders();
    }

    @SubscribeEvent
    public static void registerClient(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(TRIMOLA_ATTACK);
    }
}
