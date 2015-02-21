package mc.Mitchellbrine.binaryCraft.syntax;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class Syntaxes {

	public static ArrayList<ScriptSyntax> syntaxes = new ArrayList<ScriptSyntax>();

	static {
		registerSyntax(new DogeScriptSyntax());
	}

	public static void registerSyntax(ScriptSyntax syntax) {
		if (syntax.getAbbreviation() == null || syntax.getAbbreviation().equalsIgnoreCase("")) throw new RuntimeException("The script has a null abbreviation. This shouldn't happen!");
		else
			syntaxes.add(syntax);
	}

}
