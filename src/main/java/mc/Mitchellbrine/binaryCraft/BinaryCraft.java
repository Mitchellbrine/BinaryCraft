package mc.Mitchellbrine.binaryCraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import mc.Mitchellbrine.binaryCraft.block.BlockRegistry;
import mc.Mitchellbrine.binaryCraft.item.ItemRegistry;
import mc.Mitchellbrine.binaryCraft.network.PacketHandler;
import mc.Mitchellbrine.binaryCraft.proxy.CommonProxy;
import mc.Mitchellbrine.binaryCraft.syntax.Syntaxes;
import mc.Mitchellbrine.binaryCraft.util.RecipeHandler;
import mc.Mitchellbrine.binaryCraft.util.References;
import mc.Mitchellbrine.binaryCraft.util.StringHelper;
import mc.Mitchellbrine.binaryCraft.util.SyntaxHelper;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Mitchellbrine on 2015.
 */
@Mod(modid = References.MODID,name = References.NAME,version = References.VERSION)
public class BinaryCraft {

	@SidedProxy(clientSide = "mc.Mitchellbrine.binaryCraft.proxy.ClientProxy",serverSide = "mc.Mitchellbrine.binaryCraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static BinaryCraft instance;

	public static ScriptEngine jsEngine;

	public static Logger logger = LogManager.getLogger("JS Automation");

	public static CreativeTabs tab = new CreativeTabs(CreativeTabs.getNextID(),"Scripterino") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(BlockRegistry.binaryComputer);
		}
	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		proxy.renderStuff();
		BlockRegistry.init();
		ItemRegistry.init();
		PacketHandler.init();
		SyntaxHelper.init();
		RecipeHandler.init();

		String hello = "Hello World!";
		logger.info(hello);
		logger.info(StringHelper.toBinary(hello));
		logger.info(StringHelper.fromBinary(StringHelper.toBinaryBytes(hello)));

		ScriptEngineManager factory = new ScriptEngineManager();

		if (JAVA_VERSION >= 1.8) {
			logger.info("Detected Java 1.8. Good for you, good sir! Loading Nashorn instead of Rhino!");
			jsEngine = factory.getEngineByName("nashorn");
		} else {
			logger.info("You don't have the 1.8 powers for JS. LOOK OUT FOR RHINO!");
			jsEngine = factory.getEngineByName("JavaScript");
		}

		jsEngine.put("hello",hello);
		jsEngine.put("helloBinary",StringHelper.toBinary(hello));
		try {
			jsEngine.eval("println(hello)\n\nprintln(helloBinary)");
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		try {
			setFinalStatic(ReflectionHelper.findField(GuiMainMenu.class, "splashTexts", "field_110353_x"), new ResourceLocation(References.MODID.toLowerCase(), "texts/splashes.txt"));
			System.out.println(new ResourceLocation(References.MODID.toLowerCase(), "texts/splashes.txt"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		new Syntaxes();
	}


	public static void setFinalStatic(Field field, Object newValue) throws Exception {
		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}

	public static double JAVA_VERSION = getVersion ();

	static double getVersion () {
		String version = System.getProperty("java.version");
		int pos = version.indexOf('.');
		pos = version.indexOf('.', pos+1);
		return Double.parseDouble (version.substring (0, pos));
	}

}
