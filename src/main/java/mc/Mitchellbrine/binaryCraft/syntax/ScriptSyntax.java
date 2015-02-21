package mc.Mitchellbrine.binaryCraft.syntax;

/**
 * Created by Mitchellbrine on 2015.
 */
public abstract class ScriptSyntax {

	private String name, abbreviation;

	public ScriptSyntax(String name) {
		this.name = name;
		if (name.length() >= 3) {
			this.abbreviation = name.substring(0, 2);
		} else {
			this.abbreviation = name;
		}
	}

	public ScriptSyntax(String name, String abbreviation) {
		this.name = name;
		this.abbreviation = abbreviation;
	}

	public abstract String finalText(String originalScript);

	public String getName() {
		return name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
}
