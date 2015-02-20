package mc.Mitchellbrine.binaryCraft.block;

import cpw.mods.fml.common.network.NetworkRegistry;
import mc.Mitchellbrine.binaryCraft.BinaryCraft;
import mc.Mitchellbrine.binaryCraft.client.gui.GuiHandler;
import mc.Mitchellbrine.binaryCraft.network.PacketHandler;
import mc.Mitchellbrine.binaryCraft.network.ScriptPacketClient;
import mc.Mitchellbrine.binaryCraft.tile.TileEntityComputer;
import mc.Mitchellbrine.binaryCraft.util.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Mitchellbrine on 2015.
 */
public class BlockComputer extends BlockContainer {

	private IIcon sideIcon;
	private IIcon topIcon;

	protected BlockComputer() {
		super(Material.iron);
		this.setHardness(5.0F);
		this.setCreativeTab(BinaryCraft.tab);
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
			if (!world.isRemote) {
			}
			if (world.isRemote) {
				player.openGui(BinaryCraft.instance,0,world,x,y,z);
			}
		}
		return false;
	}

	public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int direction)
	{
		TileEntityComputer computer = (TileEntityComputer) access.getTileEntity(x,y,z);
		if (computer != null) {
			return computer.getPower();
		}
		return 0;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
			int k = getOrientation(meta);

			return ((k == 0 || k == 1) && side == 3) || ((k != 0 && k != 1) && side == k) ? this.blockIcon : (side == 0 || side == 1 ? topIcon : sideIcon);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		blockIcon = register.registerIcon(References.MODID.toLowerCase() + ":terminalFront");
		topIcon = register.registerIcon(References.MODID.toLowerCase() + ":terminalTop");
		sideIcon = register.registerIcon(References.MODID.toLowerCase() + ":terminalSide");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		int l = determineOrientation(world, x, y, z, entity);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	public static int determineOrientation(World p_150071_0_, int p_150071_1_, int p_150071_2_, int p_150071_3_, EntityLivingBase p_150071_4_)
	{
		if (MathHelper.abs((float) p_150071_4_.posX - (float) p_150071_1_) < 2.0F && MathHelper.abs((float)p_150071_4_.posZ - (float)p_150071_3_) < 2.0F)
		{
			double d0 = p_150071_4_.posY + 1.82D - (double)p_150071_4_.yOffset;

			if (d0 - (double)p_150071_2_ > 2.0D)
			{
				return 1;
			}

			if ((double)p_150071_2_ - d0 > 0.0D)
			{
				return 0;
			}
		}

		int l = MathHelper.floor_double((double)(p_150071_4_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
	}

	public static int getOrientation(int p_150076_0_)
	{
		return p_150076_0_ & 7;
	}


}
