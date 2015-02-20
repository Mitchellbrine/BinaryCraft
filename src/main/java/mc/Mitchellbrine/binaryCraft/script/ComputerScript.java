package mc.Mitchellbrine.binaryCraft.script;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import mc.Mitchellbrine.binaryCraft.event.ScriptEvent;
import mc.Mitchellbrine.binaryCraft.network.ConsolePacket;
import mc.Mitchellbrine.binaryCraft.network.PacketHandler;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptWorld;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ComputerScript {

	public String scriptCode;
	public String identifier;
	public ScriptWorld dummyWorld;
	public ScriptComputer dummyComp;

	public ComputerScript(ScriptComputer comp, String identifier, String scriptCode) {
		this.identifier = identifier;
		this.dummyComp = comp;
		this.scriptCode = scriptCode;
	}

	public void run(ScriptEngine engine) {
		if (dummyComp == null) return;

		engine.put("comp",this.dummyComp);

		if (dummyWorld == null) {
			dummyWorld = dummyComp.getWorld();
		}

		engine.put("world",this.dummyWorld);

		engine.put("x",dummyComp.getX());
		engine.put("y",dummyComp.getY());
		engine.put("z",dummyComp.getZ());

		ScriptEvent.RuntimeSyntaxEvent event = new ScriptEvent.RuntimeSyntaxEvent(this.scriptCode);

		if (this.scriptCode.contains("comp.setLanguage(\"") || this.scriptCode.contains("comp.setLanguage('")) {
			String afterQuotation = this.scriptCode.substring(this.scriptCode.indexOf("comp.setLanguage(") + 18);
			if (this.scriptCode.contains("comp.setLanguage(\"")) {
			event = new ScriptEvent.RuntimeSyntaxEvent(this.scriptCode,this.scriptCode.substring(this.scriptCode.indexOf("comp.setLanguage(") + 18,this.scriptCode.indexOf("comp.setLanguage(") + 18 + afterQuotation.indexOf("\"") - 1));
			} else {
				event = new ScriptEvent.RuntimeSyntaxEvent(this.scriptCode,this.scriptCode.substring(this.scriptCode.indexOf("comp.setLanguage(") + 18,this.scriptCode.indexOf("comp.setLanguage(") + 18 + afterQuotation.indexOf("'") - 1));
			}
		}


		if (!MinecraftForge.EVENT_BUS.post(event)) {
			try {
				engine.eval(event.scriptCode);
			} catch (ScriptException ex) {
				ex.printStackTrace();
				String oldOutput = dummyComp.getConsole();
				dummyComp.computer.consoleOutput = oldOutput + "\n" + ex.toString().replaceAll(":", ":\n ").replaceAll("<Unknown source>", this.identifier);
				if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
					PacketHandler.INSTANCE.sendTo(new ConsolePacket(this.dummyComp.computer), (EntityPlayerMP) dummyComp.computer.getWorldObj().getClosestPlayer(this.dummyComp.getX(), this.dummyComp.getY(), this.dummyComp.getZ(), 10));
				}
			}
		}
	}

	public void run(ScriptEngine engine, ArrayList<String> stringValues, ArrayList<Object> objectValues) {
		if (stringValues.size() == objectValues.size()) {
			for (int i = 0; i < stringValues.size(); i++) {
				engine.put(stringValues.get(i),objectValues.get(i));
			}
		}

		this.run(engine);

	}

	public void setCode(String code) {
		this.scriptCode = code;
	}


}
