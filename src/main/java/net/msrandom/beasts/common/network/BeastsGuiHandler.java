package net.msrandom.beasts.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.msrandom.beasts.client.gui.GuiLandwhaleInventory;
import net.msrandom.beasts.client.gui.GuiTrimolaInventory;
import net.msrandom.beasts.common.BeastsMod;
import net.msrandom.beasts.common.entity.monster.EntityTrimola;
import net.msrandom.beasts.common.entity.passive.EntityLandwhale;
import net.msrandom.beasts.common.inventory.ContainerLandwhaleInventory;
import net.msrandom.beasts.common.inventory.ContainerTrimolaInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeastsGuiHandler implements IGuiHandler {
    private static int lastId = 0;
    private static final List<BeastsGuiScreen> LIST = new ArrayList<>();
    public static final BeastsGuiScreen GUI_LANDWHALE = new BeastsGuiScreen((player, world, x, y, z) -> new ContainerLandwhaleInventory((EntityLandwhale) Objects.requireNonNull(world.getEntityByID(x)), player), (player, world, x, y, z) -> new GuiLandwhaleInventory((EntityLandwhale) world.getEntityByID(x), player));
    public static final BeastsGuiScreen GUI_TRIMOLA = new BeastsGuiScreen((player, world, x, y, z) -> new ContainerTrimolaInventory((EntityTrimola) Objects.requireNonNull(world.getEntityByID(x)), player), (player, world, x, y, z) -> new GuiTrimolaInventory((EntityTrimola) world.getEntityByID(x), player));

    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(BeastsMod.instance, new BeastsGuiHandler());
    }

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
