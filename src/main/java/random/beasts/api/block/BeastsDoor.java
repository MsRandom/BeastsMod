package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import random.beasts.api.item.BeastsDoorItem;
import random.beasts.api.main.BeastsUtils;

import java.util.HashMap;
import java.util.Map;

public class BeastsDoor extends DoorBlock {
    public static final Map<BeastsDoor, Item> DOOR_ITEMS = new HashMap<>();

    public BeastsDoor(String name) {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(3, 0).sound(SoundType.WOOD));
        BeastsUtils.addToRegistry(this, name + "_door", BeastsDoorItem::new, this::itemCallback);
    }

    private void itemCallback(Item i) {
        DOOR_ITEMS.put(this, i);
    }

    /*@Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return state.get(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : DOOR_ITEMS.getOrDefault(this, Items.AIR);
    }*/
}
