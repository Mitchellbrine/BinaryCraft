package mc.Mitchellbrine.binaryCraft.script;

import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptWorld;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

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
			dummyComp.getWorld();
		}

		engine.put("world",this.dummyWorld);

		try {
			engine.eval(this.scriptCode);
		} catch (ScriptException ex) {
			dummyComp.println(ex.toString());
		}
	}

	public void setCode(String code) {
		this.scriptCode = code;
	}


}
