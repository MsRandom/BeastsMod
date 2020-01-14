package random.beasts.common.network;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
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

    private static final List<BeastsGuiScreen> LIST = new ArrayList<>();
    private static int lastId = 0;
    public static final BeastsGuiScreen GUI_LANDWHALE = new BeastsGuiScreen((player, world, x, y, z) -> new ContainerLandwhaleInventory((EntityLandwhale) Objects.requireNonNull(world.getEntityByID(x)), player), (player, world, x, y, z) -> new GuiLandwhaleInventory((EntityLandwhale) world.getEntityByID(x), player));
    public static final BeastsGuiScreen GUI_TRIMOLA = new BeastsGuiScreen((player, world, x, y, z) -> new ContainerTrimolaInventory((EntityTrimola) Objects.requireNonNull(world.getEntityByID(x)), player), (player, world, x, y, z) -> new GuiTrimolaInventory((EntityTrimola) world.getEntityByID(x), player));

    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(BeastsMod.instance, new BeastsGuiHandler());
    }

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BeastsGuiScreen screen = LIST.get(ID);
        return screen == null ? null : screen.serverGetter.apply(player, world, x, y, z);
    }

    @Override
    public GuiContainer getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BeastsGuiScreen screen = LIST.get(ID);
        return screen == null ? null : screen.clientGetter.apply(player, world, x, y, z);
    }

    @FunctionalInterface
    private interface SidedElementGetter<T> {
        T apply(EntityPlayer player, World world, int x, int y, int z);
    }

    public static class BeastsGuiScreen {
        private final SidedElementGetter<? extends Container> serverGetter;
        private final SidedElementGetter<? extends GuiContainer> clientGetter;
        private final int id;

        BeastsGuiScreen(SidedElementGetter<? extends Container> server, SidedElementGetter<? extends GuiContainer> client) {
            this(server, client, lastId++);
        }

        private BeastsGuiScreen(SidedElementGetter<? extends Container> server, SidedElementGetter<? extends GuiContainer> client, int id) {
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
