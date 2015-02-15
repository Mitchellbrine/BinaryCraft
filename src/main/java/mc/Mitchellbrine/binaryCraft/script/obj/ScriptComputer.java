package mc.Mitchellbrine.binaryCraft.script.obj;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import mc.Mitchellbrine.binaryCraft.BinaryCraft;
import mc.Mitchellbrine.binaryCraft.network.ConsolePacket;
import mc.Mitchellbrine.binaryCraft.network.PacketHandler;
import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ScriptComputer {

	public TileEntityComputer computer;

	public ScriptComputer(TileEntityComputer computer) {
		this.computer = computer;
	}

	public int getX() {
		return computer.xCoord;
	}

	public int getY() {
		return computer.yCoord;
	}

	public int getZ() {
		return computer.zCoord;
	}

	public ScriptWorld getWorld() {
		return new ScriptWorld(computer.getWorldObj());
	}

	public String getConsole() {
		return computer.consoleOutput;
	}

	public void println(String string) {
		String oldOutput = computer.consoleOutput;
		computer.consoleOutput = oldOutput + "\n" + string;
		System.out.println("MESSAGE: " + string + " | Side: " + FMLCommonHandler.instance().getEffectiveSide());
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			PacketHandler.INSTANCE.sendTo(new ConsolePacket(this.computer),(EntityPlayerMP)computer.getWorldObj().getClosestPlayer(getX(),getY(),getZ(),10));
		}
	}

	public void print(String string) {
		String oldOutput = computer.consoleOutput;
		computer.consoleOutput = oldOutput + string;
		System.out.println("MESSAGE: " + string + " | Side: " + FMLCommonHandler.instance().getEffectiveSide());
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			PacketHandler.INSTANCE.sendTo(new ConsolePacket(this.computer),(EntityPlayerMP)computer.getWorldObj().getClosestPlayer(getX(),getY(),getZ(),10));
		}
	}

	public void clearConsole() {
		computer.consoleOutput = "";
	}

	public void executeScript(String identifier) {
		for (ComputerScript script : computer.getScripts()) {
			if (script.identifier.equalsIgnoreCase(identifier)) {
				script.run(BinaryCraft.jsEngine);
				break;
			}
		}
	}

	public int getPower() {
		return computer.getPower();
	}

	public void isServerScript() {}

	public void setPower(int newPower) {
		computer.setPower(newPower);
	}

}
