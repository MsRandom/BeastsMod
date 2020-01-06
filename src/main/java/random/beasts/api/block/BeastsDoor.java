package random.beasts.api.block;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import random.beasts.api.item.BeastsDoorItem;
import random.beasts.api.main.BeastsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BeastsDoor extends BlockDoor {
    private static final Map<BeastsDoor, Item> DOOR_ITEMS = new HashMap<>();

    public BeastsDoor(String name) {
        super(Material.WOOD);
        setHardness(3.0F);
        setSoundType(SoundType.WOOD);
        disableStats();
        BeastsUtils.addToRegistry(this, name + "_door", BeastsDoorItem::new, this::itemCallback);
    }

    private void itemCallback(Item i) {
        DOOR_ITEMS.put(this, i);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : DOOR_ITEMS.getOrDefault(this, Items.AIR);
    }
}
