package random.beasts.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import random.beasts.common.entity.passive.EntityLandwhale;

public class ContainerLandwhaleInventory extends Container {

    private EntityLandwhale landwhale;

    public ContainerLandwhaleInventory(EntityLandwhale landwhale, EntityPlayer player) {
        this.landwhale = landwhale;
        landwhale.inventory.openInventory(player);
        this.addSlotToContainer(new Slot(landwhale.inventory, 0, 54, 18) {
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == Items.SADDLE && !this.getHasStack() && landwhale.getSaddle().isEmpty() && landwhale.getSheared();
            }
        });
        for (int i1 = 0; i1 < 3; ++i1) for (int k1 = 0; k1 < 9; ++k1) this.addSlotToContainer(new Slot(player.inventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
        for (int j1 = 0; j1 < 9; ++j1) this.addSlotToContainer(new Slot(player.inventory, j1, 8 + j1 * 18, 142));
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 1)
            {
                if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (this.getSlot(0).isItemValid(itemstack1))
            {
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return landwhale.isTamed() && landwhale.isOwner(playerIn);
    }

    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        this.landwhale.inventory.closeInventory(playerIn);
    }
}
