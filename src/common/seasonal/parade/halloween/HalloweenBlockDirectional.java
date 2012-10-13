package seasonal.parade.halloween;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public abstract class HalloweenBlockDirectional extends Block
{
    protected HalloweenBlockDirectional(int i, int j, Material mat)
    {
        super(i, j, mat);
    }

    protected HalloweenBlockDirectional(int i, Material mat)
    {
        super(i, mat);
    }

    /**
     * Returns the orentation value from the specified metadata
     */
    public static int getDirection(int dir)
    {
        return dir & 3;
    }
    
    public String getTextureFile(){
		return DefaultProps.TEXTURE_BLOCKS;
    }
}
