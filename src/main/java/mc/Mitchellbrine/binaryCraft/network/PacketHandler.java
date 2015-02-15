package mc.Mitchellbrine.binaryCraft.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import mc.Mitchellbrine.binaryCraft.util.References;

/**
 * Created by Mitchellbrine on 2015.
 */
public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.MODID);

	public static void init()
	{
		INSTANCE.registerMessage(ScriptPacket.class, ScriptPacket.class, 0, Side.SERVER);
		INSTANCE.registerMessage(RunScriptPacket.class, RunScriptPacket.class, 1, Side.SERVER);
		INSTANCE.registerMessage(ConsolePacket.class, ConsolePacket.class, 2, Side.CLIENT);
	}

}
