package mc.Mitchellbrine.binaryCraft.script.obj;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ScriptNBT {

	private NBTTagCompound tag;

	public ScriptNBT(NBTTagCompound nbt) {
		this.tag = nbt;
	}

	public NBTTagCompound getTag() {
		return this.tag;
	}

	public void setTag(NBTTagCompound nbt) {
		this.tag = nbt;
	}

}
