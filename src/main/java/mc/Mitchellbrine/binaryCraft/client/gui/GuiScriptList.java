package mc.Mitchellbrine.binaryCraft.client.gui;

import cpw.mods.fml.client.GuiScrollingList;
import mc.Mitchellbrine.binaryCraft.BinaryCraft;
import mc.Mitchellbrine.binaryCraft.network.PacketHandler;
import mc.Mitchellbrine.binaryCraft.network.RunScriptPacket;
import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import net.minecraft.client.renderer.Tessellator;

import javax.script.ScriptException;
import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class GuiScriptList extends GuiScrollingList {
	private GuiComputer parent;
	private ArrayList<ComputerScript> scripts;

	public GuiScriptList(GuiComputer parent, ArrayList<ComputerScript> mods, int listWidth) {
		super(parent.mc, listWidth, parent.height, 32, parent.height - 66 + 4, 10, 35);
		this.parent = parent;
		this.scripts = mods;
	}

	@Override
	protected int getSize() {
		return scripts.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		if (this.isSelected(var1)) {
			if (scripts.get(var1).scriptCode.contains("comp.isServerScript()")) {
				PacketHandler.INSTANCE.sendToServer(new RunScriptPacket(this.parent.getTE(),this.parent.getTE().getWorldObj().provider.dimensionId,scripts.get(var1)));
			} else {
				scripts.get(var1).run(BinaryCraft.jsEngine);
			}
		}
		this.parent.setIndex(var1);
	}

	@Override
	protected boolean isSelected(int var1) {
		return this.parent.isIndexSelected(var1);
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected int getContentHeight() {
		return (this.getSize()) * 35 + 1;
	}

	@Override
	protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5) {
		ComputerScript mc = scripts.get(listIndex);
		this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(mc.identifier, listWidth - 10), this.left + 3, var3 + 2, 0xFFFFFF);
		//this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(mc.getColor(), listWidth - 10), this.left + 3 , var3 + 12, 0xCCCCCC);
	}
}


