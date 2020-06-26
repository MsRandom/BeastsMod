package net.msrandom.beasts.proxy;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.api.block.BeastsSlab;
import net.msrandom.beasts.api.item.IHandleMeta;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.api.main.BeastsRegistries;
import net.msrandom.beasts.client.renderer.entity.*;
import net.msrandom.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import net.msrandom.beasts.common.block.BlockAnemoneMouth;
import net.msrandom.beasts.common.entity.item.EntityBeastsPainting;
import net.msrandom.beasts.common.entity.item.EntityFallingCoconut;
import net.msrandom.beasts.common.entity.item.EntityThrownCoconut;
import net.msrandom.beasts.common.entity.monster.*;
import net.msrandom.beasts.common.entity.passive.*;
import net.msrandom.beasts.common.entity.projectile.EntityCoconutBomb;
import net.msrandom.beasts.common.entity.projectile.EntityGlowShrimpShot;
import net.msrandom.beasts.common.init.BeastsBlocks;
import net.msrandom.beasts.common.tileentity.TileEntityCoconut;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public static final KeyBinding TRIMOLA_ATTACK = new KeyBinding("trimola.attack", 19, "key.categories.misc");

    @Override
    public void preInit() {
        super.preInit();
        ClientRegistry.registerKeyBinding(TRIMOLA_ATTACK);
        this.registerEntityRenders();
    }

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public EntityPlayer getPlayer(MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            return ctx.getServerHandler().player;
        }
        return Minecraft.getMinecraft().player;
    }

    @Override
    public boolean isClientSneaking() {
        return Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown();
    }

    @Override
    public boolean isTrimolaAttacking() {
        return TRIMOLA_ATTACK.isKeyDown();
    }

    private void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityCoconutCrab.class, RenderCoconutCrab::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPufferfishDog.class, RenderPufferfishDog::new);
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
        RenderingRegistry.registerEntityRenderingHandler(EntityWhippingBarnacle.class, RenderWhippingBarnacle::new);
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
        RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalLegfish.class, RenderAbyssalLegfish::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGlowShrimpShot.class, RenderGlowShrimpShot::new);
        registerTileEntityRenders();
    }

    public void registerEventRenders() {
        for (Item item : BeastsRegistries.ITEMS.get()) {
            if (item instanceof IHandleMeta) {
                IHandleMeta metaItem = (IHandleMeta) item;
                for (int i = 0; i < metaItem.getDamage(); i++)
                    ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(BeastsReference.ID, Objects.requireNonNull(item.getRegistryName()).getResourcePath() + "_" + metaItem.handleMeta(i)), "inventory"));
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
        ModelLoader.setCustomStateMapper(BeastsBlocks.SHELL_SLAB.full, new StateMap.Builder().ignore(BlockSlab.HALF, BeastsSlab.VARIANT).build());
    }

    private void registerTileEntityRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoconut.class, new TileEntityCoconutRenderer());
    }

    @Override
    public boolean isFancyGraphics() {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }
}
