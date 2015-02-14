package mc.Mitchellbrine.binaryCraft.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import mc.Mitchellbrine.binaryCraft.container.ContainerComputer;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Mitchellbrine on 2015.
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case IDS.COMPUTER:
				return new ContainerComputer((TileEntityComputer)world.getTileEntity(x,y,z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case IDS.COMPUTER:
				return new GuiComputer((TileEntityComputer)world.getTileEntity(x,y,z));
		}
		return null;
	}

	public static final class IDS {
		public static final int COMPUTER = 0;
	}

	/*public static void openGui(int id, TileEntity entity) {
		switch (id) {
			case IDS.COMPUTER:
				Minecraft.getMinecraft().displayGuiScreen(new GuiComputer((TileEntityComputer)entity));
				break;
		}
	} */

}
