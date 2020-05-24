package net.msrandom.beasts.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.msrandom.beasts.common.entity.monster.EntityTrimola;

public class ContainerTrimolaInventory extends Container {
    private final EntityTrimola trimola;
    private final Slot saddleSlot;

    public ContainerTrimolaInventory(EntityTrimola trimola, EntityPlayer player) {
        this.trimola = trimola;
        trimola.inventory.openInventory(player);
        this.addSlotToContainer(saddleSlot = new Slot(trimola.inventory, 0, 54, 18) {
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == Items.SADDLE && !this.getHasStack() && trimola.getSaddle().isEmpty();
            }

            public int getSlotStackLimit() {
                return 1;
            }
        });

        for (int i1 = 0; i1 < 3; ++i1)
            for (int k1 = 0; k1 < 9; ++k1)
                this.addSlotToContainer(new Slot(player.inventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
        for (int j1 = 0; j1 < 9; ++j1) this.addSlotToContainer(new Slot(player.inventory, j1, 8 + j1 * 18, 142));
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        System.out.println("A");
        if (slot != null && slot.getHasStack())
        {
            System.out.println("B");
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

             if (index == 0 && saddleSlot.isItemValid(itemstack1))
            {
                System.out.println("C");
                if (!this.mergeItemStack(itemstack1, saddleSlot.getSlotIndex(), 1, false))
                {
                    System.out.println("D");
                    return ItemStack.EMPTY;
                }
            }
/*             else{
                 return ItemStack.EMPTY;
             }*/

            System.out.println("E");

            if (itemstack1.isEmpty())
            {
                System.out.println("F");
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                System.out.println("G");
                slot.onSlotChanged();
            }
        }
        System.out.println("H");
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return trimola.isTamed() && trimola.isOwner(playerIn);
    }

    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        this.trimola.inventory.closeInventory(playerIn);
    }
}
