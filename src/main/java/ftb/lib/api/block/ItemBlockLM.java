package ftb.lib.api.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockLM extends ItemBlock
{
	public IBlockLM blockLM;
	
	public ItemBlockLM(Block b)
	{
		super(b);
		setHasSubtypes(true);
		setMaxDamage(0);
		
		blockLM = (IBlockLM) b;
	}
	
	@Override
	public int getMetadata(int m)
	{ return m; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List l, boolean adv)
	{ blockLM.addInformation(is, ep, l, adv); }
}