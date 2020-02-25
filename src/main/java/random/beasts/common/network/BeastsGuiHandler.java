package random.beasts.common.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import random.beasts.client.gui.GuiLandwhaleInventory;
import random.beasts.client.gui.GuiTrimolaInventory;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityTrimola;
import random.beasts.common.entity.passive.EntityLandwhale;
import random.beasts.common.inventory.ContainerLandwhaleInventory;
import random.beasts.common.inventory.ContainerTrimolaInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeastsGuiHandler implements IGuiHandler {
    private static int lastId = 0;
    public static final BeastsScreen GUI_LANDWHALE = new BeastsScreen((player, world, x, y, z) -> new ContainerLandwhaleInventory((EntityLandwhale) Objects.requireNonNull(world.getEntityByID(x)), player), (player, world, x, y, z) -> new GuiLandwhaleInventory((EntityLandwhale) world.getEntityByID(x), player));
    public static final BeastsScreen GUI_TRIMOLA = new BeastsScreen((player, world, x, y, z) -> new ContainerTrimolaInventory((EntityTrimola) Objects.requireNonNull(world.getEntityByID(x)), player), (player, world, x, y, z) -> new GuiTrimolaInventory((EntityTrimola) world.getEntityByID(x), player));
    private static final List<BeastsScreen> LIST = new ArrayList<>();

    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(BeastsMod.instance, new BeastsGuiHandler());
    }

    @Override
    public Object getServerGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        BeastsScreen screen = LIST.get(ID);
        return screen == null ? null : screen.serverGetter.apply(player, world, x, y, z);
    }

    @Override
    public Object getClientGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        BeastsScreen screen = LIST.get(ID);
        return screen == null ? null : screen.clientGetter.apply(player, world, x, y, z);
    }

    @FunctionalInterface
    private interface SidedElementGetter {
        Object apply(PlayerEntity player, World world, int x, int y, int z);
    }

    public static class BeastsScreen {
        private final SidedElementGetter serverGetter;
        private final SidedElementGetter clientGetter;
        private final int id;

        BeastsScreen(SidedElementGetter server, SidedElementGetter client) {
            this(server, client, lastId++);
        }

        private BeastsScreen(SidedElementGetter server, SidedElementGetter client, int id) {
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
