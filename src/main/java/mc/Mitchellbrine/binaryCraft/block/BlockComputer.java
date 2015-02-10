package mc.Mitchellbrine.binaryCraft.block;

import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import mc.Mitchellbrine.binaryCraft.util.References;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
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
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityComputer();
	}
}
