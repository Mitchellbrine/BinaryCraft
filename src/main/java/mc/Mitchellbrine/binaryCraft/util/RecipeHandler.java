package mc.Mitchellbrine.binaryCraft.util;

import cpw.mods.fml.common.registry.GameRegistry;
import mc.Mitchellbrine.binaryCraft.block.BlockRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 * Created by Mitchellbrine on 2015.
 */
public class RecipeHandler {

	public static void init() {
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.binaryComputer),"XXX","XMX","XGX",'X', Blocks.iron_block,'M',Blocks.redstone_block,'G',Blocks.glass);
	}

}
