package mc.Mitchellbrine.binaryCraft.client.gui;

import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Mitchellbrine on 2015.
 */
public class GuiHandler {

	public static final class IDS {
		public static final int COMPUTER = 0;
	}

	public static void openGui(int id, TileEntity entity) {
		System.out.println(id);
		switch (id) {
			case IDS.COMPUTER:
				Minecraft.getMinecraft().displayGuiScreen(new GuiComputer((TileEntityComputer)entity));
				break;
		}
	}

}
