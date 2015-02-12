package mc.Mitchellbrine.binaryCraft.client.gui;

import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class GuiComputer extends GuiScreen {

	private TileEntityComputer te;

	private GuiScriptList list;

	private ArrayList<ComputerScript> scripts = new ArrayList<ComputerScript>();
	private int listWidth;
	private int selected;
	private ComputerScript selectedScript;
	private GuiButton addButton;
	private GuiButton removeButton;

	public GuiComputer(TileEntityComputer te) {
		this.te = te;
		refreshScripts();
	}

	public void refreshScripts() {
		for (ComputerScript script : te.getScripts()) {
			scripts.add(script);
		}
	}

	@SuppressWarnings("unchecked")
	public void initGui() {

		this.buttonList.clear();

		for (ComputerScript script : scripts) {
			listWidth=Math.max(listWidth,fontRendererObj.getStringWidth(script.identifier) + 10);
			listWidth=Math.max(listWidth,fontRendererObj.getStringWidth(script.identifier) + 10);
		}
		listWidth=Math.min(listWidth, 150);

		addButton = new GuiButton(20, 10, this.height - 60, this.listWidth, 20, "Add");
		removeButton = new GuiButton(21, 10, this.height - 38, this.listWidth, 20, "Edit");

		addButton.enabled = true;
		removeButton.enabled = true;

		addButton.visible = true;
		removeButton.visible = true;

		GuiButton done = new GuiButton(6, this.width / 2 - 75, this.height - 38, I18n.format("gui.done"));

		done.visible = true;

		this.buttonList.add(addButton);
		this.buttonList.add(removeButton);
		this.buttonList.add(done);

		list = new GuiScriptList(this, scripts, listWidth);
		this.list.registerScrollButtons(this.buttonList, 7, 8);

	}


	public void setIndex(int var1)
	{
		this.selected=var1;
		if (var1>=0 && var1<=scripts.size()) {
			this.selectedScript=scripts.get(selected);
		} else {
			this.selectedScript=null;
		}
	}

	public boolean isIndexSelected(int var1)
	{
		return var1==selected;
	}

	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}


	@Override
	public void drawScreen(int p_571_1_, int p_571_2_, float p_571_3_) {
		this.list.drawScreen(p_571_1_, p_571_2_, p_571_3_);
		this.drawString(this.fontRendererObj, "Scripts: ", 10, 16, 0xFFFFFF);
		if (selectedScript != null) {
			this.drawCenteredString(this.fontRendererObj, this.selectedScript.identifier + ":", this.width / 2, 16, 0xFFFFFF);
		} else {
			this.drawCenteredString(this.fontRendererObj, "<Script Not Found>", this.width / 2, 16, 0xFF0000);
		}


		if (selectedScript != null) {
			GL11.glEnable(GL11.GL_BLEND);
			int offset = ( this.listWidth + this.width ) / 2;

			int yHeight = 45;
			int xLength = listWidth + 10;

			for (String string : this.selectedScript.dummyComp.getConsole().split("\n")) {
					this.drawString(fontRendererObj, string, xLength, yHeight, 0xFFFFFF);
					yHeight += 10;
					if (yHeight > this.height) {
						this.selectedScript.dummyComp.computer.consoleOutput = "";
						break;
					}
			}

			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 21:
				scripts.remove(selectedScript);
				mc.thePlayer.closeScreen();
				break;
			case 20:
				mc.thePlayer.closeScreen();
				Minecraft.getMinecraft().displayGuiScreen(new GuiScriptEditor(this.te));
				break;
		}
		super.actionPerformed(button);
	}
}
