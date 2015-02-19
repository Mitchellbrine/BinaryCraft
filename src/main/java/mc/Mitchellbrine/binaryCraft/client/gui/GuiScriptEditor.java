package mc.Mitchellbrine.binaryCraft.client.gui;

import javafx.scene.input.Clipboard;
import mc.Mitchellbrine.binaryCraft.network.PacketHandler;
import mc.Mitchellbrine.binaryCraft.network.ScriptPacket;
import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import mc.Mitchellbrine.binaryCraft.util.SyntaxHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Mitchellbrine on 2015.
 */
public class GuiScriptEditor extends GuiScreen {

	private TileEntityComputer te;

	private String identifier;
	private String scriptCode;

	private GuiTextField identifierLine;

	private GuiButton doneButton;

	private int yPosition;
	private int xPosition;

	private int cursorCounter;

	public GuiScriptEditor(TileEntityComputer te) {
		this.te = te;
		this.identifier = "";
		this.scriptCode = "";
	}

	public GuiScriptEditor(TileEntityComputer te, String identifier) {
		this.te = te;
		this.identifier = identifier;
		this.scriptCode = "";
	}

	public GuiScriptEditor(TileEntityComputer te, String identifier,String code) {
		this.te = te;
		this.identifier = identifier;
		this.scriptCode = code;
	}

	@SuppressWarnings("unchecked")
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(doneButton = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done")));
		this.buttonList.add(new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel")));
		/*this.scriptLine = new GuiTextField(this.fontRendererObj, this.width / 2 - 150, 50, 300, 200);
		this.scriptLine.setMaxStringLength(32767);
		this.scriptLine.setFocused(false);
		this.scriptLine.setText(this.code); */
		this.identifierLine = new GuiTextField(this.fontRendererObj, this.width / 2 - 75, 10,150,20);
		this.identifierLine.setMaxStringLength(64);
		this.identifierLine.setFocused(true);
		this.identifierLine.setText(this.identifier);

		this.doneButton.enabled = (this.identifierLine.getText().trim().length() > 0);

		cursorCounter = 0;

	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	private void updateCursorCounter() {
		cursorCounter++;
	}

	protected void actionPerformed(GuiButton button){
		switch (button.id) {
			case 0:
				ArrayList<ComputerScript> removeTheseScripts = new ArrayList<ComputerScript>();

				boolean isMet = false;
				for (ComputerScript script : te.getScripts()) {
						if (this.identifierLine.getText().equalsIgnoreCase(script.identifier)) {
							if (this.scriptCode.trim().length() > 0) {
								script.setCode(this.scriptCode);
								isMet = true;
							} else {
								removeTheseScripts.add(script);
								isMet = true;
							}
						}
				}
				if (!isMet) {
					te.getScripts().add(new ComputerScript(new ScriptComputer(this.te),this.identifierLine.getText(),this.scriptCode));
				}

				for (ComputerScript script : removeTheseScripts) {
					te.getScripts().remove(script);
				}

				PacketHandler.INSTANCE.sendToServer(new ScriptPacket(this.te,this.te.getWorldObj().provider.dimensionId));
				break;
			default:
				break;
		}
		this.mc.thePlayer.closeScreen();
		super.actionPerformed(button);
	}

	public void updateScreen()
	{
		//this.scriptLine.updateCursorCounter();
		this.identifierLine.updateCursorCounter();
		this.doneButton.enabled = (this.identifierLine.getText().trim().length() > 0);
		updateCursorCounter();
	}

	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
	{
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		//this.scriptLine.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.identifierLine.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}

	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
		this.drawDefaultBackground();
		//this.scriptLine.drawTextBox();
		this.identifierLine.drawTextBox();

		drawRect(this.width / 2 - 151, 49, this.width / 2 + 151,251,-6250336);
		drawRect(this.width / 2 - 150, 50, this.width / 2 + 150,250,-16777216);
		yPosition = 52;
		String[] newLines = this.scriptCode.split("\n");
		for (String string : newLines) {
			/*if (this.fontRendererObj.getStringWidth(string) > this.width / 2 + 150) {
				string = string + "\n";
			} */
			this.fontRendererObj.drawStringWithShadow(SyntaxHelper.getSyntaxedString(string), this.width / 2 - 150, yPosition, /*0x3ADF00*/ 0xFFFFFF);
			if (Arrays.asList(newLines).lastIndexOf(string) == newLines.length - 1) {
				if (!this.identifierLine.isFocused() && this.cursorCounter / 6 % 2 == 0) {
					this.fontRendererObj.drawStringWithShadow("_", this.width / 2 - 150 + (fontRendererObj.getStringWidth(string)), yPosition, /*0x3ADF00*/ 0xFFFFFF);
				}
			}
			yPosition += 10;
		}
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}

	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		//this.scriptLine.textboxKeyTyped(p_73869_1_, p_73869_2_);
		this.identifierLine.textboxKeyTyped(p_73869_1_, p_73869_2_);

		if (p_73869_2_ == 1) {
			this.actionPerformed(this.doneButton);
		}

		if (!this.identifierLine.isFocused()) {
			if (p_73869_1_ == 13) {
				String oldWords = this.scriptCode;
				this.scriptCode = oldWords + "\n";
			} else if (p_73869_2_ == 14) {
				if (this.scriptCode.length() > 0) {
					String oldWords = this.scriptCode;
					this.scriptCode = oldWords.substring(0, oldWords.length() - 1);
				}
			} else {
				if ((p_73869_2_ >= 2 && p_73869_2_ <= 13) || (p_73869_2_ >= 16 && p_73869_2_ <= 27) || (p_73869_2_ >= 30 && p_73869_2_ < 41) || (p_73869_2_ >= 43 && p_73869_2_ <= 53) || (p_73869_2_ >= 145 && p_73869_2_ <= 147) || p_73869_2_ == 57) {
					if (p_73869_2_ == 25 && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))) {
						String oldWords = this.scriptCode;
						this.scriptCode = oldWords + GuiScreen.getClipboardString();
					} else {
						if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
							String oldWords = this.scriptCode;
							this.scriptCode = oldWords + p_73869_1_;
						}
					}
				}
			}
		}

	}

	private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_)
	{
		int i1;

		if (p_146188_1_ < p_146188_3_)
		{
			i1 = p_146188_1_;
			p_146188_1_ = p_146188_3_;
			p_146188_3_ = i1;
		}

		if (p_146188_2_ < p_146188_4_)
		{
			i1 = p_146188_2_;
			p_146188_2_ = p_146188_4_;
			p_146188_4_ = i1;
		}

		if (p_146188_3_ > this.xPosition + this.width)
		{
			p_146188_3_ = this.xPosition + this.width;
		}

		if (p_146188_1_ > this.xPosition + this.width)
		{
			p_146188_1_ = this.xPosition + this.width;
		}

		Tessellator tessellator = Tessellator.instance;
		GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glLogicOp(GL11.GL_OR_REVERSE);
		tessellator.startDrawingQuads();
		tessellator.addVertex((double)p_146188_1_, (double)p_146188_4_, 0.0D);
		tessellator.addVertex((double)p_146188_3_, (double)p_146188_4_, 0.0D);
		tessellator.addVertex((double)p_146188_3_, (double)p_146188_2_, 0.0D);
		tessellator.addVertex((double)p_146188_1_, (double)p_146188_2_, 0.0D);
		tessellator.draw();
		GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
