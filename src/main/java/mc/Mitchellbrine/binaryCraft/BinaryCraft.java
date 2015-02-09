package mc.Mitchellbrine.binaryCraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mc.Mitchellbrine.binaryCraft.proxy.CommonProxy;
import mc.Mitchellbrine.binaryCraft.util.References;
import mc.Mitchellbrine.binaryCraft.util.StringHelper;

/**
 * Created by Mitchellbrine on 2015.
 */
@Mod(modid = References.MODID,name = References.NAME,version = References.VERSION)
public class BinaryCraft {

	@SidedProxy(clientSide = "mc.Mitchellbrine.binaryCraft.proxy.ClientProxy",serverSide = "mc.Mitchellbrine.binaryCraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static BinaryCraft instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		String hello = "Hello World!";
		System.out.println(hello);
		System.out.println(StringHelper.toBinary(hello));
		System.out.println(StringHelper.fromBinary(StringHelper.toBinaryBytes(hello)));
	}

}
