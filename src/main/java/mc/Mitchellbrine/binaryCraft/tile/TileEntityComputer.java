package mc.Mitchellbrine.binaryCraft.tile;

import mc.Mitchellbrine.binaryCraft.BinaryCraft;
import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class TileEntityComputer extends TileEntity {

	private ArrayList<ComputerScript> scripts = new ArrayList<ComputerScript>();

	public String consoleOutput = "";
	private int power;

	public TileEntityComputer() {
		power = 0;
		scripts.add(new ComputerScript(new ScriptComputer(this),"HelloWorld","comp.println('Hello World')"));
	}

	public void updateEntity() {
		if (worldObj.getTotalWorldTime() % 20 == 0) {
			if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				for (ComputerScript script : scripts) {
					if (script.identifier.equalsIgnoreCase("rs_powered")) {
						script.run(BinaryCraft.jsEngine);
					}
					if (script.identifier.equalsIgnoreCase("rs_powered_" + worldObj.getStrongestIndirectPower(xCoord,yCoord,zCoord))) {
						script.run(BinaryCraft.jsEngine);
					}
				}
			}
		}
	}

	public ArrayList<ComputerScript> getScripts() {
		return this.scripts;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("power",this.power);

		NBTTagList nbttaglist = new NBTTagList();
		for (ComputerScript script : scripts) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("identifier", script.identifier);
			compound.setString("code", script.scriptCode);
			nbttaglist.appendTag(compound);
		}
		nbt.setTag("scripts", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("power")) {
			this.power = nbt.getInteger("power");
		}
		if (nbt.hasKey("scripts")) {
			NBTTagList nbts = nbt.getTagList("scripts", 10);
			for (int i = 0; i < nbts.tagCount(); i++) {
				NBTTagCompound tag = nbts.getCompoundTagAt(i);
				if (tag.hasKey("identifier") && tag.hasKey("code")) {
					scripts.add(new ComputerScript(new ScriptComputer(this), tag.getString("identifier"), tag.getString("code")));
				} else {
					System.err.println("A computer script failed to be read from NBT");
				}
			}
		}
	}

	public int getPower() {
		return this.power;
	}

	public void setPower(int newValue) {
		this.power = newValue;
	}

	public void setScripts(ArrayList<ComputerScript> scripts) {
		for (ComputerScript script : scripts) {
			System.out.println(script.identifier + " " + script.scriptCode);
		}
		this.scripts = scripts;
	}

	public void runScript(ComputerScript script) {
			for (ComputerScript script1 : scripts) {
				if (script1.identifier.equalsIgnoreCase(script.identifier)) {
					System.out.println("Ran a script from the computer");
					script1.run(BinaryCraft.jsEngine);
					break;
				}
			}
	}

}
