package random.beasts.common.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.common.tileentity.TileEntityCoconut;

import java.util.function.Supplier;

public class BeastsTileEntities {
    public static final TileEntityType<TileEntityCoconut> COCONUT = create("coconut", TileEntityCoconut::new, BeastsBlocks.COCONUT);

    private static <T extends TileEntity> TileEntityType<T> create(String name, Supplier<T> factory, Block block) {
        TileEntityType<T> type = TileEntityType.Builder.create(factory, block).build(null);
        type.setRegistryName(name);
        BeastsRegistries.TILE_ENTITIES.add(type);
        return type;
    }
}
