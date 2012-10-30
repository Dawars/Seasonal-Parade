package seasonal.parade.halloween;

import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockCandyMaker extends BlockContainer{

	public BlockCandyMaker(int blockId, int j){
		super(blockId, j, Material.iron);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t){
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		
		if(tile_entity == null || player.isSneaking()){
			return false;
		}
		player.openGui(Halloween.instance, GuiIds.CANDY_MAKER, world, x, y, z);
		return true;
	}
	
	/**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)
        {
            return 14;
        }
        else if (par5 == 0)
        {
            return 14;
        }
        else
        {
            int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
            return par5 != var6 ? 12 : 11;
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return par1 == 1 ? 14 : (par1 == 0 ? 12 : (par1 == 3 ? 12 : 11));
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
	
	@Override
	public TileEntity createNewTileEntity(World world){
		return new TileCandyMaker();
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		TileCandyMaker tile = (TileCandyMaker) world.getBlockTileEntity(i, j, k);

		if (!tile.canWork())
			return;

		float f = (float) i + 0.5F;
		float f1 = (float) j + 0.0F + (random.nextFloat() * 6F) / 16F;
		float f2 = (float) k + 0.5F;
		float f3 = 0.52F;
		float f4 = random.nextFloat() * 0.6F - 0.3F;

		world.spawnParticle("reddust", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("reddust", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("reddust", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("reddust", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		TileCandyMaker tile = (TileCandyMaker) world.getBlockTileEntity(i, j, k);

		if (tile != null) {
			tile.checkRedstonePower();
		}
	}
	/**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        TileCandyMaker tile = (TileCandyMaker) world.getBlockTileEntity(i, j, k);

		if (tile != null) {
			tile.checkRedstonePower();
		}
    }
	/**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving par5EntityLiving)
    {
    	TileCandyMaker tile = (TileCandyMaker) world.getBlockTileEntity(i, j, k);

		if (tile != null) {
			tile.checkRedstonePower();
		}
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
        	world.setBlockMetadataWithNotify(i, j, k, 2);
        }

        if (var6 == 1)
        {
        	world.setBlockMetadataWithNotify(i, j, k, 5);
        }

        if (var6 == 2)
        {
        	world.setBlockMetadataWithNotify(i, j, k, 3);
        }

        if (var6 == 3)
        {
        	world.setBlockMetadataWithNotify(i, j, k, 4);
        }
    }
	@Override
	public String getTextureFile(){
		return DefaultProps.TEXTURE_BLOCKS;
	}
}