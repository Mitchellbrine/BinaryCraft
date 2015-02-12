package mc.Mitchellbrine.binaryCraft.tile;

import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class TileEntityComputer extends TileEntity {

	private ArrayList<ComputerScript> scripts = new ArrayList<ComputerScript>();

	public String consoleOutput = "";

	public TileEntityComputer() {
		scripts.add(new ComputerScript(new ScriptComputer(this),"HelloWorld","comp.println('Hello World')"));
	}

	public void updateEntity() {

	}

	public ArrayList<ComputerScript> getScripts() {
		return this.scripts;
	}

}
