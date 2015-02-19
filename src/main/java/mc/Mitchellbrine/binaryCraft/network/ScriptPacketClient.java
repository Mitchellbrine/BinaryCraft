package mc.Mitchellbrine.binaryCraft.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ScriptPacketClient implements IMessage, IMessageHandler<ScriptPacketClient,IMessage> {

	private int x, y, z;

	private ArrayList<ComputerScript> scripts;

	public ScriptPacketClient() {
	}

	public ScriptPacketClient(TileEntityComputer te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		this.scripts = te.getScripts();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		scripts = new ArrayList<ComputerScript>();
		while (buf.readableBytes() > 1) {
			scripts.add(new ComputerScript(new ScriptComputer((TileEntityComputer) Minecraft.getMinecraft().theWorld.getTileEntity(x, y, z)), ByteBufUtils.readUTF8String(buf), ByteBufUtils.readUTF8String(buf)));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		for (ComputerScript script : scripts) {
			ByteBufUtils.writeUTF8String(buf, script.identifier);
			ByteBufUtils.writeUTF8String(buf, script.scriptCode);
		}
	}

	@Override
	public IMessage onMessage(ScriptPacketClient message, MessageContext ctx) {
		TileEntityComputer te = (TileEntityComputer) Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
		if (te != null) {
			te.setScripts(message.scripts);
			te.hasFiredTheSearch = true;
		}
		return null;
	}
}