package seasonal.parade.halloween.blocks;

import seasonal.parade.halloween.client.EntityEvilSnowman;
import seasonal.parade.halloween.common.Halloween;
import net.minecraft.src.Block;
import net.minecraft.src.BlockDirectional;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityIronGolem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySnowman;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EvilPumpkin extends HalloweenBlockDirectional
{
    /** Boolean used to seperate different states of blocks */
    private boolean blockType;

    public EvilPumpkin(int i, int j, boolean type)
    {
        super(i, Material.pumpkin);
        this.blockIndexInTexture = j;
        this.setTickRandomly(true);
        this.blockType = type;
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setRequiresSelfNotify();
    }
    
    int top = 1;
    int side = 0;
    int face = 2;
    

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int side, int meta)
    {
        if (side == 1)
        {
            return this.blockIndexInTexture;
        }
        else if (side == 0)
        {
            return this.blockIndexInTexture;
        }
        else
        {
            int var3 = this.blockIndexInTexture + 2;

            if (this.blockType)
            {
                ++var3;
            }

            return meta == 2 && side == 2 ? var3 : (meta == 3 && side == 5 ? var3 : (meta == 0 && side == 3 ? var3 : (meta == 1 && side == 4 ? var3 : this.blockIndexInTexture + 1)));
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int meta)
    {
        return meta == 1 ? this.blockIndexInTexture : (meta == 0 ? this.blockIndexInTexture : (meta == 3 ? this.blockIndexInTexture + 1 + 16 : this.blockIndexInTexture + 16));
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int par2, int par3, int par4)
    {
        super.onBlockAdded(world, par2, par3, par4);

        if (world.getBlockId(par2, par3 - 1, par4) == Halloween.blockAsh.blockID && world.getBlockId(par2, par3 - 2, par4) == Halloween.blockAsh.blockID)
        {
            if (!world.isRemote)
            {
                world.setBlock(par2, par3, par4, 0);
                world.setBlock(par2, par3 - 1, par4, 0);
                world.setBlock(par2, par3 - 2, par4, 0);
                EntityEvilSnowman var9 = new EntityEvilSnowman(world);
                var9.setLocationAndAngles((double)par2 + 0.5D, (double)par3 - 1.95D, (double)par4 + 0.5D, 0.0F, 0.0F);
                world.spawnEntityInWorld(var9);
                world.notifyBlockChange(par2, par3, par4, 0);
                world.notifyBlockChange(par2, par3 - 1, par4, 0);
                world.notifyBlockChange(par2, par3 - 2, par4, 0);
            }

            for (int var10 = 0; var10 < 120; ++var10)
            {
                world.spawnParticle("snowshovel", (double)par2 + world.rand.nextDouble(), (double)(par3 - 2) + world.rand.nextDouble() * 2.5D, (double)par4 + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        } else {
            world.notifyBlockChange(par2, par3 - 1, par4 - 1, 0);
            world.notifyBlockChange(par2, par3 - 1, par4 + 1, 0);
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int par2, int par3, int par4)
    {
        int var5 = world.getBlockId(par2, par3, par4);
        return (var5 == 0 || Block.blocksList[var5].blockMaterial.isGroundCover()) && world.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(par2, par3, par4, var6);
    }
}
