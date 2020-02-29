package random.beasts.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import random.beasts.common.entity.passive.EntityLandwhale;
import random.beasts.common.init.BeastsContainers;

public class ContainerLandwhaleInventory extends Container {
    public EntityLandwhale landwhale;

    public ContainerLandwhaleInventory(int id, PlayerInventory inventory) {
        super(BeastsContainers.LANDWHALE, id);
    }

    public ContainerLandwhaleInventory(int id, EntityLandwhale landwhale, PlayerEntity player) {
        this(id, player.inventory);
        this.landwhale = landwhale;
        landwhale.inventory.openInventory(player);
        this.addSlot(new Slot(landwhale.inventory, 0, 8, 18) {
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == Items.SADDLE && !this.getHasStack() && landwhale.inventory.getStackInSlot(0).isEmpty() && landwhale.getSheared();
            }

            public int getSlotStackLimit() {
                return 1;
            }
        });
        for (int i1 = 0; i1 < 3; ++i1)
            for (int k1 = 0; k1 < 9; ++k1)
                this.addSlot(new Slot(player.inventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
        for (int j1 = 0; j1 < 9; ++j1) this.addSlot(new Slot(player.inventory, j1, 8 + j1 * 18, 142));
        if (landwhale.hasChest()) for (int k = 0; k < 3; ++k)
            for (int l = 0; l < 5; ++l)
                this.addSlot(new Slot(landwhale.inventory, 2 + l + k * 5, 80 + l * 18, 18 + k * 18));
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        //slot.getSlot is causing the game to freeze by never existing this method
        /*ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 1)
                if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) return ItemStack.EMPTY;
                else if (this.getSlot(0).isItemValid(itemstack1))
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) return ItemStack.EMPTY;

            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }*/

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return landwhale.isTamed() && landwhale.isOwner(playerIn);
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.landwhale.inventory.closeInventory(playerIn);
    }
}
