package random.beasts.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import random.beasts.common.init.BeastsTileEntities;

public class TileEntityCoconut extends TileEntity {
    public TileEntityCoconut(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TileEntityCoconut() {
        super(BeastsTileEntities.COCONUT);
    }
}
