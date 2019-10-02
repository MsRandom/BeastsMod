package rando.beasts.common.utils.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import rando.beasts.client.gui.GuiLandwhaleInventory;
import rando.beasts.common.entity.passive.EntityLandwhale;
import rando.beasts.common.inventory.ContainerLandwhaleInventory;

public class GuiHandler implements IGuiHandler {
	
	public static final int GUI_LANDWHALE = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_LANDWHALE) return new ContainerLandwhaleInventory((EntityLandwhale) world.getEntityByID(x), player);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_LANDWHALE) return new GuiLandwhaleInventory((EntityLandwhale) world.getEntityByID(x), player);
		return null;
	}

}
