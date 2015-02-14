package mc.Mitchellbrine.binaryCraft.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ScriptPacket implements IMessage, IMessageHandler<ScriptPacket,IMessage> {

	private int x, y, z, dimension;

	private ArrayList<ComputerScript> scripts;

	public ScriptPacket() {
	}

	public ScriptPacket(TileEntityComputer te, int dimension) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		this.dimension = dimension;
		this.scripts = te.getScripts();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		dimension = buf.readInt();
		scripts = new ArrayList<ComputerScript>();
		while (buf.readableBytes() > 1) {
			scripts.add(new ComputerScript(new ScriptComputer((TileEntityComputer)MinecraftServer.getServer().worldServerForDimension(dimension).getTileEntity(x,y,z)), ByteBufUtils.readUTF8String(buf),ByteBufUtils.readUTF8String(buf)));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(dimension);
		for (ComputerScript script : scripts) {
			ByteBufUtils.writeUTF8String(buf,script.identifier);
			ByteBufUtils.writeUTF8String(buf,script.scriptCode);
		}
	}

	@Override
	public IMessage onMessage(ScriptPacket message, MessageContext ctx) {
		TileEntityComputer te = (TileEntityComputer) MinecraftServer.getServer().worldServerForDimension(message.dimension).getTileEntity(message.x, message.y, message.z);
		if (te != null) {
			te.setScripts(message.scripts);
		}
		return null;
	}
}