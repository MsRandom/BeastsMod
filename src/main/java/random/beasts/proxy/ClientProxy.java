package random.beasts.proxy;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import random.beasts.client.gui.GuiLandwhaleInventory;
import random.beasts.client.gui.GuiTrimolaInventory;
import random.beasts.client.renderer.entity.*;
import random.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.item.EntityBeastsPainting;
import random.beasts.common.entity.item.EntityFallingCoconut;
import random.beasts.common.entity.item.EntityThrownCoconut;
import random.beasts.common.entity.monster.*;
import random.beasts.common.entity.passive.*;
import random.beasts.common.entity.projectile.EntityCoconutBomb;
import random.beasts.common.init.BeastsContainers;
import random.beasts.common.tileentity.TileEntityCoconut;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

public class ClientProxy extends CommonProxy {
    public static final KeyBinding TRIMOLA_ATTACK = new KeyBinding("trimola.attack", 19, "key.categories.misc");

    @Override
    public PlayerEntity getPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public boolean isClientSneaking() {
        return Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown();
    }

    @Override
    public boolean isTrimolaAttacking() {
        return TRIMOLA_ATTACK.isKeyDown();
    }

    @Override
    public void clientSetup() {
        super.clientSetup();
        ClientRegistry.registerKeyBinding(TRIMOLA_ATTACK);
    }

    private void registerGuis() {
        ScreenManager.registerFactory(BeastsContainers.LANDWHALE, GuiLandwhaleInventory::new);
        ScreenManager.registerFactory(BeastsContainers.TRIMOLA, GuiTrimolaInventory::new);
    }

    private void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityCoconutCrab.class, RenderCoconutCrab::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPufferfishDog.class, RenderPufferfishDog::new);
        //RenderingRegistry.registerEntityRenderingHandler(EntityRabbitman.class, RenderRabbitman::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySkewerShrimp.class, RenderSkewerShrimp::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGiantGardenEel.class, RenderGiantGardenEel::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityVileEel.class, RenderVileEel::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLandwhale.class, RenderLandwhale::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCoconutBomb.class, RenderCoconutBomb::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCoralBranchie.class, RenderCoralBranchie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityChorusBranchie.class, RenderChorusBranchie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWoodBranchie.class, RenderWoodBranchie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBeastsPainting.class, RenderBeastsPainting::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingCoconut.class, RenderFallingCoconut::new);
        //RenderingRegistry.registerEntityRenderingHandler(EntityWhippingBarnacle.class, RenderWhippingBarnacle::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHermitTurtle.class, RenderHermitTurtle::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityAnemoneCrawler.class, RenderAnemoneCrawler::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLegfish.class, RenderLegfish::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityScallop.class, RenderScallop::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityThrownCoconut.class, RenderThrownCoconut::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTrimola.class, RenderTrimola::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySlimeSlug.class, RenderSlimeSlug::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityButterflyFish.class, RenderButterflyFish::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFireflySquid.class, RenderFireflySquid::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityIsopod.class, RenderIsopod::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGlowShrimp.class, RenderGlowShrimp::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTupala.class, RenderTupala::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnglerPup.class, RenderAnglerPup::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnglerQueen.class, RenderAnglerQueen::new);
		registerTileEntityRenders();
	}

	public void registerEventRenders() {
        this.registerGuis();
        this.registerEntityRenders();
        /*for (Item item : BeastsRegistries.ITEMS.get()) {
            if (item instanceof IHandleMeta) {
                IHandleMeta metaItem = (IHandleMeta) item;
                for (int i = 0; i < metaItem.getDamage(); i++)
                    ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(BeastsMod.MOD_ID, Objects.requireNonNull(item.getRegistryName()).getPath() + "_" + metaItem.handleMeta(i)), "inventory"));
            } else
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
        }
        for (Block block : BeastsRegistries.BLOCKS.get()) {
            Item item = Item.getItemFromBlock(block);
            if (item != Items.AIR)
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(block.getRegistryName()), "inventory"));
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
        ModelLoader.setCustomStateMapper(BeastsBlocks.SHELL_SLAB.full, new StateMap.Builder().ignore(BlockSlab.HALF, BeastsSlab.VARIANT).build());*/
    }

    private void registerTileEntityRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoconut.class, new TileEntityCoconutRenderer());
    }

    @Override
    public <A extends BipedModel<?>> A getArmorModel(Item armorItem, EquipmentSlotType armorSlot) {
        return ArmorData.MODELS.getFrom(armorItem, armorSlot);
    }

    @Override
    public String getArmorTexture(Item armorItem, EquipmentSlotType armorSlot) {
        String texture = ArmorData.TEXTURES.getFrom(armorItem, armorSlot);
        return texture == null ? null : BeastsMod.MOD_ID + ":textures/models/armor/" + texture + ".png";
    }

    private static class SpawnEggLoader implements IResourcePack {
        private static final String NAME = "Beasts Spawn Egg Loader";
        private static final Set<String> NAMESPACES = Sets.newHashSet(BeastsMod.MOD_ID);
        private static final byte[] SPAWN_EGG = ("{\n    \"parent\": \"item/template_spawn_egg\"\n}\n").getBytes();

        @Override
        public InputStream getRootResourceStream(String fileName) {
            if (fileName.contains(BeastsMod.MOD_ID) && fileName.contains("spawn_egg")) {
                return new ByteArrayInputStream(SPAWN_EGG);
            }
            return null;
        }

        @Override
        public InputStream getResourceStream(ResourcePackType type, ResourceLocation location) {
            return getRootResourceStream(location.toString());
        }

        @Override
        public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, String pathIn, int maxDepth, Predicate<String> filter) {
            return Collections.emptyList();
        }

        @Override
        public boolean resourceExists(ResourcePackType type, ResourceLocation location) {
            return location.getNamespace().equals(BeastsMod.MOD_ID) && location.getNamespace().contains("spawn_egg");
        }

        @Override
        public Set<String> getResourceNamespaces(ResourcePackType type) {
            return NAMESPACES;
        }

        @Nullable
        @Override
        public <T> T getMetadata(IMetadataSectionSerializer<T> deserializer) {
            return null;
        }

        @Override
        public String getName() {
            return NAME;
        }

        @Override
        public void close() {
        }
    }
}
