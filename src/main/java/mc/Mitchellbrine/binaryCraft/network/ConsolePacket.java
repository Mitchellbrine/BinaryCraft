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
public class ConsolePacket implements IMessage, IMessageHandler<ConsolePacket,IMessage> {

	private int x, y, z;

	private String console;

	public ConsolePacket() {
	}

	public ConsolePacket(TileEntityComputer te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		this.console = te.consoleOutput;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		console = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		ByteBufUtils.writeUTF8String(buf,console);
	}

	@Override
	public IMessage onMessage(ConsolePacket message, MessageContext ctx) {
		TileEntityComputer te = (TileEntityComputer) Minecraft.getMinecraft().theWorld.getTileEntity(message.x,message.y,message.z);
		if (te != null) {
			te.consoleOutput = message.console;
		}
		return null;
	}
}