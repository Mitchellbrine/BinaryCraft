package mc.Mitchellbrine.binaryCraft.syntax;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class DogeScriptSyntax extends ScriptSyntax {

	public DogeScriptSyntax() {
		super("DogeScript","dgs");
	}

	@Override
	public String finalText(String oldScript) {
		ArrayList<String> newAlteredLines = new ArrayList<String>();
		String newScript = "";
		for (String originalScript : oldScript.split("\n")) {
			originalScript = originalScript.replaceAll("shh ", " // ");
			originalScript = originalScript.replaceAll(" not ", " !== ");
			originalScript = originalScript.replaceAll(" and ", " && ");
			originalScript = originalScript.replaceAll(" or ", " || ");
			originalScript = originalScript.replaceAll(" is ", " === ");
			originalScript = originalScript.replaceAll(" next", " ;");
			originalScript = originalScript.replaceAll(" as ", " = ");
			originalScript = originalScript.replaceAll(" more ", " += ");
			originalScript = originalScript.replaceAll(" less ", " -= ");
			originalScript = originalScript.replaceAll(" lots ", " *= ");
			originalScript = originalScript.replaceAll(" few ", " /= ");
			originalScript = originalScript.replaceAll(" bigger ", " > ");
			originalScript = originalScript.replaceAll(" smaller ", " < ");
			originalScript = originalScript.replaceAll(" biggerish ", " >= ");
			originalScript = originalScript.replaceAll(" smallerish ", " <= ");
			originalScript = originalScript.replaceAll(" dose ", " . ");
			originalScript = originalScript.replaceAll("but", "else");
			originalScript = originalScript.replaceAll("wow&", "}");
			originalScript = originalScript.replaceAll("wow", "}");
			if (originalScript.contains("rly")) {
				String rlyIf = originalScript.substring(originalScript.indexOf("rly ") + 4);
				originalScript = "if (" + rlyIf + ")";
			}
			if (originalScript.contains("many")) {
				String whileLoop = originalScript.substring(originalScript.indexOf("many ") + 5);
				originalScript = "while(" + whileLoop + ")";
			}
			if (originalScript.contains("much")) {
				String forLoop = originalScript.substring(originalScript.indexOf("much ") + 5);
				originalScript = "for(" + forLoop + ")";
			}
			newAlteredLines.add(originalScript);
		}
		for (String string : newAlteredLines) {
			newScript = newScript + string + "\n";
		}
		return newScript;
	}
}
