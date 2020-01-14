package random.beasts.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.common.entity.monster.EntityTrimola;
import random.beasts.common.inventory.ContainerTrimolaInventory;

public class GuiTrimolaInventory extends GuiContainer {
    private static final ResourceLocation GUI = new ResourceLocation(BeastsReference.ID, "textures/gui/container/trimola.png");
    private final EntityTrimola trimola;

    public GuiTrimolaInventory(EntityTrimola trimola, EntityPlayer player) {
        super(new ContainerTrimolaInventory(trimola, player));
        this.trimola = trimola;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        GuiInventory.drawEntityOnScreen(i + 97, j + 60, 20, i + 97 - mouseX, j + 25 - mouseY, trimola);
    }
}
