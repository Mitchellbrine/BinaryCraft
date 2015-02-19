package mc.Mitchellbrine.binaryCraft.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import mc.Mitchellbrine.binaryCraft.script.ComputerScript;
import mc.Mitchellbrine.binaryCraft.script.obj.ScriptComputer;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class RunScriptPacket implements IMessage, IMessageHandler<RunScriptPacket,IMessage> {

	private int x, y, z, dimension;

	private ComputerScript script;

	public RunScriptPacket() {}

	public RunScriptPacket(TileEntityComputer te, int dimension, ComputerScript script) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		this.dimension = dimension;
		this.script = script;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		dimension = buf.readInt();
		String identifier = ByteBufUtils.readUTF8String(buf);
		String scriptCode = ByteBufUtils.readUTF8String(buf);
		script = new ComputerScript(new ScriptComputer((TileEntityComputer)MinecraftServer.getServer().worldServerForDimension(dimension).getTileEntity(x,y,z)),identifier,scriptCode);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(dimension);
		ByteBufUtils.writeUTF8String(buf,script.identifier);
		ByteBufUtils.writeUTF8String(buf,script.scriptCode);
	}

	@Override
	public IMessage onMessage(RunScriptPacket message, MessageContext ctx) {
			TileEntityComputer te = (TileEntityComputer) MinecraftServer.getServer().worldServerForDimension(message.dimension).getTileEntity(message.x, message.y, message.z);
			if (te != null) {
				te.runScript(message.script);
			}
		return null;
	}
}