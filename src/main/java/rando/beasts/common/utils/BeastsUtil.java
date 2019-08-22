package rando.beasts.common.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import rando.beasts.client.init.BeastsCreativeTabs;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;

public class BeastsUtil {

    public static void addToRegistry(Item item, String name, boolean tab) {
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        if(tab)
            item.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsItems.LIST.add(item);
    }

    public static void addToRegistry(Block block, String name, boolean tab) {
        block.setUnlocalizedName(name);
        block.setRegistryName(name);
        if(tab)
            block.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsBlocks.LIST.add(block);
        BeastsItems.LIST.add(new ItemBlock(block).setRegistryName(name));
    }

    public static Entity getClosestEntityToEntity(Entity e, double distance) {
        return getClosestEntity2(e.getClass(), e.world, e.posX, e.posY, e.posZ, distance);
    }

    public static Entity getClosestEntity2(Class search, World world, double x, double y, double z, double distance) {
        double d4 = -1.0D;
        Entity entityFound = null;

        for (int i = 0; i < world.loadedEntityList.size(); ++i) {
            Entity currE = (Entity) world.loadedEntityList.get(i);

            if (!(currE.getClass() == search)) {
                double d5 = currE.getDistanceSq(x, y, z);

                if ((distance < 0.0D || d5 < distance * distance) && (d4 == -1.0D || d5 < d4)) {
                    d4 = d5;
                    entityFound = currE;
                }
            }

        }

        return entityFound;
    }
}
