package mc.Mitchellbrine.binaryCraft.script.obj;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ScriptInventory {

	private IInventory inventory;

	public ScriptInventory(IInventory inventory) {
		this.inventory = inventory;
	}

	public int getSize() {
		return inventory.getSizeInventory();
	}

	public int getStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	public int getItemInSlot(int slot) {
		return Item.getIdFromItem(inventory.getStackInSlot(slot).getItem());
	}

	public int getMetaInSlot(int slot) {
		return inventory.getStackInSlot(slot).getItemDamage();
	}

	public ScriptNBT getNBTInSlot(int slot) {
		return new ScriptNBT(inventory.getStackInSlot(slot).getTagCompound());
	}

	private void decreaseStackSize(int slot, int amount) {
		inventory.decrStackSize(slot, amount);
	}

	private void setInventoryContents(int slot, int id, int amount, int meta, ScriptNBT nbt) {
		ItemStack stack = new ItemStack(Item.getItemById(id),amount,meta);
		stack.setTagCompound(nbt.getTag());
		this.inventory.setInventorySlotContents(slot, stack);
	}

	private void setInventoryContents(int slot, int id) {
		this.inventory.setInventorySlotContents(slot,new ItemStack(Item.getItemById(id)));
	}

	private void setInventoryContents(int slot, int id, int meta) {
		this.inventory.setInventorySlotContents(slot,new ItemStack(Item.getItemById(id),1,meta));
	}

	private void setInventoryContents(int slot, int id, int meta, ScriptNBT nbt) {
		ItemStack stack = new ItemStack(Item.getItemById(id),1,meta);
		stack.setTagCompound(nbt.getTag());
		this.inventory.setInventorySlotContents(slot,stack);
	}

	public void moveContents(ScriptInventory otherInv, int thisInvSlot, int otherInvSlot) {
		if (this.inventory.getStackInSlot(thisInvSlot) == null || otherInv.inventory.getStackInSlot(otherInvSlot) != null) { throw new NullPointerException("The slot cannot be altered"); }
		ItemStack stack = this.inventory.getStackInSlot(thisInvSlot);
		this.inventory.setInventorySlotContents(thisInvSlot,null);
		otherInv.inventory.setInventorySlotContents(otherInvSlot,stack.copy());
	}

	public void dumpContents(ScriptWorld world, int x, int y, int z, int slots) {
		for (int i = 0; i < slots;i++) {
			if (inventory.getStackInSlot(i) != null) {
				EntityItem item = new EntityItem(world.world, x, y, z, inventory.getStackInSlot(i));
				world.world.spawnEntityInWorld(item);
				inventory.setInventorySlotContents(i,null);
			}
		}
	}

}
