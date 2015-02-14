package mc.Mitchellbrine.binaryCraft.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import mc.Mitchellbrine.binaryCraft.BinaryCraft;
import mc.Mitchellbrine.binaryCraft.client.gui.GuiHandler;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void renderStuff() {
		NetworkRegistry.INSTANCE.registerGuiHandler(BinaryCraft.instance,new GuiHandler());
	}
}
