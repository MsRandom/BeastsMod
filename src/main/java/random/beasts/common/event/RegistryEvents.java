package random.beasts.common.event;

import net.minecraft.block.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.block.BeastsSlab;
import random.beasts.common.block.BlockAnemoneMouth;
import random.beasts.common.init.*;
import random.beasts.common.item.IHandleMeta;
import random.beasts.common.main.BeastsReference;

import java.util.Objects;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class RegistryEvents {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BeastsBlocks.LIST.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(BeastsItems.LIST.toArray(new Item[0]));
        FurnaceRecipes.instance().addSmelting(BeastsItems.CRAB_LEG, new ItemStack(BeastsItems.COOKED_CRAB_LEG), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.EEL_CHOP, new ItemStack(BeastsItems.COOKED_EEL_CHOP), 0.50f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.SCALLOP_TONGUE, new ItemStack(BeastsItems.COOKED_SCALLOP_TONGUE), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.SHRIMP, new ItemStack(BeastsItems.COOKED_SHRIMP), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.RAW_KEBAB, new ItemStack(BeastsItems.COOKED_KEBAB), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.DAGGERFISH, new ItemStack(BeastsItems.COOKED_DAGGERFISH), 0.50f);
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(BeastsBiomes.LIST.toArray(new Biome[0]));
        BeastsBiomes.addTypes(BeastsBiomes.DRIED_REEF, BiomeManager.BiomeType.WARM, 10, BEACH, HOT, DRY, SANDY);
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
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().registerAll(BeastsRecipes.LIST.toArray(new IRecipe[0]));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        //todo for some reason this gets called on servers, and of course crashes
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
        ModelLoader.setCustomStateMapper(BeastsBlocks.JELLY_WOOD_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(BeastsBlocks.JELLY_WOOD_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(BeastsBlocks.PALM_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(BeastsBlocks.PALM_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(BeastsBlocks.PALM_LEAVES, new StateMap.Builder().ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(BeastsBlocks.JELLY_LEAVES, new StateMap.Builder().ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(BeastsBlocks.ANEMONE_MOUTH, new StateMap.Builder().ignore(BlockAnemoneMouth.FED).build());
        ModelLoader.setCustomStateMapper(BeastsBlocks.JELLY_WOOD_SLAB.full, new StateMap.Builder().ignore(BlockSlab.HALF, BeastsSlab.VARIANT).build());
        ModelLoader.setCustomStateMapper(BeastsBlocks.PALM_SLAB.full, new StateMap.Builder().ignore(BlockSlab.HALF, BeastsSlab.VARIANT).build());
    }
}
