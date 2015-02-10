package mc.Mitchellbrine.binaryCraft.tile;

import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Mitchellbrine on 2015.
 */
public class TileEntityComputer extends TileEntity {

	private ComputerScript[] scripts = new ComputerScript[256];

	public String consoleOutput = "";

	public void updateEntity() {
		if (!consoleOutput.equalsIgnoreCase("")) {
			System.out.println(consoleOutput);
		}
	}
}
