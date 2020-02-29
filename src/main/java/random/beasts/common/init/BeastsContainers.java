package random.beasts.common.init;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.common.inventory.ContainerLandwhaleInventory;
import random.beasts.common.inventory.ContainerTrimolaInventory;

public class BeastsContainers {
    public static final ContainerType<ContainerLandwhaleInventory> LANDWHALE = create("landwhale", ContainerLandwhaleInventory::new);
    public static final ContainerType<ContainerTrimolaInventory> TRIMOLA = create("trimola", ContainerTrimolaInventory::new);

    private static <T extends Container> ContainerType<T> create(String name, ContainerType.IFactory<T> factory) {
        ContainerType<T> type = new ContainerType<>(factory);
        type.setRegistryName(name);
        BeastsRegistries.CONTAINERS.add(type);
        return type;
    }
}
