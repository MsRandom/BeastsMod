package random.beasts.common.init;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import random.beasts.api.main.BeastsReference;
import random.beasts.common.tileentity.TileEntityCoconut;

import java.util.ArrayList;
import java.util.List;

public class BeastsTileEntities {

    private static final List<BeastsTileEntity> LIST = new ArrayList<>();
    private static final BeastsTileEntity COCONUT = new BeastsTileEntity(TileEntityCoconut.class, "coconut");

    public static void init() {
        for (BeastsTileEntity tile : LIST)
            GameRegistry.registerTileEntity(tile.cls, new ResourceLocation(BeastsReference.ID, tile.name));
    }

    private static class BeastsTileEntity {
        private Class<? extends TileEntity> cls;
        private String name;

        private BeastsTileEntity(Class<? extends TileEntity> te, String name) {
            this.cls = te;
            this.name = name;
            LIST.add(this);
        }
    }
}
