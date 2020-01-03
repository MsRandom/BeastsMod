package random.beasts.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import random.beasts.client.gui.GuiLandwhaleInventory;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityLandwhale;
import random.beasts.common.inventory.ContainerLandwhaleInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeastsGuiHandler implements IGuiHandler {

	public static final BeastsGuiScreen GUI_LANDWHALE = new BeastsGuiScreen((player, world, x, y, z) -> new ContainerLandwhaleInventory((EntityLandwhale) Objects.requireNonNull(world.getEntityByID(x)), player), (player, world, x, y, z) -> new GuiLandwhaleInventory((EntityLandwhale) world.getEntityByID(x), player));

	private static int lastId = 0;
	private static final List<BeastsGuiScreen> LIST = new ArrayList<>();
	private final byte[] blockBiomeArray = new byte[256];

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BeastsGuiScreen screen = LIST.get(ID);
		return screen == null ? null : screen.serverGetter.apply(player, world, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BeastsGuiScreen screen = LIST.get(ID);
		return screen == null ? null : screen.clientGetter.apply(player, world, x, y, z);
	}

	public static void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(BeastsMod.instance, new BeastsGuiHandler());
	}

	public Biome getBiome(BlockPos pos, BiomeProvider provider) {
		int i = pos.getX() & 15;
		int j = pos.getZ() & 15;
		int k = this.blockBiomeArray[j << 4 | i] & 255;

		if (k == 255) {
			Biome biome = provider.getBiome(pos, Biomes.PLAINS);
			k = Biome.getIdForBiome(biome);
			this.blockBiomeArray[j << 4 | i] = (byte) (k & 255);
		}

		Biome biome1 = Biome.getBiome(k);
		return biome1 == null ? Biomes.PLAINS : biome1;
	}

	@FunctionalInterface
	private interface SidedElementGetter {
		Object apply(EntityPlayer player, World world, int x, int y, int z);
	}

	public static class BeastsGuiScreen {
		private final SidedElementGetter serverGetter;
		private final SidedElementGetter clientGetter;
		private final int id;

		BeastsGuiScreen(SidedElementGetter server, SidedElementGetter client) {
			this(server, client, lastId++);
		}

		private BeastsGuiScreen(SidedElementGetter server, SidedElementGetter client, int id) {
			this.serverGetter = server;
			this.clientGetter = client;
			this.id = id;
			LIST.add(id, this);
		}

		public int getId() {
			return id;
		}
	}
}
