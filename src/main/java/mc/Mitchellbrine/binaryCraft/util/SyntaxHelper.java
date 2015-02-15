package mc.Mitchellbrine.binaryCraft.util;

import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mitchellbrine on 2015.
 */
public class SyntaxHelper {

	public static ArrayList<String> syntax = new ArrayList<String>();
	public static HashMap<String,EnumChatFormatting> syntaxToColor = new HashMap<String, EnumChatFormatting>();

	public static void init() {
		syntax.clear();
		syntaxToColor.clear();

		syntax.add("(?:'(.[^']+)')");
		syntaxToColor.put("(?:'(.[^']+)')", EnumChatFormatting.GREEN);

		syntax.add("comp");
		syntaxToColor.put("comp",EnumChatFormatting.RED);

		syntax.add("world");
		syntaxToColor.put("world",EnumChatFormatting.RED);

		syntax.add("x");
		syntaxToColor.put("x",EnumChatFormatting.RED);
		syntax.add("y");
		syntaxToColor.put("y",EnumChatFormatting.RED);
		syntax.add("z");
		syntaxToColor.put("z",EnumChatFormatting.RED);
	}

	public static String getSyntaxedString(String oldString) {
		String newString = oldString;
		/*for (String syntax : SyntaxHelper.syntax) {
			if (!syntax.equalsIgnoreCase("(?:'(.[^']+)')")) {
				newString = newString.replaceAll(syntax, SyntaxHelper.syntaxToColor.get(syntax) + syntax + EnumChatFormatting.RESET);
			} else {
				//newString = newString.replaceAll(syntax, "$1" + syntaxToColor.get(syntax) + inBetweenValue + EnumChatFormatting.RESET + "$2");
			}
		} */
		return newString;
	}

}
