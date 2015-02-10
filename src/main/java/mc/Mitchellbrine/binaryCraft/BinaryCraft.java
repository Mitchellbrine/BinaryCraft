package mc.Mitchellbrine.binaryCraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mc.Mitchellbrine.binaryCraft.block.BlockRegistry;
import mc.Mitchellbrine.binaryCraft.item.ItemRegistry;
import mc.Mitchellbrine.binaryCraft.proxy.CommonProxy;
import mc.Mitchellbrine.binaryCraft.util.References;
import mc.Mitchellbrine.binaryCraft.util.StringHelper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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

		proxy.renderStuff();
		BlockRegistry.init();
		ItemRegistry.init();

		String hello = "Hello World!";
		System.out.println(hello);
		System.out.println(StringHelper.toBinary(hello));
		System.out.println(StringHelper.fromBinary(StringHelper.toBinaryBytes(hello)));

		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");

		engine.put("hello",hello);
		engine.put("helloBinary",StringHelper.toBinary(hello));
		try {
			engine.eval("println(hello)\n\nprintln(helloBinary)");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

}
