package mc.Mitchellbrine.binaryCraft.script;

import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptWorld;

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

		try {
			engine.eval(this.scriptCode);
		} catch (ScriptException ex) {
			ex.printStackTrace();
			String oldOutput = dummyComp.getConsole();
			dummyComp.computer.consoleOutput = oldOutput + "\n" + ex.toString().replaceAll(":",":\n ");
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
