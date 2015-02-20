package mc.Mitchellbrine.binaryCraft.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * Created by Mitchellbrine on 2015.
 */
@Cancelable
public class ScriptEvent extends Event {

	public String scriptCode;

	public ScriptEvent(String code) {
		this.scriptCode = code;
	}

	public static class RuntimeSyntaxEvent extends ScriptEvent {
		private String syntaxLanguage = "JavaScript";

		public RuntimeSyntaxEvent(String code, String syntax) {
			super(code);
			this.syntaxLanguage = syntax;
		}

		public RuntimeSyntaxEvent(String code) {
			super(code);
		}

		public String getLanguage() {
			return syntaxLanguage;
		}
	}

}
