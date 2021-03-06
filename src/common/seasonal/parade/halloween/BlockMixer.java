package seasonal.parade.halloween;

import java.util.Random;

import net.minecraft.src.*;

public class BlockMixer extends BlockContainer{
	public BlockMixer(int blockId, int j){
		super(blockId, j, Material.iron);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t){
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		
		if(tile_entity == null || player.isSneaking()){
			return false;
		}
		player.openGui(Halloween.instance, GuiIds.MIXER, world, x, y, z);
		return true;
	}
	/**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)
        {
            return 10;
        }
        else if (par5 == 0)
        {
            return 9;
        }
        else
        {
            int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
            return par5 != var6 ? 8 : 7;
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return par1 == 1 ? 10 : (par1 == 0 ? 8 : (par1 == 3 ? 8 : 7));
    }
	@Override
	public void breakBlock(World world, int x, int y, int z, int i, int j){
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, i, j);
	}
	
	private void dropItems(World world, int x, int y, int z){
		Random rand = new Random();
		
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		
		if(!(tile_entity instanceof IInventory)){
			return;
		}
		
		IInventory inventory = (IInventory) tile_entity;
		
		for(int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack item = inventory.getStackInSlot(i);
			
			if(item != null && item.stackSize > 0){
				float rx = rand.nextFloat() * 0.6F + 0.1F;
				float ry = rand.nextFloat() * 0.6F + 0.1F;
				float rz = rand.nextFloat() * 0.6F + 0.1F;
				
				EntityItem entity_item = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
				
				if(item.hasTagCompound()){
					entity_item.item.setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}
				
				float factor = 0.5F;
				
				entity_item.motionX = rand.nextGaussian() * factor;
				entity_item.motionY = rand.nextGaussian() * factor + 0.2F;
				entity_item.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entity_item);
				item.stackSize = 0;
			}
		}
	}
	/**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
        }

        if (var6 == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
        }

        if (var6 == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
        }

        if (var6 == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
        }
    }
	@Override
	public TileEntity createNewTileEntity(World world){
		return new TileMixer();
	}
	
	@Override
	public String getTextureFile(){
		return DefaultProps.TEXTURE_BLOCKS;
	}
}