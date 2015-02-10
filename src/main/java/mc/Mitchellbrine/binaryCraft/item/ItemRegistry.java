package mc.Mitchellbrine.binaryCraft.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;

import java.util.ArrayList;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ItemRegistry {

	public static ArrayList<Item> items = new ArrayList<Item>();

	public static void init() {



		// Done initializing

		for (Item item : items) {
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		}
	}


}
