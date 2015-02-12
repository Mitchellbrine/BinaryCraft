package mc.Mitchellbrine.binaryCraft.block;

import mc.Mitchellbrine.binaryCraft.client.gui.GuiHandler;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import mc.Mitchellbrine.binaryCraft.util.References;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Mitchellbrine on 2015.
 */
public class BlockComputer extends BlockContainer {

	protected BlockComputer() {
		super(Material.iron);
		this.setHardness(5.0F);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockName("biCComputer");
		this.setBlockTextureName(References.MODID + ":computer");
		BlockRegistry.blocks.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityComputer();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		TileEntityComputer computer = (TileEntityComputer) world.getTileEntity(x,y,z);
		if (computer != null) {
			if (world.isRemote) {
				GuiHandler.openGui(GuiHandler.IDS.COMPUTER, computer);
			}
		}
		return false;
	}
}
