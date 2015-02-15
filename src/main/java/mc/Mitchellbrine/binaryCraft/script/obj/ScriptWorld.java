package mc.Mitchellbrine.binaryCraft.script.obj;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ScriptWorld {

	protected World world;

	public ScriptWorld(World server) {
		this.world = server;
	}

	public int getBlock(int x, int y, int z) {
		if (world.getBlock(x,y,z) == null || world.isAirBlock(x,y,z)) {
			return -1;
		}
		return Block.getIdFromBlock(world.getBlock(x,y,z));
	}

	public boolean setBlock(int x, int y, int z, int id) {
		return world.setBlock(x,y,z,Block.getBlockById(id));
	}

	public boolean setBlock(int x, int y, int z, int id, int meta, int flag) {
		return world.setBlock(x, y, z, Block.getBlockById(id), meta, flag);
	}

	public EntityPlayer getPlayer(String name) {
		return world.getPlayerEntityByName(name);
	}

	public long getTotalTime() {
		return world.getTotalWorldTime();
	}

	public long getTime() {
		return world.getWorldTime();
	}

	public ScriptInventory getInventory(int x, int y, int z) {
		if (world.getTileEntity(x,y,z) instanceof IInventory) {
			return new ScriptInventory((IInventory)world.getTileEntity(x,y,z));
		}
		return null;
	}

}
