package mc.Mitchellbrine.binaryCraft.container;

import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ContainerComputer extends Container {
	public TileEntityComputer computer;

	public ContainerComputer(TileEntityComputer computer) {
		this.computer = computer;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
}
