package mc.Mitchellbrine.binaryCraft.client.gui;

import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class GuiScriptEditor extends GuiScreen {

	private TileEntityComputer te;

	private String identifier;
	private String code;

	private GuiTextField scriptLine;
	private GuiTextField identifierLine;

	private GuiButton doneButton;

	public GuiScriptEditor(TileEntityComputer te) {
		this.te = te;
		this.identifier = "";
		this.code = "";
	}

	public GuiScriptEditor(TileEntityComputer te, String identifier) {
		this.te = te;
		this.identifier = identifier;
		this.code = "";
	}

	public GuiScriptEditor(TileEntityComputer te, String identifier,String code) {
		this.te = te;
		this.identifier = identifier;
		this.code = code;
	}

	@SuppressWarnings("unchecked")
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
		this.buttonList.add(doneButton = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
		this.scriptLine = new GuiTextField(this.fontRendererObj, this.width / 2 - 150, 50, 300, 200);
		this.scriptLine.setMaxStringLength(32767);
		this.scriptLine.setFocused(false);
		this.scriptLine.setText(this.code);
		this.identifierLine = new GuiTextField(this.fontRendererObj, this.width - 300, 10,150,20);
		this.identifierLine.setMaxStringLength(64);
		this.identifierLine.setFocused(true);
		this.identifierLine.setText(this.identifier);

		this.doneButton.enabled = (this.identifierLine.getText().trim().length() > 0);

	}

	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton button){
		switch (button.id) {
			case 0:
				ArrayList<ComputerScript> removeTheseScripts = new ArrayList<ComputerScript>();

				boolean isMet = false;
				for (ComputerScript script : te.getScripts()) {
						if (this.identifierLine.getText().equalsIgnoreCase(script.identifier)) {
							if (this.scriptLine.getText().trim().length() > 0) {
								script.setCode(this.scriptLine.getText());
								isMet = true;
							} else {
								removeTheseScripts.add(script);
								isMet = true;
							}
						}
				}
				if (!isMet) {
					te.getScripts().add(new ComputerScript(new ScriptComputer(this.te),this.identifierLine.getText(),this.scriptLine.getText()));
				}

				for (ComputerScript script : removeTheseScripts) {
					te.getScripts().remove(script);
				}

				break;
			default:
				break;
		}
		this.mc.thePlayer.closeScreen();
		super.actionPerformed(button);
	}

	public void updateScreen()
	{
		this.scriptLine.updateCursorCounter();
		this.identifierLine.updateCursorCounter();
	}

	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
	{
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.scriptLine.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.identifierLine.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}

	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
		this.drawDefaultBackground();
		this.scriptLine.drawTextBox();
		this.identifierLine.drawTextBox();
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}

	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		this.scriptLine.textboxKeyTyped(p_73869_1_, p_73869_2_);
		this.identifierLine.textboxKeyTyped(p_73869_1_, p_73869_2_);

		if (p_73869_2_ == 1)
		{
			this.actionPerformed(this.doneButton);
		}
	}

}
