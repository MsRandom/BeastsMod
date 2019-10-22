package rando.beasts.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import rando.beasts.client.gui.GuiLandwhaleInventory;
import rando.beasts.common.entity.passive.EntityLandwhale;
import rando.beasts.common.inventory.ContainerLandwhaleInventory;
import rando.beasts.common.main.BeastsMod;

import java.util.ArrayList;
import java.util.List;

public class BeastsGuiHandler implements IGuiHandler {

	private static int lastId = 0;
	private static final List<BeastsGuiScreen> LIST = new ArrayList<>();
	public static final BeastsGuiScreen GUI_LANDWHALE = new BeastsGuiScreen((player, world, x, y, z) -> new ContainerLandwhaleInventory((EntityLandwhale) world.getEntityByID(x), player), (player, world, x, y, z) -> new GuiLandwhaleInventory((EntityLandwhale) world.getEntityByID(x), player));

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BeastsGuiScreen screen = LIST.get(ID);
		return screen == null ? null :screen.getServer.apply(player, world, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BeastsGuiScreen screen = LIST.get(ID);
		return screen == null ? null :screen.getClient.apply(player, world, x, y, z);
	}

	public static void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(BeastsMod.instance, new BeastsGuiHandler());
	}

	public static class BeastsGuiScreen {
		private final SidedElementGetter getServer;
		private final SidedElementGetter getClient;
		private final int id;

		BeastsGuiScreen(SidedElementGetter server, SidedElementGetter client) {
			this(server, client, lastId++);
		}

		private BeastsGuiScreen(SidedElementGetter server, SidedElementGetter client, int id) {
			this.getServer = server;
			this.getClient = client;
			this.id = id;
			LIST.add(id, this);
		}

		public int getId() {
			return id;
		}
	}
	
	@FunctionalInterface
	private interface SidedElementGetter {
		Object apply(EntityPlayer player, World world, int x, int y, int z);
	}
}
