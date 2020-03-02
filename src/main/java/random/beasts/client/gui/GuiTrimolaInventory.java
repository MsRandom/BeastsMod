package random.beasts.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityTrimola;
import random.beasts.common.inventory.ContainerTrimolaInventory;

public class GuiTrimolaInventory extends ContainerScreen<ContainerTrimolaInventory> {
    private static final ResourceLocation GUI = new ResourceLocation(BeastsMod.MOD_ID, "textures/gui/container/trimola.png");
    private final EntityTrimola trimola;

    public GuiTrimolaInventory(ContainerTrimolaInventory screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.trimola = screenContainer.trimola;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.minecraft != null) {
            this.minecraft.getTextureManager().bindTexture(GUI);
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            this.blit(i, j, 0, 0, this.xSize, this.ySize);
            InventoryScreen.drawEntityOnScreen(i + 97, j + 60, 20, i + 97 - mouseX, j + 25 - mouseY, trimola);
        }
    }
}
