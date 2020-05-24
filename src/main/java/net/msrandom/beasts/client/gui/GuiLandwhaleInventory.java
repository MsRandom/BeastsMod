package net.msrandom.beasts.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.common.entity.passive.EntityLandwhale;
import net.msrandom.beasts.common.inventory.ContainerLandwhaleInventory;

public class GuiLandwhaleInventory extends GuiContainer {
    private static final ResourceLocation GUI = new ResourceLocation(BeastsReference.ID, "textures/gui/container/landwhale.png");
    private static final ResourceLocation CHEST = new ResourceLocation(BeastsReference.ID, "textures/gui/container/landwhale_chest.png");
    private final EntityLandwhale landwhale;

    public GuiLandwhaleInventory(EntityLandwhale landwhale, EntityPlayer player) {
        super(new ContainerLandwhaleInventory(landwhale, player));
        this.landwhale = landwhale;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(landwhale.hasChest() ? CHEST : GUI);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        GuiInventory.drawEntityOnScreen(i + 51, j + 60, 10, i + 97 - mouseX, j + 25 - mouseY, landwhale);
    }
}
